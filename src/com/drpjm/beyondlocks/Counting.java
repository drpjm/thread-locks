package com.drpjm.beyondlocks;

import java.util.concurrent.atomic.AtomicInteger;

public class Counting {

	public static void main(String[] args) throws InterruptedException {
		
		final AtomicInteger counter = new AtomicInteger();
		
		class CountingThread extends Thread {
			@Override
			public void run() {
				for(int i = 0; i < 10000; i++){
					counter.incrementAndGet();
				}
			}
		}
		CountingThread ct1 = new CountingThread();
		CountingThread ct2 = new CountingThread();
		ct1.start();
		ct2.start();
		ct1.join();
		ct2.join();
		System.out.println(counter.get());
	}
}
