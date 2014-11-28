package com.drpjm.beyondlocks;

import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

	public static void main(String[] args) {
		
		FancyPhilosopher[] philosophers = new FancyPhilosopher[5];
		ReentrantLock fancyTable = new ReentrantLock();
		int length = philosophers.length;
		
		// Generate the philosophers.
		for(int i = 0; i < length; i++){
			philosophers[i] = new FancyPhilosopher(fancyTable, i);
		}
		for(int i = 0; i < length; i++){
			int leftIdx = (i+(length-1)) % 5;
			int rightIdx = (i+1+length) % 5;
//			System.out.println("id= " + i + ": left= " + leftIdx + ", right= " + rightIdx);
			philosophers[i].setLeft(philosophers[leftIdx]);
			philosophers[i].setRight(philosophers[rightIdx]);
		}
		// Finally, start up these FancyPhilosophers! They must be professors!
		for(int i = 0; i < length; i++){
			philosophers[i].start();
		}
		for(int i = 0; i < length; i++){
			try {
				philosophers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
