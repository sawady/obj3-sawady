package model

// -1 para el ultimo byte significa *

case class IP(byte1: Int, byte2: Int, byte3: Int, byte4: Int)

case class IPRange(ip1: IP, ip2: IP) {
  
  def isInRange(ip: IP): Boolean = true
  
}