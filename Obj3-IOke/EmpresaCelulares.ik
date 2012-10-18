Fecha = Origin mimic
Fecha initialize = method(dia, mes,
	self dia = dia
	self mes = mes
)
Fecha esIgual = method(x, self dia == x dia && self mes == x mes)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Comunicaciones
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Comunicacion = Origin mimic
;nro_destino
;tipo_comunicacion
;extension (duracion o longitud del texto)
;fecha
Comunicacion precio_base = method(self tipo_comunicacion precio_unitario * self extension)
Comunicacion es_llamada  = method(self tipo_comunicacion es_llamada)
Comunicacion es_larga    = method(self tipo_comunicacion es_larga(self extension))
Comunicacion reducirMinutos = method(min, 
	newCom = Comunicacion mimic
	newCom nro_destino = self nro_destino
	newCom tipo_comunicacion = self tipo_comunicacion
	newCom fecha = self fecha
	
	if(self extension > min,
		newCom extension = self extension - min
		(newCom, 0),
		
		newCom extension = 0
		(newCom, min - self extension)
	)
)

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; PLANES
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

PlanBase = Origin mimic
PlanBase aplicar_plan = method(xs, xs)
PlanBase precio_extra = 10

Planes = PlanBase mimic
;planes
Planes precio_extra  = method(self planes fold(0, r, x, r + x))
Planes aplicar_plan  = method(xs,
	self planes fold(xs, rs, p, p aplicar_plan(rs))
)

NumerosAmigos = PlanQueFiltra mimic
;numeros
NumerosAmigos aplicar_plan = method(xs, 
	xs reject(x, 
		self numeros one?(==(x nro_destino))
	)
)

CiudadesAmigas = PlanQueFiltra mimic
;ciudades
CiudadesAmigas aplicar_plan = method(xs, 
	xs reject(x,
		x tipo_comunicacion cell?(:localidad_destino) && self ciudades one?(==(x tipo_comunicacion localidad_destino))
	)
)

feriados = [Fecha mimic(1,2)]

HablateTodo = PlanQueFiltra mimic
HablateTodo aplicar_plan = method(xs, 
	xs reject(x, 
		feriados one?(esIgual(x fecha))
	)
)

Prepago = PlanBase mimic
;aplica
Prepago aplicar_plan = method(xs,
	xs collect(x,
		if(! self aplica(x),
			x,
			(newCom, min_restantes) = x reducirMinutos(self minutos_libres)
			self minutos_libres = min_restantes
			newCom
		)
	)
)

PrepagoLocal = Prepago mimic
PrepagoLocal minutos_libres = 60
PrepagoLocal aplica = method(x, x tipo_comunicacion mimics?(LlamadaLocal))

PrepagoInternacional = Prepago mimic
PrepagoInternacional minutos_libres = 30
PrepagoInternacional aplica = method(x, x tipo_comunicacion mimics?(LLamadaLargaDistancia))

mejor_plan_para = fn(c, mes,

	cliente_dummy = Cliente mimic
	cliente_dummy comunicaciones = c comunicaciones

	los_5_mejores = fn(xs,
		xs group() map(kv, (key,value) = kv. (key,value count)) sortBy(second) map(first) take(5)
	)

	plan_amigos = NumerosAmigos mimic
	plan_amigos numeros = los_5_mejores(
		cliente_dummy comunicaciones at(mes) collect(nro_destino)
	)

	plan_ciudades = CiudadesAmigas mimic
	plan_ciudades ciudades = los_5_mejores(
		cliente_dummy comunicaciones at(mes) filter(tipo_comunicacion cell?(:localidad_destino)) collect(tipo_comunicacion localidad_destino)
	)

	lista_de_planes = [plan_amigos do(nombre = "Numeros Amigos"), 
                       plan_ciudades do(nombre = "Ciudades Amigas"), 
                       HablateTodo mimic do(nombre = "Hablate Todo"), 
                       PrepagoLocal mimic do(nombre = "Prepago Local"), 
                       PrepagoInternacional mimic do(nombre = "Prepago Internacional")
                      ]

	lista_de_planes min(l, cliente_dummy plan = l. cliente_dummy monto_facturar(mes)) nombre 
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Cliente
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Cliente = Origin mimic
Cliente comunicaciones = {}
Cliente plan = PlanBase

;La cantidad total de minutos de llamada utilizados en ese mes.
Cliente cantidad_total_minutos = method(mes, 
	self comunicaciones at(mes) filter(es_llamada) fold(0, sum, x, sum + x extension)
)

;El monto a facturar en ese mes.
Cliente monto_facturar = method(mes, 
	self plan aplicar_plan(self comunicaciones at(mes)) fold(0, sum, x, sum + x precio_base) + self plan precio_extra
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
	self comunicaciones at(mes) groupBy(fecha dia) max(value length) key
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Empresa
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Empresa = Origin mimic
Empresa clientes = []

;El cliente que mas minutos hablo
Empresa cliente_mas_minutos_hablo = method(mes,
	self clientes max(cantidad_total_minutos(mes))
)

;Cliente que mas facturo
Empresa cliente_con_mayor_factura = method(mes,
	self clientes max(monto_facturar(2))
)

;El cliente con mas comunicaciones largas
Empresa cliente_con_mas_comunicaciones_largas = method(mes,
	self clientes max(cantidad_comunicaciones_largas(2))
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Ejemplos
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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
fede nombre = "Fede"

martin = Cliente mimic
martin comunicaciones = {2 => [una_comunicacion_local, un_sms, una_comunicacion_local, una_comunicacion_local, un_sms]}
martin nombre = "Martin"

movistar = Empresa mimic
movistar clientes = [fede,martin]

imprimir = fn(text, valor, (text + ": " + valor) println)

imprimir("Fede, Cantidad total de minutos", fede cantidad_total_minutos(2))
imprimir("Fede, Monto a facturar", fede monto_facturar(2))
imprimir("Fede, Cantidad comunicaciones largas", fede cantidad_comunicaciones_largas(2))
imprimir("Fede, Ciudades a las que realizo llamadas larga distancia", fede ciudades_llamadas_larga_distancia(2))
imprimir("Fede, Dia que mas comunicaciones realizo", fede dia_que_mas_llamadas_realizo(2))

imprimir("Cliente que mas minutos hablo", movistar cliente_mas_minutos_hablo(2) nombre)
imprimir("Cliente con mayor factura", movistar cliente_con_mayor_factura(2) nombre)
imprimir("Cliente con mas comunicaciones largas", movistar cliente_con_mas_comunicaciones_largas(2) nombre)

lautaro = Cliente mimic
lautaro comunicaciones = {2 => [una_comunicacion_local, una_comunicacion_larga, un_sms]}
lautaro nombre = "Lautaro"
imprimir("Lautaro con plan Base factura", lautaro monto_facturar(2))

lautaro plan = NumerosAmigos mimic do(
	numeros = [1169328418]
)
imprimir("Lautaro con plan Numeros Amigos factura", lautaro monto_facturar(2))

lautaro plan = CiudadesAmigas mimic do(
	ciudades = ["Lima"]
)
imprimir("Lautaro con plan Ciudades Amigas factura", lautaro monto_facturar(2))

lautaro plan = HablateTodo mimic
imprimir("Lautaro con plan Hablate Todo factura", lautaro monto_facturar(2))

lautaro plan = PrepagoLocal mimic
imprimir("Lautaro con plan Prepago Local factura", lautaro monto_facturar(2))

lautaro plan = PrepagoInternacional mimic
imprimir("Lautaro con plan Prepago Internacional factura", lautaro monto_facturar(2))

imprimir("Mejor Plan para Lautaro en Febrero es", mejor_plan_para(lautaro, 2))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; RESULTADOS
;Fede, Cantidad total de minutos: 20
;Fede, Monto a facturar: 3110
;Fede, Cantidad comunicaciones largas: 2
;Fede, Ciudades a las que realizo llamadas larga distancia: [Lima]
;Fede, Dia que mas comunicaciones realizo: 1
;Cliente que mas minutos hablo: Martin
;Cliente con mayor factura: Fede
;Cliente con mas comunicaciones largas: Martin
;;Lautaro con plan Base factura: 3110
;Lautaro con plan Numeros Amigos factura: 10
;Lautaro con plan Ciudades Amigas factura: 610
;Lautaro con plan Hablate Todo factura: 110
;Lautaro con plan Prepago Local factura: 2610
;Lautaro con plan Prepago Internacional factura: 610
;Mejor Plan para Lautaro en Febrero es: Numeros Amigos