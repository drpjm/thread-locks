package com.drpjm.beyondlocks;

import java.util.concurrent.locks.ReentrantLock;

public class Interupptible {

	public static void main(String[] args) {
		
		final ReentrantLock lock1 = new ReentrantLock();
		final ReentrantLock lock2 = new ReentrantLock();
		
		Thread t1 = new Thread(){
			public void run() {
				
				try{
					System.out.println("t1 acquire lock1...");
					lock1.lockInterruptibly();
					System.out.println("t1 lock1 complete.");
					Thread.sleep(1000);
					System.out.println("t1 acquire lock2...");
					lock2.lockInterruptibly();
					System.out.println("t1 lock2 complete.");
				} catch (InterruptedException ie) {
					System.out.println("t1 interrupted");
				}
				
			}
		};

		Thread t2 = new Thread(){
			public void run() {
				
				try{
					System.out.println("t2 acquire lock2...");
					lock2.lockInterruptibly();
					System.out.println("t2 lock2 complete.");
					Thread.sleep(1000);
					System.out.println("t2 acquire lock1...");
					lock1.lockInterruptibly();
					System.out.println("t2 lock1 complete.");
				} catch (InterruptedException ie) {
					System.out.println("t2 interrupted");
				}
				
			}
		};

		t1.start();
		t2.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t1.interrupt();
		t2.interrupt();
		try {
			t1.join(); // current thread will wait until t1 completes
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
