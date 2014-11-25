package diningphilosophers;

import java.util.Random;

public class Philosopher extends Thread {

	private Chopstick left, right;
	private Random random;
	
	public Philosopher(Chopstick left, Chopstick right){
		this.left = left;
		this.right = right;
		random = new Random();
	}

	@Override
	public void run() {
		try {
			while(true){
				Thread.sleep(random.nextInt(1000)); // THINK!
				synchronized(left){ // Grab left chopstick
					synchronized(right){ // Grab right chopstick
						System.out.println("Philosopher " + (left.getId() + 1) + " eating!");
						Thread.sleep(random.nextInt(1000)); // EAT!
					}
				}
			}
		} catch (InterruptedException ie){}
	}
}
