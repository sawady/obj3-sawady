package model;

@Cacheable
public class Funciones {
	
	@CacheableFunction
	public int fib(int n) {
		return n > 2 ? fib(n - 1) + fib(n - 2) : 1;
	}

	@CacheableFunction
	public int ack(int m, int n) {
		if (m == 0) {
			return n + 1;
		} else if (n == 0) {
			return ack(m - 1, 1);
		} else {
			return ack(m - 1, ack(m, n - 1));
		}
	}
	
}