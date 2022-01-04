package org.twittercity.twittercitymod.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorProvider {
	private static ExecutorProvider instance = null;
	private final ExecutorService executor;
	
	private ExecutorProvider() {
		executor = Executors.newSingleThreadExecutor();
	}
	
	public static ExecutorService getExecutorService() {
		if(instance == null) {
			instance = new ExecutorProvider();
		}
		return instance.executor;
	}
}
