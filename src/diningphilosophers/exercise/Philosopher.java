package diningphilosophers.exercise;

import java.util.Random;

public class Philosopher extends Thread {

	private Chopstick left, right;
	private Random random;
	private final int id;
	
	public Philosopher(int id, Chopstick left, Chopstick right){
		this.id = id;
		this.left = left;
		this.right = right;
		random = new Random();
	}

	@Override
	public void run() {
		try {
			while(true){

				// If they think the same amount of time, the chance for a deadlock increases!

//				Thread.sleep(random.nextInt(1000)); // THINK!
				Thread.sleep(1000); // THINK!
				synchronized(left){ // Grab left chopstick
					System.out.println("Philosopher " + this.id + "  reached LEFT.");
					synchronized(right){ // Grab right chopstick
						System.out.println("Philosopher " + this.id + "  EATING.");
						Thread.sleep(100); // EAT!
					}
				}
			}
		} catch (InterruptedException ie){}
	}
}
