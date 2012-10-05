package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Cacheable
public class Temperaturas {
	private List<Registro> registroDeTemperaturas = new ArrayList<Registro>();

	@CacheableFunction
	public int temperaturaMaxima(String ciudad) {
		int max = Integer.MIN_VALUE;
		
		for (Registro registro : registroDeTemperaturas) {
			if (ciudad.equals(registro.ciudad) && registro.temperatura > max) {
				max = registro.temperatura;
			}
		}
		return max;
	}
	
	public void limpiarTemperatures() {
		registroDeTemperaturas = new ArrayList<Registro>();
	}

	class Registro {
		private String ciudad;
		private Date fecha;
		private int temperatura;
	}
}
