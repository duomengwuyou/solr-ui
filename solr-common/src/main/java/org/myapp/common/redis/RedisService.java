package org.myapp.common.redis;


import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * User: wujunbo
 * Date: 2/15/12
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RedisService {
	Collection<String> keys(String pattern);

	void delete(Collection<String> keys);

	List<Map<String, Object>> getMultiMap(Collection<String> keys) throws IOException;

	List<Map<String, Object>> getMultiMap(Collection<String> keys, String[] fields) throws IOException;

	/**
	 * time in seconds not millisecons
	 *
	 * @param key
	 * @param time seconds not millisecons
	 */
	public long expireAt(String key, long time);

	public long timeToLive(String key);

	List<String> getList(String key);

	List<String> getList(String key, long start, long end);

	void setList(String key, List<String> list);

	long incr(String key);

	<T> T getAndSet(String key, T newValue, Class<T> cls) throws IOException;

	long getListSize(String key);

	long hIncrBy(String key, String field, long inc);

	public static class ScoredValue {
		public ScoredValue(double score, String value) {
			this.score = score;
			this.value = value;
		}

		public String value;
		public double score;

		@Override
		public boolean equals(Object obj) {
			if (null == obj) {
				return false;
			}
			if (!(obj instanceof ScoredValue)) {
				return false;
			}
			ScoredValue sv = (ScoredValue) obj;
			return value == null ? sv.value == null : (value.equals(sv.value));
		}

		@Override
		public int hashCode() {
			return value == null ? 0 : value.hashCode();
		}

		@Override
		public String toString() {
			return ReflectionToStringBuilder.toString(this);
		}
	}

	public static final int ONE_DAY_IN_SECONDS = 60 * 60 * 24;

	public boolean isHealthy();

	public void delete(String key);

	public boolean exists(String key);

	public long expire(String key, int seconds);

	/**
	 * @param key
	 * @param members
	 */
	 public void addToSortedSet(String key, Collection<ScoredValue> members);

	public void addToSortedSet(String key, ScoredValue members);

	public void deleteFromSortedSet(String key, Collection<String> members);

	public void deleteFromSortedSet(String key, String members);

	public Set<String> getSortedSet(String key, int start, int end);

	public Set<String> getSortedSet(String key);

	public long getSortedSetSize(String key);

	/**
	 * @param key
	 * @param value
	 */
	public void addToSet(String key, String value);

	public void addToSet(String key, Collection<String> collection);

	public boolean isMember(String key, String member);

	public Set<String> getSet(String key);

	public long getSetSize(String key);

	public void deleteFromSet(String key, Collection<String> members);

	public void deleteFromSet(String key, String member);


	/**
	 * @param key
	 * @param values
	 */
	public void setMap(String key, Map<String, Object> values);

	public Map<String, Object> getMap(String key);

	public Map<String, Object> getMap(String key, String[] fields) throws IOException;

	public void deleteFromMap(String key, String[] fields) ;

	public void deleteFromMap(String key, String field) ;

	/**
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value);
	
	 public String setExpire(String key, String value, int seconds);

	public <T> T get(String key, Class<T> cls);
	
	
	public String get(String key);


	/**
	 * 获取一个分布式锁
	 * @param lockKey
	 * @param expired 锁过期时间；单位：毫秒
	 * @return
	 */
	public boolean acquireLock(String lockKey,long expired);

	/**
	 * 释放分布式锁
	 * @param lockKey
	 */
	public void releaseLock(String lockKey);


	public void pushQueue(String queueName,String value);

	public String popQueue(String queueName);

}

