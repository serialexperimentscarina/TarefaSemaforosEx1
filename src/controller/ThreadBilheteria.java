package controller;

import java.util.concurrent.Semaphore;

public class ThreadBilheteria extends Thread{
	
	private Semaphore mutex;
	private static int ingressos = 100;
	private int clienteID;
	
	public ThreadBilheteria(Semaphore mutex, int clienteID) {
		this.mutex = mutex;
		this.clienteID = clienteID;
	}
	
	@Override
	public void run() {
		login();
	}

	// 1 ) Login no sistema: Processo que pode demorar de 50 ms a 2 s, sendo que, se o tempo passar de 1s,
	// ao final do tempo de espera de login, o comprador recebe uma mensagem de timeout e, por não
	// conseguir fazer o login, não poderá fazer a compra.
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

	// 2) Processo de compra: Processo que pode demorar de 1 s a 3 s, sendo que, se o tempo passar de
	// 2,5s, ao final do tempo de espera da compra, o comprador recebe uma mensagem de final de tempo
	// de sessão e, por estourar o tempo de sessão, não poderá fazer a compra
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

	// 3) Validação da compra: O sistema deve verificar se há ingressos suficientes para finalizar a
	// compra. Se houver, faz a compra e subtrai do total de ingressos disponíveis. O sistema comunica a
	// venda da quantidade de ingressos para o usuário e a quantidade de ingressos ainda disponíveis. Se
	// não houver a totalidade dos ingressos disponibiliados, o comprador recebe mensagem sobre a
	// indisponibilidade dos ingressos e, como não é possível fracionar a quantidade pedida, este perde a
	// possibilidade de compra na sessão.
	private void validarCompra(int numIngressos) {
		if (numIngressos <= ingressos) {
			ingressos -= numIngressos;
			System.out.println("COMPRA: Cliente #" + clienteID + " realizou a compra de " + numIngressos + " ingresso(s)! " + ingressos + " ainda disponíveis.");
		} else {
			System.out.println("INDISPONIBILIDADE: Cliente #" + clienteID + " não pôde realizar sua compra, já que não há ingressos suficientes.");
		}
	}

}
