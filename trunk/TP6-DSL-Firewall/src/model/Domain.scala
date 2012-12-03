package model
import scala.collection.mutable.MutableList

case class IP(byte1: Int, byte2: Int, byte3: Int, byte4: Int) {

  def >(otherIP: IP): Boolean =
    byte1 > otherIP.byte1 || 
    byte1 == otherIP.byte1 && byte2 > otherIP.byte2 || 
    byte2 == otherIP.byte2 && byte3 > otherIP.byte3 || 
    byte3 == otherIP.byte3 && byte4 > otherIP.byte4
    
  def <(otherIP: IP): Boolean = !(this > otherIP) && (this != otherIP)
    

}

case class Port(port: Int) {

  def >(otherPort: Port): Boolean = port > otherPort.port

  def <(otherPort: Port): Boolean = port < otherPort.port

}

case class Socket(ip: IP, port: Port)

case class Packet(val port: Port, val source: IP, val dest: IP, val body: String)

class Net {

  val wan: Seq[Packet] = MutableList()

  val lan: Seq[Packet] = MutableList()

  val firewall = new Firewall()

}