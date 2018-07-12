package com.dsc.proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;

public class Proxy implements InvocationHandler{
	
	private Object targetObject;
	
	public  Object newProxyInstance(Object targetObject){
		this.targetObject = targetObject;
		return org.springframework.cglib.proxy.Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
				targetObject.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("start------");
		arg1.invoke(arg0, arg2);
		System.out.println("end----");
		return null;
	}

}
