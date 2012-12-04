package model
import scala.collection.mutable.Stack
import scala.collection.mutable.Queue
import scala.collection.mutable.MutableList

trait FirewallDSL {

  // ESTADO
  
  private var firewall: Firewall = _
  private var sentences: Stack[SENTENCE] = _
  
  // FUNCION PRINCIPAL

  def onFirewall(f: Firewall): Unit => Unit = {
    sentences = Stack()
    firewall = f
    return (body: Unit) => { body; sentences foreach(_.build) } 
  }

  // SYNTACTIC SUGAR
  
  def Ports(ports: Int*): List[Port] = ports.map(Port(_)).toList

  def SOURCE(byte1: Int, byte2: Int, byte3: Int, byte4: Int): Source = new Source(IP(byte1, byte2, byte3, byte4))

  def DEST(byte1: Int, byte2: Int, byte3: Int, byte4: Int): Dest = new Dest(IP(byte1, byte2, byte3, byte4))
  
  class WILDCARD
  val * = new WILDCARD

  trait TARGET
  case class Source(ip: IP) extends TARGET
  case class Dest(ip: IP) extends TARGET
  
  // SENTENCIAS
  
  trait SENTENCE {

    var rules: MutableList[Rule] = MutableList()
    var actions: Queue[Action] = Queue()

    def addAction(act: Action): SENTENCE = {
      actions.enqueue(act)
      return this
    }

    def addRule(r: Rule): SENTENCE = {
      rules += r
      return this
    }
    
    def ALL(w: WILDCARD) = addRule(TrueRule)
    
    //////////////  PORTS //////////////

    def EQ(aPort: Port) = addRule(EqPort(aPort))

    def GT(aPort: Port) = addRule(GtPort(aPort))

    def LT(aPort: Port) = addRule(LtPort(aPort))

    def FROM(aPort: Port) = GT(aPort)

    def TO(aPort: Port) = LT(aPort)

    //////////////  IPs //////////////

    def EQ(anIP: Source) = addRule(EqIpSource(anIP.ip))

    def EQ(anIP: Dest) = addRule(EqIpDest(anIP.ip))

    def EQ(ports: List[Port]) = addRule(OneApply(ports map (EqPort(_))))

    def GT(anIP: Source) = addRule(GtIpSource(anIP.ip))

    def GT(anIP: Dest) = addRule(GtIpDest(anIP.ip))

    def LT(anIP: Source) = addRule(LtIpSource(anIP.ip))

    def LT(anIP: Dest) = addRule(LtIpDest(anIP.ip))

    def FROM(anIP: Source) = GT(anIP)

    def FROM(anIP: Dest) = GT(anIP)

    def TO(anIP: Source) = LT(anIP)

    def TO(anIP: Dest) = LT(anIP)
    
    def SOURCE(w: WILDCARD) = FROM(new Source(IP(0, 0, 0, 0))) TO (new Source(IP(255, 255, 255, 255)))

    def SOURCE(w: WILDCARD, byte2: Int, byte3: Int, byte4: Int) = FROM(new Source(IP(0, byte2, byte3, byte4))) TO (new Source(IP(255, byte2, byte3, byte4)))

    def SOURCE(byte1: Int, w: WILDCARD, byte3: Int, byte4: Int) = FROM(new Source(IP(byte1, 0, byte3, byte4))) TO (new Source(IP(byte1, 255, byte3, byte4)))

    def SOURCE(byte1: Int, byte2: Int, w: WILDCARD, byte4: Int) = FROM(new Source(IP(byte1, byte2, 0, byte4))) TO (new Source(IP(byte1, byte2, 255, byte4)))

    def SOURCE(byte1: Int, byte2: Int, byte3: Int, w: WILDCARD) = FROM(new Source(IP(byte1, byte2, byte3, 0))) TO (new Source(IP(byte1, byte2, byte3, 255)))

    def DEST(w: WILDCARD) = FROM(new Dest(IP(0, 0, 0, 0))) TO (new Dest(IP(255, 255, 255, 255)))
    
    def DEST(w: WILDCARD, byte2: Int, byte3: Int, byte4: Int) = FROM(new Dest(IP(0, byte2, byte3, byte4))) TO (new Dest(IP(255, byte2, byte3, byte4)))

    def DEST(byte1: Int, w: WILDCARD, byte3: Int, byte4: Int) = FROM(new Dest(IP(byte1, 0, byte3, byte4))) TO (new Dest(IP(byte1, 255, byte3, byte4)))

    def DEST(byte1: Int, byte2: Int, w: WILDCARD, byte4: Int) = FROM(new Dest(IP(byte1, byte2, 0, byte4))) TO (new Dest(IP(byte1, byte2, 255, byte4)))

    def DEST(byte1: Int, byte2: Int, byte3: Int, w: WILDCARD) = FROM(new Dest(IP(byte1, byte2, byte3, 0))) TO (new Dest(IP(byte1, byte2, byte3, 255)))

    ////////////// ACTIONS //////////////

    def LOG = addAction(LogMessage)

    def LOG_DENY = addAction(LogDeny)

    def INFORM_DEST = addAction(InformDest)

    def REDIRECT(byte1: Int, byte2: Int, byte3: Int, byte4: Int) = addAction(Redirect(IP(byte1, byte2, byte3, byte4)))

    ////////////////////////////////////

    def build = {
      firewall.add(new FirewallRule(AllApply(rules.seq), this.ruleType, new JoinActions(actions)))
    }

    def ruleType: RuleType

  }
  
  def BLOCK = new BLOCK
  
  def PASS = new PASS
  
  class BLOCK extends SENTENCE {
    
    sentences.push(this)

    def ruleType = Block

  }

  class PASS extends SENTENCE {
    
    sentences.push(this)

    def ruleType = Pass
  }

}