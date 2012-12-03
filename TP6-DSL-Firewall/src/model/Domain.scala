package model
import scala.collection.mutable.MutableList
import scala.util.control.Exception
import scala.collection.mutable.Queue

case class IP(byte1: Int, byte2: Int, byte3: Int, byte4: Int) {

  if (byte1 < 0 && byte1 > 255
    || byte2 < 0 && byte2 > 255
    || byte3 < 0 && byte3 > 255
    || byte4 < 0 && byte4 > 255) throw new Exception("All numbers must be between 0 and 255")

  def >(otherIP: IP): Boolean =
    byte1 > otherIP.byte1 ||
      byte1 == otherIP.byte1 && byte2 > otherIP.byte2 ||
      byte2 == otherIP.byte2 && byte3 > otherIP.byte3 ||
      byte3 == otherIP.byte3 && byte4 > otherIP.byte4

  def <(otherIP: IP): Boolean = !(this > otherIP) && (this != otherIP)

}

case class Port(port: Int) {

  if (port < 0) throw new Exception("Port must be positive")

  def >(otherPort: Port): Boolean = port > otherPort.port

  def <(otherPort: Port): Boolean = port < otherPort.port

}

case class Packet(val port: Port, val source: IP, val dest: IP, val body: String)

object LAN {

  var lan: Queue[Packet] = Queue()

  var firewall = new Firewall()

  // Deberia depender de una politica
  def incomePacket(p: Packet): Unit = {
    firewall.checkPacket(p) match {
      case None => lan.enqueue(p)
      case Some(Pass) => lan.enqueue(p)
      case Some(Block) =>
    }
  }
  
  def reset() = {
	  lan = Queue()
	  firewall = new Firewall()    
  }

}

object WAN {
  
  var toNet: Queue[Packet] = Queue()
  
  def reset() = { 
    toNet = Queue() 
  }
  
}