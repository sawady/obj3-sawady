Dynamic = Origin mimic
Dynamic with = method(+:keyed, 
 	keyed each(param, 
		(name, value) = param;
		self name = value
	)
)

Comunicacion = Dynamic mimic
;nro_destino
;tipo_comunicacion
;extension (duracion o longitud del mensaje)

Comunicacion precio_base = method(self tipo_comunicacion precio_unitario() * self extension)

Localidad = Dynamic mimic
;nombre

TablaPreciosLargaDistancia calcular = method(250)

LlamadaLocal = Dynamic mimic
LlamadaLocal precio_unitario = method(50)

LLamadaLargaDistancia = Dynamic mimic
;localidad_origen
;localidad_destino

LLamadaLargaDistancia precio_unitario = method(
	TablaPreciosLargaDistancia calcular(self localidad_origen, self localidad_destino)
)

MensajeDeTexto = Dynamic mimic
MensajeDeTexto precio_unitario = method(10)

Empresa = Dynamic mimic
Empresa clientes = Dict []

Cliente = Dynamic mimic
Empresa comunicaciones = Dict []

;La cantidad total de minutos de llamada utilizados en ese mes.
Cliente cantidad_total_minutos = method(mes, self comunicaciones at(mes) fold(0, fn(x,y, x + y)))

fede = Cliente mimic
fede comunicaciones = [1,2,3,4,5,6,9]