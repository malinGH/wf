package com.dsc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class httpDemo {
	public static void main(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet getmethod = new HttpGet("https://www.guazi.com/foshan/ef2259a8653a9feex.htm#fr_page=list&fr_pos=city&fr_no=39");
		getmethod.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"));
		getmethod.addHeader(new BasicHeader("Cookie","antipas=3535f58j724j8053I18619x7361"));
		try {
			HttpResponse httpresponse =  httpclient.execute(getmethod);
			HttpEntity httpentity = httpresponse.getEntity();
			//System.out.println(EntityUtils.toString(httpentity,"UTF-8"));
		    System.out.println(httpresponse.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
