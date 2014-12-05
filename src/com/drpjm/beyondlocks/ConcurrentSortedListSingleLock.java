package com.drpjm.beyondlocks;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSortedListSingleLock {

	private class Node {
		int value;
		Node prev;
		Node next;
		
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
	
	public ConcurrentSortedListSingleLock(){
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

	// Now, the entire function insert is synchronized.
	public synchronized void insert(int newValue){
		Node current = this.head;
		// Lock down the head...
		Node next = current.next;
		while(true){
			if (next == tail || next.value < newValue){
				// insert new value in a Node between current and next!
				Node newNode = new Node(newValue, current, next);
				next.prev = newNode;
				current.next = newNode;
				return; // All done!
			}
			current = next;
			next = current.next;
		}
	}
	
	public static void main(String[] args){
		ConcurrentSortedListSingleLock newList = new ConcurrentSortedListSingleLock();
		
		newList.insert(10);
		newList.insert(1);
		newList.insert(4);
		newList.insert(11);
		
		System.out.println(newList);
	}
	
}
