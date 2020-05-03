package com.ex.multithreading;

class H2O {

	private final Object lock = new Object();
	private int hCount = 0;
	private int oCount = 0;


	public H2O() {

	}

	public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

		synchronized (lock) {

			while(hCount >= 2) {
				lock.wait();
			}
			// releaseHydrogen.run() outputs "H". Do not change or remove this line.
			releaseHydrogen.run();
			hCount++;
			countReset();
			lock.notifyAll();
		}
	}

	public void oxygen(Runnable releaseOxygen) throws InterruptedException {

		synchronized (lock) {

			while(oCount >= 1) {
				lock.wait();
			}
			// releaseOxygen.run() outputs "O". Do not change or remove this line.
			releaseOxygen.run();
			oCount++;
			countReset();
			lock.notifyAll();
		}
		
	}
	
	public void countReset() {
		if(hCount>=2 && oCount>=1) {
			hCount = 0;
            oCount = 0;
		}
	}
	
}
