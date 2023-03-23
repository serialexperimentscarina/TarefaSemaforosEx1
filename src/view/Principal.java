package view;

import java.util.concurrent.Semaphore;

import controller.ThreadBilheteria;

public class Principal {

	public static void main(String[] args) {
		
		Semaphore mutex = new Semaphore(1);
		
		for (int i = 0; i < 300; i++) {
			Thread cliente = new ThreadBilheteria(mutex, (i + 1));
			cliente.start();
		}

	}

}
