package model

import scalaz._
import Scalaz._

object Test extends App with DSL {

  val list = List(Some(1), None, None)
  val list2: List[Option[Int]] = List()
  
  val l1 = list
  val l2 = list2.sequence
  
  println(l1)
  println(l2)
  
  BLOCK LT Port(1024)
  PASS GT Port(1024) 
  BLOCK EQ Port(8080)
//  PASS EQ PORTS(80,23,25,110)
  
  BLOCK EQ SOURCE(192,168,0,19)
  
  BLOCK EQ SOURCE(192,168,0,19) EQ DEST(192,168,0,19)
  
  BLOCK EQ SOURCE(1,1,1,1)
  
  BLOCK EQ DEST(10,10,10,10) FROM SOURCE(192,1,1,1) TO SOURCE(192,1,1,10) FROM Port(80) TO Port(120)

}