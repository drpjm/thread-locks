package diningphilosophers;

import java.util.Random;

public class Philosopher extends Thread {

	private Chopstick first, second;
	private Random random;
	
	public Philosopher(Chopstick left, Chopstick right){
		if(left.getId() < right.getId()){
			this.first = left;
			this.second = right;
		}
		else{
			this.first = right;
			this.second = left;
		}
		random = new Random();
	}

	@Override
	public void run() {
		try {
			while(true){
				Thread.sleep(random.nextInt(1000)); // THINK!
				synchronized(first){ // Grab left chopstick
					synchronized(second){ // Grab right chopstick
						System.out.println("Philosopher eating with chopsticks " + "(" + first.getId() + "," + second.getId() + ")");
						Thread.sleep(random.nextInt(1000)); // EAT!
					}
				}
			}
		} catch (InterruptedException ie){}
	}
}
