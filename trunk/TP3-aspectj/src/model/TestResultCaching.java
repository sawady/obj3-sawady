package model;

public class TestResultCaching {

	public static void main(String[] args) {
		Funciones funcs = new Funciones();
		
		for(int i = 22; i >= 0; i--){
			System.out.println(funcs.fib(i));
		}
	}

}
