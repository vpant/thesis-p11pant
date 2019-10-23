package org.twittercity.twittercitymod.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTaskBlocker implements ITaskBlocker {

	private final Lock lock = new ReentrantLock();
	
	@Override
	public void acquire() {
		lock.lock();
	}

	@Override
	public void release() {
		lock.unlock();
	}

}
