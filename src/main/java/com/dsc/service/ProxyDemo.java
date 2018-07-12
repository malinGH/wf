package com.dsc.service;

public class ProxyDemo implements ProxyDemoInterface{

	@Override
	public String addProxy() {
		// TODO Auto-generated method stub
		System.out.println("add");
		return "add";
	}

	@Override
	public String printSomething() {
		// TODO Auto-generated method stub
		String something = "减肥是不可能的！这辈子都不可能减肥的，运动又不会做，只有躺在床上才可以维持得了生活";
		System.out.println(something);
		return something;
	}
	
}
