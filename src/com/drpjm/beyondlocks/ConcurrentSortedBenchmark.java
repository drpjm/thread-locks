package com.drpjm.beyondlocks;

import java.util.Random;

/* 
 * This class assesses the performance of ConcurrentSortedList with "hand-over-hand"
 * node locking vs. the use of a single lock on the insert() function. This synchronous
 * version is ConcurrentSortedListSingleLock.
 */
public class ConcurrentSortedBenchmark {

	public static void main(String[] args) {

		final ConcurrentSortedList myList = new ConcurrentSortedList();
		final ConcurrentSortedListSingleLock myListSingleLock = new ConcurrentSortedListSingleLock();
		final int maxCount = 2000;
		
		Thread oddThread = new Thread(){
			int runCount = 0;
			Random rand = new Random();
			public void run() {
				while(true){
					// Tries to insert random odds!
					int randomOdd = 2*rand.nextInt(100) + 1;
					myList.insert(randomOdd);
					runCount++;
					if(runCount > maxCount){
						return;
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread evenThread = new Thread(){
			int runCount = 0;
			Random rand = new Random();
			public void run() {
				while(true){
					// Tries to insert random evens!
					int randomEven = 2*rand.nextInt(100);
					myList.insert(randomEven);
					runCount++;
					if(runCount > maxCount){
						return;
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		long startTime = System.currentTimeMillis();
		oddThread.start();
		evenThread.start();
		
		try {
			oddThread.join();
			evenThread.join();
			long completeTime = System.currentTimeMillis();
			System.out.println("ConcurrentSortedList: Two threads inserted " + maxCount + " numbers in " + (completeTime-startTime) + " ms.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread oddThreadRedux = new Thread(){
			int runCount = 0;
			Random rand = new Random();
			public void run() {
				while(true){
					// Tries to insert random odds!
					int randomOdd = 2*rand.nextInt(100) + 1;
					myListSingleLock.insert(randomOdd);
					runCount++;
					if(runCount > maxCount){
						return;
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread evenThreadRedux = new Thread(){
			int runCount = 0;
			Random rand = new Random();
			public void run() {
				while(true){
					// Tries to insert random evens!
					int randomEven = 2*rand.nextInt(100);
					myListSingleLock.insert(randomEven);
					runCount++;
					if(runCount > maxCount){
						return;
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		startTime = System.currentTimeMillis();
		oddThreadRedux.start();
		evenThreadRedux.start();

		try {
			oddThreadRedux.join();
			evenThreadRedux.join();
			long completeTime = System.currentTimeMillis();
			System.out.println("ConcurrentSortedListSingleLock: Two threads inserted " + maxCount + " numbers in " + (completeTime-startTime) + " ms.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
