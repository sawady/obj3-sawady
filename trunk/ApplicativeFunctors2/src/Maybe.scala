abstract class Maybe[A]
case class MNothing[A] extends Maybe[A]
case class Just[A](a: A) extends Maybe[A]