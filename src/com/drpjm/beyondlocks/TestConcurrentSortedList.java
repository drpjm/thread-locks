package com.drpjm.beyondlocks;

import java.util.Random;

public class TestConcurrentSortedList {

	public static void main(String[] args) {

		final ConcurrentSortedList myList = new ConcurrentSortedList();
		
		Thread t1 = new Thread(){
			Random rand = new Random();
			public void run() {
				while(true){
					// Tries to insert random odds!
					int randomOdd = 2*rand.nextInt(100) + 1;
					myList.insert(randomOdd);
					try {
						Thread.sleep(randomOdd * 10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread t2 = new Thread(){
			Random rand = new Random();
			public void run() {
				while(true){
					// Tries to insert random evens!
					int randomEven = 2*rand.nextInt(100);
					myList.insert(randomEven);
					try {
						Thread.sleep(randomEven * 10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread printThread = new Thread(){
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
						System.out.println(myList);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}
		};
		
		printThread.start();
		t1.start();
		t2.start();
		
		try {
			printThread.join();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
