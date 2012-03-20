trait Functor[F[A]] {
	
	def fmap[A,B](ftor: F[A], f: A => B): F[B]

}