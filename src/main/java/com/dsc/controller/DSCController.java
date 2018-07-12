package com.dsc.controller;

import java.util.concurrent.Executor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class DSCController {
	public static void main(String[] args) {
		Executor executor = new ThreadPoolTaskExecutor(); 
	}
}
