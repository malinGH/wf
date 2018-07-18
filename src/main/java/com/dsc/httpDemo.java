package com.dsc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
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
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class httpDemo {
	public static void main(String[] args) {
		try {
			List<Header> headers = new ArrayList<Header>();
			headers.add(new BasicHeader("User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"));
			HttpHost proxy = test2();
			test1(headers,proxy);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static  String  test3(Object[] obj) throws Exception {
		String jsFile = "src/main/webapp/WEB-INF/js/guazicookie.js";
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
				String result = (String) invocable.invokeFunction("createCookie", obj);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scriptReader.close();
		}
		return null;
	}

	public static HttpHost test2() {
		HttpHost proxy = null;
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
				proxy = new HttpHost(str[0],Integer.parseInt(str[1]));
				if(isGoodProxy(proxy)) {
					return proxy;
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public static boolean isGoodProxy(HttpHost httphost ) {
		boolean isGoodProxy = false;
		List<String> checkUrls = new ArrayList<String>();
		checkUrls.add("http://www.baidu.com/");
		checkUrls.add("http://www.sina.com.cn/");
		checkUrls.add("http://www.taobao.com/");
		Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(httphost.getHostName(),httphost.getPort()));
		for(String url:checkUrls) {
			try {
				URL u = new URL(url);
				URLConnection connection = u.openConnection(proxy);
				connection.setConnectTimeout(600);
				connection.setReadTimeout(1200);
				connection.connect();
				if(CollectionUtils.isEmpty(connection.getHeaderFields())) {
					isGoodProxy = false;
					continue;
				}
				for(List<String> vs:connection.getHeaderFields().values()) {
					for(String value:vs) {
						if(value.toUpperCase().indexOf("200 OK")!=-1) {
							isGoodProxy = true;
							break;
						}
					}
				}
				if(	isGoodProxy ) {
					break;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("URL异常");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("open proxy异常");
			}
		}
		return isGoodProxy;
	}
	
	
	
	public static void test1(List<Header> headers,HttpHost proxy ) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CookieStore cookieStore = new BasicCookieStore();
		httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet getmethod = new HttpGet("https://www.guazi.com/www/buy/i7/");
		for(Header header:headers){
			getmethod.addHeader(header);
		}
		try {
			if(proxy!=null){
				RequestConfig config = RequestConfig.custom().setConnectTimeout(10000)
		                .setConnectionRequestTimeout(10000)
		                .setMaxRedirects(4)
		                .setSocketTimeout(10000)
		                .setRedirectsEnabled(true)
		                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
		                .setProxy(proxy)
		                .build();
				getmethod.setConfig(config);
				System.out.println("哈哈哈哈哈哈哈哈哈哈哈");
			}
			HttpResponse httpresponse = httpclient.execute(getmethod);
			HttpEntity httpentity = httpresponse.getEntity();
			System.out.println(httpresponse.getStatusLine().getStatusCode());
			String content = EntityUtils.toString(httpentity, "UTF-8");
			if(httpresponse.getStatusLine().getStatusCode()==203){
				String str = content;
			    String from = "value=anti(";
				int startIndex = str.indexOf(from)+from.length();
				int endIndex = str.indexOf(");var name='antipas'");
				String[] subStr = str.substring(startIndex, endIndex).split(",");
				String string = subStr[0].substring(1,subStr[0].length()-1);
				String key = subStr[1].substring(1, subStr[1].length()-1);
				Object[] obj = new Object[] {string,key};
				try {
					String cookie = test3(obj);
					getmethod.addHeader(new BasicHeader("Cookie", cookie));
					httpresponse = httpclient.execute(getmethod);
					httpentity = httpresponse.getEntity();
					
					System.out.println(EntityUtils.toString(httpentity, "UTF-8"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
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
			httpClient.execute(httpPost);
			String JSESSIONID = null;
			String cookie_user = null;
			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
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
