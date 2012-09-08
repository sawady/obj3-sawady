package model

object ObtenerMejorPlan {
  def apply(c: Cliente, fecha: Fecha): Plan = {

    val comunicacionesACalcular: List[TipoDeComunicacion] = comunicacionesTresMeses(c, fecha)
    val todosLosContactos: List[Int] = comunicacionesACalcular.map(c => c.nroDestino).distinct
    val todasLasCiudades: List[Ciudad] = comunicacionesACalcular.collect({ case l: Llamada => l.datos.ciudadDestino }).distinct
    

    var xs: List[Plan] = List()

    xs ::= new NumerosAmigos(numerosMasCaros(comunicacionesACalcular, todosLosContactos).take(5))
    xs ::= new CiudadesAmigas(ciudadMasCara(comunicacionesACalcular, todasLasCiudades))
    xs ::= new HablateTodo()
    
    val fechaDummy = Fecha(1,1)
    return xs.maxBy(p => new Cliente(c.numero, Map(fechaDummy -> comunicacionesACalcular), List(p)).montoAFacturar(fechaDummy))
  }	

  private def comunicacionesTresMeses(c: Cliente, fecha: Fecha): List[TipoDeComunicacion] = tresMeses(fecha).flatMap(f => c.comunicaciones(f))

  private def tresMeses(fecha: Fecha): List[Fecha] = List(Fecha(fecha.mes - 2, fecha.año), Fecha(fecha.mes - 1, fecha.año), fecha)

  def numerosMasCaros(c: List[TipoDeComunicacion], contactos: List[Int]): List[Int] = {
    return contactos.sortBy(num => montoParaCliente(c, num))
  }

  def montoParaCliente(c: List[TipoDeComunicacion], unNroDestino: Int): Int = {
    val comunicacionesConCliente: List[TipoDeComunicacion] = c.filter(c => c match {
      case l: Llamada          => l.datos.nroDestino == unNroDestino
      case sms: MensajeDeTexto => sms.nroDestino == unNroDestino
    })

    val fechaDummy   = Fecha(1,1)
    val clienteDummy = new Cliente(0, Map(fechaDummy -> comunicacionesConCliente), List(PlanBasico))
    return clienteDummy.montoAFacturar(fechaDummy)
  }

  def ciudadMasCara(c: List[TipoDeComunicacion], ciudades: List[Ciudad]): Ciudad = {
    return ciudades.maxBy(ciudad => montoParaCiudad(c, ciudad))
  }

  def montoParaCiudad(c: List[TipoDeComunicacion], ciudad: Ciudad): Int = {
    val comunicacionesConCiudad: List[TipoDeComunicacion] = c.collect({ case l: Llamada => l }).filter(c => c.datos.ciudadDestino == ciudad)

    val fechaDummy   = Fecha(1,1)
    val clienteDummy = new Cliente(1, Map(fechaDummy -> comunicacionesConCiudad), List(PlanBasico))

    return clienteDummy.montoAFacturar(fechaDummy)
  }

}