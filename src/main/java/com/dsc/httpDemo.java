package com.dsc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class httpDemo {
	public static void main(String[] args) {

		try {
			test1();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void test3() throws Exception {
		String jsFile = "/Users/fanwang/eclipseProjects/wf/src/main/webapp/WEB-INF/js/guazicookie.js";
		// 得到一个ScriptEngine对象
		ScriptEngineManager maneger = new ScriptEngineManager();
		ScriptEngine engine = maneger.getEngineByName("JavaScript");

		FileInputStream fileInputStream = new FileInputStream(new File(jsFile));
		Reader scriptReader = new InputStreamReader(fileInputStream, "utf-8");
		try {
			engine.eval(scriptReader);
			if (engine instanceof Invocable) {
				// 调用JS方法
				Invocable invocable = (Invocable) engine;
				String result = (String) invocable.invokeFunction("wf");
				System.out.println(result);
				System.out.println(result.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scriptReader.close();
		}

		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// HttpGet httpget = new HttpGet("https://www.guazi.com/hz/buy/");
		// try {
		// HttpResponse httpresponse = httpclient.execute(httpget);
		// HttpEntity entity = httpresponse.getEntity();
		// System.out.println(EntityUtils.toString(entity,"UTF-8"));
		// } catch (ClientProtocolException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public static void test2() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://proxypool.sqaproxy.souche.com/api/display?group=1");
		try {
			HttpResponse httpresponse = httpclient.execute(httpget);
			HttpEntity entity = httpresponse.getEntity();
			JSONObject json = JSONObject.parseObject(EntityUtils.toString(entity));
			Set<Entry<String, Object>> entrySet = json.entrySet();
			for (Entry entry : entrySet) {
				String[] str = entry.getValue().toString().split(":");
				System.out.println("host:" + str[0] + " port:" + str[1]);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void test1() {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CookieStore cookieStore = new BasicCookieStore();
		httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		// HttpHost proxy = new HttpHost("218.93.191.132",809);
		HttpGet getmethod = new HttpGet("https://www.guazi.com/hz/buy/");
		getmethod.addHeader(new BasicHeader("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"));
		getmethod.addHeader(new BasicHeader("Cookie","antipas=25347N650691848Be58Xh6435Y900"));
		//getmethod.addHeader(new BasicHeader("Host","www.guazi.com"));
		// getmethod.addHeader(new BasicHeader("expires","Thu, 16 Aug 2018 15:10:58 GMT"));
		// TimeUnit.MINUTES.sleep(10);
		try {
			// httpclient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			HttpResponse httpresponse = httpclient.execute(getmethod);
			HttpEntity httpentity = httpresponse.getEntity();

			System.out.println(httpresponse.getStatusLine().getStatusCode());
			System.out.println(EntityUtils.toString(httpentity, "UTF-8"));
			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println(cookies.get(i).getValue());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void test() {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			CookieStore cookieStore = new BasicCookieStore();
			httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			httpPost = new HttpPost("https://www.guazi.com/hz/buy/");
			// List<NameValuePair> list = new ArrayList<NameValuePair>();
			// Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
			// while (iterator.hasNext()) {
			// Entry<String, String> elem = (Entry<String, String>) iterator.next();
			// list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			// }
			// if (list.size() > 0) {
			// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
			// httpPost.setEntity(entity);
			// }
			httpClient.execute(httpPost);
			String JSESSIONID = null;
			String cookie_user = null;
			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println(1);
				if (cookies.get(i).getName().equals("JSESSIONID")) {
					JSESSIONID = cookies.get(i).getValue();
				}
				if (cookies.get(i).getName().equals("cookie_user")) {
					cookie_user = cookies.get(i).getValue();
				}
			}
			if (cookie_user != null) {
				result = JSESSIONID;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
