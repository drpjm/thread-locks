package com.drpjm;

public class Puzzle {
	static boolean answerReady = false;
	static int answer = 0;
	static Thread t1 = new Thread(){
		@Override
		public void run() {
			answer = 42;
			answerReady = true;
		}
	};
	
	static Thread t2 = new Thread(){
		@Override
		public void run() {
			if (answerReady == true)
				System.out.println("The answer is " + answer);
			else
				System.out.println("I have no idea!");
		}
	};
	public static void main(String[] args) throws InterruptedException {
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

}
