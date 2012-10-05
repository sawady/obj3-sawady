package model;

import java.util.ArrayList;

public class TestResultCaching {

	public static void main(String[] args) {
		Funciones funcs = new Funciones();

		for (int i = 22; i >= 0; i--) {
			System.out.println(funcs.fib(i));
		}
		
		Temperaturas temp = new Temperaturas();
		System.out.println("Primera busqueda de temperatura");
		temp.temperaturaMaxima("Quilmes");
		System.out.println("Ahora debe cacehar");
		temp.temperaturaMaxima("Quilmes");
		System.out.println("Ahora no debe cachear");
		temp.limpiarTemperatures();
		temp.temperaturaMaxima("Quilmes");
		System.out.println("Ahora vuelve a cachear");
		temp.temperaturaMaxima("Quilmes");
	}

}
