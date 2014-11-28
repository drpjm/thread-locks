package com.drpjm.beyondlocks;

import java.util.Random;
import java.util.concurrent.locks.*;

public class FancyPhilosopher extends Thread {

	private final int id;
	private boolean isEating;
	private FancyPhilosopher leftPhilosopher;
	private FancyPhilosopher rightPhilosopher;
	private ReentrantLock table;
	private Condition tableCondition;
	private Random random;
	
	public FancyPhilosopher(ReentrantLock table, int id){
		this.id = id;
		this.table = table;
		this.isEating = false;
		this.tableCondition = table.newCondition();
		this.random = new Random();
	}
	
	public long getId() {
		return id;
	}

	public void setLeft(FancyPhilosopher left){
		this.leftPhilosopher = left;
	}
	public void setRight(FancyPhilosopher right){
		this.rightPhilosopher = right;
	}
	
	public void run(){
		try{
			while(true){
				think();
				eat();
			}
		} catch (InterruptedException ie){
			ie.printStackTrace();
		}
	}

	private void eat() throws InterruptedException {
		System.out.println("Philosopher " + id + " wants to eat.");
		table.lock();
		try{
			while(leftPhilosopher.isEating || rightPhilosopher.isEating){
				tableCondition.await();
			}
			this.isEating = true;
			int eatTime = random.nextInt(1000);
			System.out.println("Philosopher " + id + " is eating for " + eatTime + "ms");
			Thread.sleep(eatTime);
		} finally {
			table.unlock();
		}
	}

	private void think() throws InterruptedException {
		table.lock();
		try {
			isEating = false;
			this.leftPhilosopher.tableCondition.signal();
			this.rightPhilosopher.tableCondition.signal();
		} finally {
			table.unlock();
		}
		int thinkTime = random.nextInt(2000);
		System.out.println("Philosopher " + id + " is thinking for " + thinkTime + "ms");
		Thread.sleep(thinkTime);
	}
}
