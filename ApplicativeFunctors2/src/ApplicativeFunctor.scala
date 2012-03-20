abstract trait ApplicativeFunctor[F[A]] extends Functor[F] {
	
// pure id <*> v = v                            -- Identity
// pure (.) <*> u <*> v <*> w = u <*> (v <*> w) -- Composition
// pure f <*> pure x = pure (f x)               -- Homomorphism
// u <*> pure y = pure ($ y) <*> u              -- Interchange
	
	def pure[A](a: A): F[A]
	
	def <*>[A,B](f1: F[A => B], f2: F[A]): F[B]
	
}