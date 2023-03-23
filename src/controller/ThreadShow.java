package controller;

import java.util.concurrent.Semaphore;

public class ThreadShow extends Thread{
	
	private Semaphore mutex;
	private static int ingressos = 100;
	private int clienteID;
	
	public ThreadShow(Semaphore mutex, int clienteID) {
		this.mutex = mutex;
		this.clienteID = clienteID;
	}
	
	@Override
	public void run() {
		login();
	}

	private void login() {
		int tempoDeLogin = (int)((Math.random() * 1951) + 50);
		try {
			sleep(tempoDeLogin);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (tempoDeLogin > 1000) {
			System.out.println("TIMEOUT: Cliente #" + clienteID + " ultrapassou o tempo máximo de login.");

		} else {
			compra();
		}
	}

	private void compra() {
		int tempoDeCompra = (int)((Math.random() * 2001) + 1000);
		try {
			sleep(tempoDeCompra);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (tempoDeCompra > 2500) {
			System.out.println("FINAL DE TEMPO DE SESSÃO: Cliente #" + clienteID + " ultrapassou o tempo máximo de compra.");

		} else {
			int numIngressos = (int)((Math.random() * 4) + 1);
			try {
				mutex.acquire();
				validarCompra(numIngressos);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				mutex.release();
			}
		}
	}

	private void validarCompra(int numIngressos) {
		if (numIngressos <= ingressos) {
			ingressos -= numIngressos;
			System.out.println("COMPRA: Cliente #" + clienteID + " realizou a compra de " + numIngressos + " ingressos! " + ingressos + " ainda disponíveis.");
		} else {
			System.out.println("INDISPONIBILIDADE: Cliente #" + clienteID + " não pôde realizar sua compra, já que não há ingressos suficientes.");
		}
	}

}
