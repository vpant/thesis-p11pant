package org.twittercity.twittercitymod.concurrency;

public interface ITaskBlocker {
	void acquire();
	
	void release();
}
