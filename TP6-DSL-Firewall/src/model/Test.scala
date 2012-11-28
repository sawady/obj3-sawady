package model

import scalaz._
import Scalaz._

object Test extends App {

  val list = List(Some(1), None, None)
  val list2: List[Option[Int]] = List()
  
  val l1 = list
  val l2 = list2.sequence
  
  println(l1)
  println(l2)

}