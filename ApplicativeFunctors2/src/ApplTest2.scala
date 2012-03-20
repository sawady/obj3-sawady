import MaybeAppl._

object ApplTest2 {

  def main(args: Array[String]): Unit = {
	  
	  def sumar: Int => Int => Int = x => y => x+y
	  
	  val m1 = Just(sumar)
	  val m2 = Just(2)
	  val m3 = Just(5)
	  
	  val m4 =  m1 <*> m2 <*> m3

  }
  
}