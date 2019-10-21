package org.twittercity.twittercitymod.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorProvider {
	private final static int NUMBER_OF_THREADS_IN_POOL = 4;
	private static ExecutorProvider instance = null;
	private ExecutorService executor = null;
	
	private ExecutorProvider() {
		executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS_IN_POOL);
	}
	
	public static ExecutorService getExecutorService() {
		if(instance == null) {
			instance = new ExecutorProvider();
		}
		return instance.executor;
	}
}
