package org.myapp.common.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

import org.myapp.common.utils.Constants;

@Service("redisService")
public class ShardedRedisServiceImpl implements RedisService{

	public static final Logger LOGGER = LoggerFactory.getLogger(ShardedRedisServiceImpl.class);
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	@Autowired
	private RedisSerializer redisSerializer;
	private String cachePrefix = Constants.cachePrefix;


	public long expireAt(String key, long time) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("expire key:{} at:{}", key, time);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			long ret = shardedJedis.expireAt(prefixedKey, time);
			if (ret != 1) {
				LOGGER.warn("key {} does not exist or timeout could not be set", key);
			}
			return ret;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return -1;
	}


	/**
	 * 获取一个分布式锁
	 * @param lockKey
	 * @param expired 锁过期时间；单位：毫秒
	 * @return
	 */
	public boolean acquireLock(String lockKey,long expired) {
		// 1. 通过SETNX试图获取一个lock
		boolean success = false;
		ShardedJedis shardedJedis = shardedJedisPool.getResource();   
		try{
			String lock = cachePrefix + lockKey;
			long value = System.currentTimeMillis() + expired + 1;     
			long acquired = shardedJedis.setnx(lock, String.valueOf(value));
			//SETNX成功，则成功获取一个锁
			if (acquired == 1){      
				success = true;
				//SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
			}else {
				long oldValue = Long.valueOf(shardedJedis.get(lock));
				//已超时
				if (oldValue < System.currentTimeMillis()) {
					String getValue = shardedJedis.getSet(lock, String.valueOf(value));               
					// 获取锁成功
					if (Long.valueOf(getValue) == oldValue) {
						success = true;
						// 已被其他进程捷足先登了
					}else{ 
						success = false;
					}
					//未超时，则直接返回失败
				}else{             
					success = false;
				}
			}        
		}catch (Exception e) {
			LOGGER.error("redis acquireLock lockkey[" + cachePrefix + ":" + lockKey + "] error", e);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return success;      
	}

	/**
	 * 释放分布式锁
	 * @param lockKey
	 */
	public void releaseLock(String lockKey) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();   
		try{
			String lock = cachePrefix + lockKey;
			long current = System.currentTimeMillis();       
			// 检查自己获得的锁当前是否已超时，若已超时，无须释放；避免删除非自己获取得到的锁
			if (current < Long.valueOf(shardedJedis.get(lock))){
				shardedJedis.del(lock);      
			}
		}catch (Exception e) {
			LOGGER.error("redis releaseLock lockkey[" + cachePrefix + ":" + lockKey + "] error", e);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public void pushQueue(String queueName,String value){
		ShardedJedis shardedJedis = shardedJedisPool.getResource();   
		try{
			shardedJedis.lpush(cachePrefix+queueName , value);
		}catch (Exception e) {
			LOGGER.error("redis releaseLock lockkey[" + cachePrefix + ":" + queueName + "] error", e);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
	}

	public String popQueue(String queueName){
		String ret = null;
		ShardedJedis shardedJedis = shardedJedisPool.getResource();    
		try{
			ret = shardedJedis.lpop(cachePrefix + queueName);
		}catch (Exception e) {
			LOGGER.error("redis releaseLock lockkey[" + cachePrefix + ":" + queueName + "] error", e);
			returnBrokenResource(shardedJedis);
		} finally {
			returnResource(shardedJedis);
		}
		return ret;
	}


	public long timeToLive(String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("ttl key:{}", key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.ttl(prefixedKey);
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return -1;
	}


	public List<String> getList(String key) {
		return getList(key, 0, -1);
	}


	public List<String> getList(String key, long start, long end) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get list for key:" + key);
		}
		key = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			List<String> l = shardedJedis.lrange(key, start, end);
			return l.isEmpty() ? null : l;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}


	public long incr(String key) {
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.incr(prefixedKey);
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return -1;
	}


	public <T> T getAndSet(String key, T newValue, Class<T> cls)
			throws IOException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get for key:{} with value:{}", key, newValue);
		}
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		try {
			String value = shardedJedis.getSet(cachePrefix + key, redisSerializer.serialize(newValue));
			if (null == value) {
				return null;
			}
			return redisSerializer.deserialize(value, cls);
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}


	public long getListSize(String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get list for key:" + key);
		}
		key = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.llen(key);
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return 0;
	}


	public long hIncrBy(String key, String field, long inc) {
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.hincrBy(prefixedKey, field, inc);
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return -1;
	}


	public void delete(String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("delete key:" + key);
		}
		key = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.del(key);
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public boolean exists(String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("check key exists:" + key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.exists(prefixedKey);
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}


	public long expire(String key, int seconds) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("expire key:{} with {} seconds", key, seconds);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			long ret = shardedJedis.expire(prefixedKey, seconds);
			if (ret != 1) {
				LOGGER.warn("key {} does not exist or timeout could not be set",key);
			}
			return ret;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return -1;
	}


	public Set<String> getSortedSet(String key, int start, int end) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get sorted set for key:" + key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Set<String> set = shardedJedis.zrevrange(prefixedKey, start, end);
			return set.isEmpty() ? null : set;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}


	public Set<String> getSortedSet(String key) {
		return getSortedSet(key, 0, -1);
	}


	public long getSortedSetSize(String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get sorted set size for key:{}", key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Long ret = shardedJedis.zcard(prefixedKey);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("zcard for key:{} with reply:{}", key, ret);
			}
			return ret;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return 0;
	}


	public void addToSet(String key, String value) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("add to set for key:" + key + " with value:" + value);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Long ret = shardedJedis.sadd(prefixedKey, value);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("add value:{} to set:{} with reply:" + ret, value, key);
			}
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public boolean isMember(String key, String member) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("check {} is member of key:{}", member, key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.sismember(prefixedKey, member);
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return false;
	}


	public Set<String> getSet(String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get set for key:" + key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Set<String> set = shardedJedis.smembers(prefixedKey);
			return set.isEmpty() ? null : set;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}


	public long getSetSize(String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get set size for key:{}", key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Long size = shardedJedis.scard(prefixedKey);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("scard for key:{} with reply:{}", key, size);
			}
			return size;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return 0;
	}


	public void setMap(String key, Map<String, Object> values) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("set map for key:{} with value:{}", key, values);
		}
		if (null == values) {
			LOGGER.info("null map values for key:" + key);
			return;
		}
		if (values.isEmpty()) {
			LOGGER.info("empty map for key:{},do nothing", key);
			return;
		}
		Map<String, String> redisValue = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : values.entrySet()) {
            try {
                redisValue.put(entry.getKey(), redisSerializer.serialize(entry.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			String status = shardedJedis.hmset(cachePrefix + key, redisValue);
			if (!"OK".equals(status)) {
				LOGGER.warn("failed to set key:{} with map value:{},status:" + status, key, redisValue);
			}
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public Map<String, Object> getMap(String key)  {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get map for key:{}", key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Map<String, String> values = shardedJedis.hgetAll(prefixedKey);
			if (values.isEmpty()) {
				return null;
			}
			Map<String, Object> result = new HashMap<String, Object>(values.size());
			for (Map.Entry<String, String> entry : values.entrySet()) {
                try {
                    result.put(entry.getKey(), redisSerializer.deserialize(entry.getValue(), Object.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
			return result;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}


	public Map<String, Object> getMap(String key, String[] fields)
			throws IOException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get map for key:{} and fields:{}", key, Arrays.asList(fields));
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			List<String> values = shardedJedis.hmget(prefixedKey, fields);
			if (values.isEmpty()) {
				return null;
			}
			Map<String, Object> result = new HashMap<String, Object>(values.size());
			for (int i = 0; i < fields.length; i++) {
				String value = values.get(i);
				if (null != value) {
					result.put(fields[i], redisSerializer.deserialize(value, Object.class));
				}
			}
			return result.isEmpty() ? null : result;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}


	public void set(String key, Object value)  {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("set for key:{} with value:{}", key, value);
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			String v = redisSerializer.serialize(value);
			shardedJedis.set(cachePrefix + key, v);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public <T> T get(String key, Class<T> cls)  {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get for key:{} with value type:{}", key, cls);
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			String value = shardedJedis.get(cachePrefix + key);
			if (null == value) {
				return null;
			}
			return redisSerializer.deserialize(value, cls);
		} catch (Exception e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}
	
	 public String get(String key) {
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("get for key:{}", key);
			}
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				String value = shardedJedis.get(cachePrefix + key);
				if (null == value) {
					return null;
				}
				return value;
			} catch (Exception e) {
				returnBrokenResource(shardedJedis);
				shardedJedis = null;
				LOGGER.warn(e.toString(), e);
			} finally {
				returnResource(shardedJedis);
			}
			return null;
		}

	private void returnBrokenResource(ShardedJedis shardedJedis) {
		if (null != shardedJedis) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
		}
	}

	private void returnResource(ShardedJedis shardedJedis) {
		if (shardedJedis != null) {
			shardedJedisPool.returnResource(shardedJedis);
		}
	}


	public List<Map<String, Object>> getMultiMap(Collection<String> keys) throws IOException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get multi map for keys:{}", keys);
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			List<Response<Map<String, String>>> list = new ArrayList<Response<Map<String, String>>>(keys.size());
			for (String key : keys) {
				String prefixedKey = cachePrefix + key;
				list.add(pipeline.hgetAll(prefixedKey));
			}
			pipeline.sync();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(keys.size());
			for (Response<Map<String, String>> response : list) {
				Map<String, String> sm = response.get();
				Map<String, Object> dm = new HashMap<String, Object>();
				for (Map.Entry<String, String> entry : sm.entrySet()) {
					dm.put(entry.getKey(), redisSerializer.deserialize(entry.getValue(), Object.class));
				}
				result.add(dm);
			}
			return result;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}


	public List<Map<String, Object>> getMultiMap(Collection<String> keys,String[] fields) throws IOException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("get multi map for keys:{} and fields:{}", keys, Arrays.asList(fields));
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			List<Response<List<String>>> list = new ArrayList<Response<List<String>>>(keys.size());
			for (String key : keys) {
				String prefixedKey = cachePrefix + key;
				list.add(pipeline.hmget(prefixedKey, fields));
			}
			pipeline.sync();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for (Response<List<String>> response : list) {
				List<String> values = response.get();
				Map<String, Object> dm = new HashMap<String, Object>();
				for (int i = 0; i < fields.length; i++) {
					String value = values.get(i);
					if (null != value) {
						dm.put(fields[i], redisSerializer.deserialize(value, Object.class));
					}
				}
				result.add(dm);
			}
			return result;
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
		return null;
	}


	public void setList(String key, List<String> list) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("set list for key:{} with value:{}", key, list);
		}
		if (list == null) {
			return;
		}
		key = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			shardedJedis.del(key);//pipeline.del(key);
			List<Response<Long>> result = new ArrayList<Response<Long>>(list.size());
			for (String s : list) {
				result.add(pipeline.rpush(key, s));
			}
			pipeline.sync();
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("set list pipeline:" + result);
			}
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public void addToSortedSet(String key, Collection<ScoredValue> members) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("add to sorted set for key:" + key + " with value:" + members);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			List<Response<Long>> result = new ArrayList<Response<Long>>(members.size());
			for (ScoredValue member : members) {
				result.add(pipeline.zadd(prefixedKey, member.score, member.value));
			}
			pipeline.sync();
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("add to sorted set pipeline:" + result);
			}
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public void addToSortedSet(String key, ScoredValue member) {
		addToSortedSet(key, Arrays.asList(member));
	}


	public void deleteFromSortedSet(String key, Collection<String> members) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("delete members:{} for key:" + key, members);
		}
		if (null == members) {
			return;
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			List<Response<Long>> result = new ArrayList<Response<Long>>(members.size());
			for (String member : members) {
				result.add(pipeline.zrem(prefixedKey, member));
			}
			pipeline.sync();
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("delete from set pipeline for key:{} with result:{}", key, result);
			}
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}
	
	
	 public String setExpire(String key, String value, int seconds) {
	        if (LOGGER.isTraceEnabled()) {
	            LOGGER.trace("expire key:{} with {} seconds", key, seconds);
	        }
	        String prefixedKey = cachePrefix + key;
	        ShardedJedis shardedJedis = null;
	        try {
	        	shardedJedis = shardedJedisPool.getResource();
	            String ret = shardedJedis.setex(prefixedKey, seconds, value);
	            return ret;
	        } catch (JedisException e) {
	            returnBrokenResource(shardedJedis);
	            shardedJedis = null;
	            LOGGER.warn(e.toString(), e);
	        } finally {
	            returnResource(shardedJedis);
	        }
	        return null;
	    }


	public void deleteFromSortedSet(String key, String members) {
		deleteFromSortedSet(key, Arrays.asList(members));
	}


	public void addToSet(String key, Collection<String> collection) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("add to set for key:" + key + " with value:" + collection);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			List<Response<Long>> result = new ArrayList<Response<Long>>(collection.size());
			for (String member : collection) {
				result.add(pipeline.sadd(prefixedKey, member));
			}
			pipeline.sync();
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("add to sorted set pipeline:" + result);
			}
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public void deleteFromSet(String key, Collection<String> members) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("delete members:{} for key:" + key, members);
		}
		if (null == members) {
			return;
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			List<Response<Long>> result = new ArrayList<Response<Long>>(members.size());
			for (String member : members) {
				result.add(pipeline.srem(prefixedKey, member));
			}
			pipeline.sync();
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("delete from set pipeline for key:{} with result:{}", key, result);
			}
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}	
	}


	public void deleteFromSet(String key, String member) {
		deleteFromSet(key, Arrays.asList(member));
	}


	public void deleteFromMap(String key, String[] fields) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("delete fields:{} from map:{}", Arrays.asList(fields), key);
		}
		String prefixedKey = cachePrefix + key;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			List<Response<Long>> result = new ArrayList<Response<Long>>(fields.length);
			for (String fl : fields) {
				result.add(pipeline.hdel(prefixedKey, fl));
			}
			pipeline.sync();
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("delete fields from map, key:{} with result:{}", key, result);
			}
		} catch (JedisException e) {
			returnBrokenResource(shardedJedis);
			shardedJedis = null;
			LOGGER.warn(e.toString(), e);
		} finally {
			returnResource(shardedJedis);
		}
	}


	public void deleteFromMap(String key, String field) {
		if (key != null && field != null) {
			deleteFromMap(key, new String[]{field});
		}
	}

	/////////


	public boolean isHealthy() {
		throw new UnsupportedOperationException();
	}


	public Collection<String> keys(String pattern) {
		throw new UnsupportedOperationException();
	}


	public void delete(Collection<String> keys) {
		throw new UnsupportedOperationException();
	}

}
