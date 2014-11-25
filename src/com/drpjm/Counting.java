package com.drpjm;

public class Counting {

	public static void main(String[] args) throws InterruptedException {
		class Counter {
			private int count = 0;
			// adding sychronized keyword makes the increment() function enforce mutual exclusion.
			public synchronized void increment(){
				count++;
			}
			public int getCount() {
				return count;
			}
		}
		final Counter counter = new Counter();
		
		class CountingThread extends Thread {
			@Override
			public void run() {
				for(int i = 0; i < 10000; i++){
					counter.increment();
				}
			}
		}
		CountingThread ct1 = new CountingThread();
		CountingThread ct2 = new CountingThread();
		ct1.start();
		ct2.start();
		ct1.join();
		ct2.join();
		System.out.println(counter.getCount());
	}
}
