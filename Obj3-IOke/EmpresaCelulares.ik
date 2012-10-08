Fecha = Origin mimic
Fecha initialize = method(dia, mes,
	self dia = dia
	self mes = mes
)

Comunicacion = Origin mimic
;nro_destino
;tipo_comunicacion
;extension (duracion o longitud del texto)
;fecha

Comunicacion precio_base = method(self tipo_comunicacion precio_unitario * self extension)
Comunicacion es_llamada  = method(self tipo_comunicacion es_llamada)
Comunicacion es_larga    = method(self tipo_comunicacion es_larga(self extension))

Localidad = Origin mimic
;nombre

tabla_precios = fn(c1, c2, 
	case( (c1,c2),
		("Buenos Aires", "Lima"), 250,
		100
	)
)


Llamada = Origin mimic
Llamada es_llamada = true
Llamada es_larga = method(x, x > 5)

LlamadaLocal = Llamada mimic
LlamadaLocal precio_unitario = 50

LLamadaLargaDistancia = Llamada mimic
LLamadaLargaDistancia initialize = method(localidad_origen, localidad_destino,
	self localidad_origen = localidad_origen
	self localidad_destino = localidad_destino
)

LLamadaLargaDistancia precio_unitario = method(
	tabla_precios(self localidad_origen, self localidad_destino)
)

MensajeDeTexto = Origin mimic
MensajeDeTexto precio_unitario = 10
MensajeDeTexto es_llamada = false
MensajeDeTexto es_larga   = method(x, x > 140)

Cliente = Origin mimic
Cliente comunicaciones = {}

;La cantidad total de minutos de llamada utilizados en ese mes.
Cliente cantidad_total_minutos = method(mes, 
	self comunicaciones at(mes) filter(es_llamada) fold(0, sum, x, sum + x extension)
)

;El monto a facturar en ese mes.
Cliente montor_facturar = method(mes, 
	self comunicaciones at(mes) fold(0, sum, x, sum + x precio_base)
)

;La cantidad de comunicaciones ”largas”. Una llamada
;es larga si supera los 5 minutos, un mensaje de texto
;es largo si supera los 140 caracteres.
Cliente cantidad_comunicaciones_largas = method(mes, self comunicaciones at(mes) count(es_larga))

;Las ciudades a las que realizo llamadas de larga distancia.
Cliente ciudades_llamadas_larga_distancia = method(mes, 
	self comunicaciones at(mes) map(tipo_comunicacion) filter(mimics?(LLamadaLargaDistancia)) map(localidad_destino)
)

;El dia en el que mas comunicaciones realizo.
Cliente dia_que_mas_llamadas_realizo = method(mes,
	self comunicaciones at(mes) groupBy(fecha dia)
)

una_comunicacion_local = Comunicacion mimic
una_comunicacion_local fecha = Fecha mimic(1,2)
una_comunicacion_local nro_destino = 1169328418
una_comunicacion_local tipo_comunicacion = LlamadaLocal mimic
una_comunicacion_local extension = 10

una_comunicacion_larga = Comunicacion mimic
una_comunicacion_larga fecha = Fecha mimic(1,2)
una_comunicacion_larga nro_destino = 1169328418
una_comunicacion_larga tipo_comunicacion = LLamadaLargaDistancia mimic("Buenos Aires","Lima")
una_comunicacion_larga extension = 10

un_sms = Comunicacion mimic
un_sms fecha = Fecha mimic(2,2)
un_sms nro_destino = 1169328418
un_sms tipo_comunicacion = MensajeDeTexto mimic
un_sms extension = 10

fede = Cliente mimic
fede comunicaciones = {2 => [una_comunicacion_local, una_comunicacion_larga, un_sms]}

imprimir = fn(msj, valor, (msj + ": " + valor) println)

imprimir("Cantidad total de minutos", fede cantidad_total_minutos(2))
imprimir("Monto a facturar", fede montor_facturar(2))
imprimir("Cantidad comunicaciones largas", fede cantidad_comunicaciones_largas(2))
imprimir("Ciudades a las que realizo llamadas larga distancia", fede ciudades_llamadas_larga_distancia(2))

fede dia_que_mas_llamadas_realizo(2)