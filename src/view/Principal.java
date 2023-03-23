package view;

import java.util.concurrent.Semaphore;

import controller.ThreadShow;

public class Principal {

	public static void main(String[] args) {
		
		Semaphore mutex = new Semaphore(1);
		
		for (int i = 0; i < 300; i++) {
			Thread cliente = new ThreadShow(mutex, (i + 1));
			cliente.start();
		}

	}

}
