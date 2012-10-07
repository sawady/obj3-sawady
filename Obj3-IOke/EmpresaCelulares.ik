Comunicacion = Origin mimic
;nro_destino
;tipo_comunicacion
;extension (duracion o longitud del mensaje)

Comunicacion precio_base = method(self tipo_comunicacion precio_unitario * self extension)

Localidad = Origin mimic
;nombre

TablaPreciosLargaDistancia = Origin mimic
TablaPreciosLargaDistancia calcular = method(x, y, 250)

LlamadaLocal = Origin mimic
LlamadaLocal precio_unitario = method(50)

LLamadaLargaDistancia = Origin mimic
LLamadaLargaDistancia initialize = method(localidad_origen, localidad_destino,
	self localidad_origen = localidad_origen
	self localidad_destino = localidad_destino
)

LLamadaLargaDistancia precio_unitario = method(
	TablaPreciosLargaDistancia calcular(self localidad_origen, self localidad_destino)
)

MensajeDeTexto = Origin mimic
MensajeDeTexto precio_unitario = method(10)

Empresa = Origin mimic
Empresa clientes = {}

Cliente = Origin mimic
Cliente comunicaciones = {}

;La cantidad total de minutos de llamada utilizados en ese mes.
Cliente cantidad_total_minutos = method(mes, self comunicaciones at(mes) fold(0, sum, x, sum + x precio_base))

una_comunicacion_local = Comunicacion mimic
una_comunicacion_local nro_destino = 1169328418
una_comunicacion_local tipo_comunicacion = LlamadaLocal mimic
una_comunicacion_local extension = 10

una_comunicacion_larga = Comunicacion mimic
una_comunicacion_larga nro_destino = 1169328418
una_comunicacion_larga tipo_comunicacion = LLamadaLargaDistancia mimic("Quilmes","Buenos Aires")
una_comunicacion_larga extension = 10

un_sms = Comunicacion mimic
un_sms nro_destino = 1169328418
un_sms tipo_comunicacion = MensajeDeTexto mimic
un_sms extension = 10

fede = Cliente mimic
fede comunicaciones = {2 => [una_comunicacion_local, una_comunicacion_larga, un_sms]} 

fede cantidad_total_minutos(2)