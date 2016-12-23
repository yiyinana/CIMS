package com.base.test;

//public class MutliThreadDemo {
//	public static void main(String[] args) {
//		MutliThread m1 = new MutliThread("Window 1");
//		m1.start();
//	}
//}
//
//class MutliThread extends Thread {
//	private int ticket = 100;// 每个线程都拥有100张票
//
//	MutliThread(String name) {
//		super(name);// 调用父类带参数的构造方法
//	}
//
//	public void run() {
//		while (ticket > 0) {
//			System.out.println(ticket-- + " is saled by " + Thread.currentThread().getName());
//		}
//	}
//}

public class MutliThreadDemo {
	public static void main(String[] args) {
		MutliThread m1 = new MutliThread("Window 1");
		MutliThread m2 = new MutliThread("Window 2");
		MutliThread m3 = new MutliThread("Window 3");
		Thread t1 = new Thread(m1);
		Thread t2 = new Thread(m2);
		Thread t3 = new Thread(m3);
		t1.start();
		t2.start();
		t3.start();
	}
}

class MutliThread implements Runnable {
	private int ticket = 100;// 每个线程都拥有100张票
	private String name;

	MutliThread(String name) {
		this.name = name;
	}

	public void run() {
		while (ticket > 0) {
			System.out.println(ticket-- + " is saled by " + name);
		}
	}
}