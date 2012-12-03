package model
import scala.collection.mutable.MutableList

case class Ip(byte1: Int, byte2: Int, byte3: Int, byte4: Int)

case class Port(port: Int) {
  
  def >(otherPort: Port): Boolean = port > otherPort.port
  
  def <(otherPort: Port): Boolean = port < otherPort.port
  
}

case class Socket(ip: Ip, port: Port)

case class Packet(val source: Socket, val dest: Socket, val body: String)

class Net {
  
  val wan: Seq[Packet]   = MutableList()
  
  val lan: Seq[Packet]   = MutableList()
  
  val firewall = new Firewall()
  
}