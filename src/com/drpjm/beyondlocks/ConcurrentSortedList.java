package com.drpjm.beyondlocks;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSortedList {

	private class Node {
		int value;
		Node prev;
		Node next;
		// This lock enables the "hand-over-hand" locking mechanism for concurrent
		// access to data within a ConcurrentSortedList.
		final ReentrantLock nodeLock = new ReentrantLock();
		
		Node(){}
		
		Node(int v,  Node p, Node n){
			this.value = v;
			this.prev = p;
			this.next = n;
		}

		@Override
		public String toString() {
			return "[" + this.value + "]";
		}
		
	}
	
	private final Node head;
	private final Node tail;
	
	public ConcurrentSortedList(){
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.prev = head;
	}
	
	@Override
	public String toString() {
		Node current = this.head;
		String output = "[HEAD]->";
		while(current != this.tail){
			
			if(current != this.head){
				output += current.toString();
			}
			current = current.next;
			
		}
		output += "->[TAIL]";
		return output;
	}

	public void insert(int newValue){
		Node current = this.head;
		// Lock down the head...
		current.nodeLock.lock();
		Node next = current.next;
		try{
			while(true){
				next.nodeLock.lock();
				try {
					if (next == tail || next.value < newValue){
						// insert new value in a Node between current and next!
						Node newNode = new Node(newValue, current, next);
						next.prev = newNode;
						current.next = newNode;
						return; // All done!
					}
				} finally {
					current.nodeLock.unlock();
				}
				current = next;
				next = current.next;
			}
		} finally {
			// always unlock the current node!
			next.nodeLock.unlock();
		}
	}
	
	public static void main(String[] args){
		ConcurrentSortedList newList = new ConcurrentSortedList();
		
		newList.insert(10);
		newList.insert(1);
		newList.insert(4);
		newList.insert(11);
		
		System.out.println(newList);
	}
	
}
