package diningphilosophers.exercise;

public class DiningPhilosophers {

	public static void main(String[] args) {
		
		// create the chopsticks
		Chopstick[] chopSticks = new Chopstick[5];
		Philosopher[] philosophers = new Philosopher[5];
		
		for(int i = 0; i < chopSticks.length; i++){
			chopSticks[i] = new Chopstick(i);
		}
		for(int i = 0; i < philosophers.length; i++){
			philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i+1) % 5]);
			philosophers[i].start();
		}
		for(int i = 0; i < philosophers.length; i++){
			try {
				philosophers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
