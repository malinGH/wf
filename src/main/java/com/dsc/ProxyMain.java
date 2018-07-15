package com.dsc;

import java.lang.reflect.Method;

import com.dsc.proxy.Proxy;
import com.dsc.service.ProxyDemo;
import com.dsc.service.ProxyDemoInterface;

public class ProxyMain {
	public static void main(String[] args) {
		Proxy proxy = new Proxy();
	//ProxyDemoInterface pi = (ProxyDemoInterface) proxy.newProxyInstance(new ProxyDemo());
		
		ProxyDemoInterface pi = new ProxyDemo();
		try {
			proxy.invoke(pi, pi.getClass().getMethod("printSomething", null), null);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
			
		}
	}
}
