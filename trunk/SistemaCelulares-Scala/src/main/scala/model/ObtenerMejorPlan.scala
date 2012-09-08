package model

object ObtenerMejorPlan {
  // Por el momento solo calcula para una fecha determinada el mejor plan
  def apply(c: Cliente, fecha: Fecha): Plan = {
    
    var xs: List[Plan] = List()
    
    xs ::= new NumerosAmigos(numerosMasCaros(c, todosLosContactos(c, fecha), fecha).take(5))
    xs ::= new CiudadesAmigas(ciudadMasCara(c, todosLasCiudades(c, fecha), fecha))
    xs ::= new HablateTodo()
    
    return xs.maxBy(p => new Cliente(c.numero, c.comunicaciones, List(p)).montoAFacturar(fecha))
  }
  
  def todosLosContactos(c: Cliente, fecha: Fecha): List[Int] = {
    return c.comunicaciones(fecha).map(c => c.nroDestino).distinct
  }
  
  def numerosMasCaros(c: Cliente, contactos: List[Int], fecha: Fecha): List[Int] = {
    return contactos.sortBy(num => montoParaCliente(c, num, fecha)) 
  }
  
  def montoParaCliente(c: Cliente, unNroDestino: Int, fecha: Fecha): Int = {
    val comunicacionesConCliente: List[TipoDeComunicacion] = c.comunicaciones(fecha).filter(c => c match {
      case l: Llamada => l.datos.nroDestino == unNroDestino
      case sms: MensajeDeTexto => sms.nroDestino == unNroDestino
    })
    
    val clienteDummy = new Cliente(c.numero, Map(fecha -> comunicacionesConCliente), List(PlanBasico))
    
    return clienteDummy.montoAFacturar(fecha)
  }
  
  def todosLasCiudades(c: Cliente, fecha: Fecha): List[Ciudad] = {
    return c.comunicaciones(fecha).collect({case l: Llamada => l.datos.ciudadDestino}).distinct
  }
  
  def ciudadMasCara(c: Cliente, ciudades: List[Ciudad], fecha: Fecha): Ciudad = {
    return ciudades.maxBy(ciudad => montoParaCiudad(c, ciudad, fecha)) 
  }
  
  def montoParaCiudad(c: Cliente, ciudad: Ciudad, fecha: Fecha): Int = {
    val comunicacionesConCiudad: List[TipoDeComunicacion] = c.comunicaciones(fecha).collect({case l: Llamada => l}).filter(c => c.datos.ciudadDestino == ciudad)
    
    val clienteDummy = new Cliente(c.numero, Map(fecha -> comunicacionesConCiudad), List(PlanBasico))
    
    return clienteDummy.montoAFacturar(fecha)
  }
  
}