package model

object ObtenerMejorPlan {
  def apply(c: Cliente, fecha: Fecha): Plan = {

    val comunicacionesACalcular: List[Comunicacion] = comunicacionesTresMeses(c, fecha)
    val todosLosContactos: List[Int] = comunicacionesACalcular.map(c => c.nroDestino).distinct
    val todasLasCiudades: List[Ciudad] = comunicacionesACalcular.collect({ case l: Llamada => l.datos.ciudadDestino }).distinct

    object PlanNumerosAmigos extends PlanNumerosAmigos {
      val nrosLibres: List[Int] = numerosMasCaros(comunicacionesACalcular, todosLosContactos).take(5)
    }

    object PlanCiudadesAmigas extends PlanCiudadesAmigas {
      val ciudadAmiga: Ciudad = ciudadMasCara(comunicacionesACalcular, todasLasCiudades)
    }

    object PlanHablateTodo extends PlanHablateTodo

    object PrepagoInternacional extends PrepagoInternacional

    object PrepagoLocal extends PrepagoLocal

    var xs: List[Plan] = List()

    xs ::= PlanNumerosAmigos
    xs ::= PlanCiudadesAmigas
    xs ::= PlanHablateTodo
    xs ::= PrepagoInternacional
    xs ::= PrepagoLocal

    val fechaDummy = Fecha(10, 10)
    return xs.minBy(p => new Cliente(c.numero, p).registrarComunicaciones(fechaDummy, comunicacionesACalcular).montoAFacturar(fechaDummy))
  }

  private def comunicacionesTresMeses(c: Cliente, fecha: Fecha): List[Comunicacion] = tresMeses(fecha).flatMap(f => c.comunicaciones.getOrElse(f, List()))

  private def tresMeses(fecha: Fecha): List[Fecha] = List(Fecha(fecha.mes - 2 % 13, fecha.año), Fecha(fecha.mes - 1 % 13, fecha.año), fecha)

  def numerosMasCaros(c: List[Comunicacion], contactos: List[Int]): List[Int] = {
    return contactos.sortBy(num => montoParaCliente(c, num))
  }

  def montoParaCliente(c: List[Comunicacion], unNroDestino: Int): Int = {
    val comunicacionesConCliente: List[Comunicacion] = c.filter(c => c match {
      case l: Llamada          => l.datos.nroDestino == unNroDestino
      case sms: MensajeDeTexto => sms.nroDestino == unNroDestino
    })

    val fechaDummy = Fecha(10, 10)
    val clienteDummy = new Cliente(0, new Plan()).registrarComunicaciones(fechaDummy, comunicacionesConCliente)
    return clienteDummy.montoAFacturar(fechaDummy)
  }

  def ciudadMasCara(c: List[Comunicacion], ciudades: List[Ciudad]): Ciudad = {
    return ciudades.maxBy(ciudad => montoParaCiudad(c, ciudad))
  }

  def montoParaCiudad(c: List[Comunicacion], ciudad: Ciudad): Int = {
    val comunicacionesConCiudad: List[Comunicacion] = c.collect({ case l: Llamada => l }).filter(c => c.datos.ciudadDestino == ciudad)

    val fechaDummy = Fecha(10, 10)
    val clienteDummy = new Cliente(1, new Plan()).registrarComunicaciones(fechaDummy, comunicacionesConCiudad)

    return clienteDummy.montoAFacturar(fechaDummy)
  }

}