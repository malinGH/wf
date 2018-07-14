package com.dsc;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;


public class HttpDemo {
	public static void main(String[] args) {
		guaziershouche();
    }
	
	
	public static void test() {
		//建立一个新的请求客户端
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	 
	    //使用HttpGet方式请求网址
	    HttpGet httpGet = new HttpGet("http://beijing.taoche.com/ajax/buycar/getrecomcars.ashx?cid=201&r=0.28224202923878017");
	    
	 
	    //获取网址的返回结果
	    CloseableHttpResponse response = null;
	    try {
	      response = httpClient.execute(httpGet);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	 
	    //获取返回结果中的实体
	    HttpEntity entity = response.getEntity();
	 
	    //将返回的实体输出
	    try {
	      System.out.println(EntityUtils.toString(entity,"UTF-8"));
	      System.out.println(response.getStatusLine().getStatusCode());
	      EntityUtils.consume(entity);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}

	
	public static void guaziershouche() {
		try {
			URI uri = new URIBuilder("https://www.guazi.com/www/buy").build();
			
			
			List<Header> headerList = new ArrayList<Header>();
		    headerList.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
//		    headerList.add(new BasicHeader(HttpHeaders.HOST,"www.guazi.com"));
//		    headerList.add(new BasicHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"));
//		    headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br"));
//		    headerList.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9"));
//		    headerList.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "max-age=0"));
		    headerList.add(new BasicHeader(HttpHeaders.USER_AGENT, 
		    		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"));	    
			headerList.add(new BasicHeader("Cookie", "antipas=4Y227082521L801G667155i846"));
		  //构造HttpClient
		    HttpClient httpClient = HttpClients.custom().setDefaultHeaders(headerList).build();
		 
		    //构造HttpGet请求
		    HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(uri).build();
		 
		    //获取结果
		    HttpResponse httpResponse = httpClient.execute(httpUriRequest);
		 
		    //获取返回结果中的实体
		    HttpEntity entity = httpResponse.getEntity();
		 
		    //查看页面内容结果
		    String rawHTMLContent = EntityUtils.toString(entity,"UTF-8");
		    System.out.println(httpResponse.getStatusLine().getStatusCode());
		    System.out.println(rawHTMLContent);
		 
		    //关闭HttpEntity流
		    EntityUtils.consume(entity);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void errortest() {
		org.apache.commons.httpclient.HttpClient httpclient = new org.apache.commons.httpclient.HttpClient();
	    GetMethod getmethod = new GetMethod("https://www.baidu.com/"); 
	    
	    httpclient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
	    try {
			int statuscode = httpclient.executeMethod(getmethod);

			Cookie[] cookies = httpclient.getState().getCookies();
			StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                System.out.println("cookies = "+c.toString());
            }
			System.out.println(statuscode);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
