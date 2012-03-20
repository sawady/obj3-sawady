object MaybeAppl extends ApplicativeFunctor[Maybe] {
	
	implicit def wrapperMaybe[A,B](fm: Maybe[A=>B]): MaybeAppl[A,B] = new MaybeAppl(fm)
	
	def fmap[A,B](m: Maybe[A], f: A => B): Maybe[B] = {
		m match {
			case Just(x) => Just(f(x))
			case MNothing() => MNothing()
		}
	}
	
	def pure[A](a: A): Maybe[A] = Just(a)
	
	def <*>[A,B](f1: Maybe[A => B], f2: Maybe[A]): Maybe[B] = {
		f1 match {
			case Just(f) => f2 match {
				case Just(x) => Just(f(x))
				case MNothing() => MNothing()
			}
			case MNothing() => MNothing()
		}
	}

}

final class MaybeAppl[A,B](fm: Maybe[A=>B]) {
	
	  def <*>(m: Maybe[A]): Maybe[B] = MaybeAppl.<*>(fm,m)
}