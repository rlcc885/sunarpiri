package gob.pe.sunarp.extranet.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EsperarSalir extends Thread {
	
	private BufferedReader teclado;
	private EsperarSalirListener listener;
	
	public EsperarSalir(EsperarSalirListener listener) {
		this.listener = listener;
		teclado = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void run() {
		
		try {
			while (true) {
			
				System.out.println("Para parar el proceso escribir SALIR");

				String linea = teclado.readLine();
			
				if (linea.equalsIgnoreCase("SALIR")) {
					listener.salir();
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer de teclado.");
			e.printStackTrace();
		}
	}
}

