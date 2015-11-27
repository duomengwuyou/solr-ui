package org.myapp.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpPoolClientUtils {

	private final static Logger LOG = LoggerFactory.getLogger(HttpPoolClientUtils.class);

	private static HttpParams httpParams;

	private static PoolingClientConnectionManager cm;

	// 客户端总并行链接最大数
	private final static int MAX_TOTAL_CONNECTIONS = 400;

	// 每个主机的最大并行链接数
	private final static int MAX_ROUTE_CONNECTIONS = 400;

	private final static String DEFAULTCHARSET = "UTF-8";

	// 连接超时时间
	private final static int CONNECT_TIMEOUT = 10000;

	// 读取超时时间
	private final static int READ_TIMEOUT = 20000;

	static {
		httpParams = new BasicHttpParams();
		// 连接请求超时
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
		// 数据读取超时
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, READ_TIMEOUT);
		// 编码
		httpParams.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, DEFAULTCHARSET);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(MAX_TOTAL_CONNECTIONS); // 主机最大连接数和客户端最大连接数设置成一样的
		cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
	}

	public static boolean downLoadFile(String url, Map<String, String> params, String localPath) throws Exception {
		HttpGet httGet = null;
		try {

			if (params != null && params.size() > 0) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				Iterator<Entry<String, String>> it = params.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				url = url.trim() + "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");
			}

			HttpClient httpclient = getHttpClient();
			httGet = new HttpGet(url.trim());

			HttpResponse response = httpclient.execute(httGet);
			if (response.getStatusLine().getStatusCode() != 200) {
				return false;
			}
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();

			File localFile = new File(localPath);
			if (!localFile.getParentFile().exists()) {
				localFile.mkdirs();
			}
			File tmpFile = File.createTempFile(System.currentTimeMillis() + "", ".tmp", localFile.getParentFile());
			FileUtils.copyInputStreamToFile(inputStream, tmpFile);

			if (!localFile.exists()) {
				tmpFile.renameTo(localFile);
			} else if (localFile.exists() && localFile.delete()) {
				tmpFile.renameTo(localFile);
			}
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (httGet != null)
				httGet.releaseConnection();
		}
	}
	
	public static String get(String url, Map<String, String> params) throws Exception{
		return get(url, params, null, null);
	}

	public static String get(String url, Map<String, String> params, String proxyHostname, String proxyPort) throws Exception {
		long starttime = System.currentTimeMillis();
		HttpGet httGet = null;
		try {
			HttpClient httpclient = getHttpClient();
			
			if (StringUtils.isNotBlank(proxyHostname) && StringUtils.isNotBlank(proxyPort)) {
				HttpHost proxy = new HttpHost(proxyHostname, Integer.parseInt(proxyPort));
				httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}

			if (params != null && params.size() > 0) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				Iterator<Entry<String, String>> it = params.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				url = url.trim() + "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");
			}
			httGet = new HttpGet(url.trim());
			HttpResponse response = httpclient.execute(httGet);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception(response.getStatusLine().getStatusCode() + " reason:" + response.getStatusLine().getReasonPhrase());
			}
			LOG.info("url[{}],响应时间[{}]", url, (System.currentTimeMillis() - starttime));
			return EntityUtils.toString(response.getEntity(), DEFAULTCHARSET);
		} catch (ClientProtocolException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			if (httGet != null)
				httGet.releaseConnection();
		}
	}

	public static DefaultHttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient(cm);
		httpClient.setParams(httpParams);
		HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.IGNORE_COOKIES);
		httpClient.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
		return httpClient;
	}

	public static String post(String url, Map<String, String> params, Map<String, List> listParam) throws Exception {
		return post(url, params, listParam, null, null);
	}

	/**
	 * post形式请求资源(httpclient的post方法不支持302跳转，如果有需要特殊处理)
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params, Map<String, List> arrayParams, String proxyHostname, String proxyPort) throws Exception {
		HttpPost post = null;
		try {
			HttpClient httpclient = getHttpClient();
			if (StringUtils.isNotBlank(proxyHostname) && StringUtils.isNotBlank(proxyPort)) {
				HttpHost proxy = new HttpHost(proxyHostname, Integer.parseInt(proxyPort));
				httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			post = new HttpPost(url.trim());
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (params != null && params.size() > 0) {
				Iterator<Entry<String, String>> it = params.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			if (arrayParams != null && arrayParams.size() > 0) {
				Iterator<Entry<String, List>> listItem = arrayParams.entrySet().iterator();
				while (listItem.hasNext()) {
					Entry<String, List> entry = listItem.next();
					List list = entry.getValue();
					for (Object obj : list) {
						nameValuePairs.add(new BasicNameValuePair(entry.getKey(), (String) obj));
					}
				}
			}
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new HttpException(new StringBuffer("url[").append(url).append("],StatusCode=[").append(response.getStatusLine().getStatusCode()).append("]").toString());
			}
			return EntityUtils.toString(response.getEntity(), DEFAULTCHARSET);
		} catch (ClientProtocolException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}

	/**
	 * post形式请求资源(httpclient的post方法不支持302跳转，如果有需要特殊处理)
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String data) throws Exception {
		HttpPost post = null;
		try {
			HttpClient httpclient = getHttpClient();
			post = new HttpPost(url.trim());
			if (data != null) {
				post.setEntity(new StringEntity(data));
			}
			HttpResponse response = httpclient.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new HttpException(new StringBuffer("url[").append(url).append("],StatusCode=[").append(response.getStatusLine().getStatusCode()).append("]").toString());
			}
			return EntityUtils.toString(response.getEntity(), DEFAULTCHARSET);
		} catch (ClientProtocolException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}

	public static void shutdown() {
		if (cm != null)
			cm.shutdown();
	}

	public static void main(String[] args) throws Exception {
		String host = "http://localhost:8889/app/page/1"; // dev
		// String host =
		// "http://127.0.0.1:9195/adunion/mg?mg_id=2&mg_type=100&op=440&locale=JP&ls=d65e81939a800b9fccefc5bd10f6aed0";
		// // dev
		// String host =
		// "http://127.0.0.1:9195/adunion/spmg?spe_mg_id=22&spe_mg_type=102&op=238&locale=en&license=licenese";
		// // dev
		String json = HttpPoolClientUtils.get(host, null);
		System.out.print(json);

		// String host = "http://10.18.102.75:8000/dataApi/getAdData"; // dev
		// // String host =
		// "http://10.18.102.75:8000/dataApi/prepareData4Test?id=1&click_num=88&show_num=80&install_num=50";
		// Map listParams = new HashMap();
		// List list = new ArrayList();
		// list.add("1");
		// list.add("2");
		// list.add("3");
		// listParams.put("ids[]", list);
		// String json = HttpPoolClientUtils.post(host, null,listParams);
		// System.out.print(json);
	}
}