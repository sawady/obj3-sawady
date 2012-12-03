package model

trait DSL {
  
  private val fireWallConfig = new Firewall()
  
  def Ports(ports: Int*): List[Port] = ports.map(Port(_)).toList
 
  trait IP_TYPE 
  
  case class SOURCE(byte1: Int, byte2: Int, byte3: Int, byte4: Int) extends IP_TYPE
  case class DEST(byte1: Int, byte2: Int, byte3: Int, byte4: Int) extends IP_TYPE
  
  trait SENTENCE {
    
    var rules: List[Rule] = _
    var action: Action = NullAction
    
    def ALL: SENTENCE = {
      return this
    }

    def EQ(tg: IP_TYPE): SENTENCE = {
      return this
    }
    
    def EQ(port: Port): SENTENCE = {
      return this
    }
    
    def EQ(ports: List[Port]): SENTENCE = {
      return this
    }
    
    def GT(anIP: IP_TYPE): SENTENCE = {
      return this
    }
    
    def GT(aPort: Port): SENTENCE = {
      return this
    }
    
    def LT(anIP: IP_TYPE): SENTENCE = {
      return this
    }
    
    def LT(aPort: Port): SENTENCE = {
      return this
    }
    
    def FROM(anIP: IP_TYPE) = GT(anIP)
    
    def TO(anIP: IP_TYPE) = LT(anIP)    
    
    def FROM(aPort: Port) = GT(aPort)
    
    def TO(aPort: Port) = LT(aPort)    
    
//    def build: FirewallRule
    
  }
  
  object BLOCK extends SENTENCE
  
  object PASS extends SENTENCE
  
}