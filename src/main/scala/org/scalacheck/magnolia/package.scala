package org.scalacheck

package object magnolia {
  implicit def arbitraryFromDerivedArbitrary[T](implicit derived: DerivedArbitrary[T]): Arbitrary[T] =
    derived.underlying
}
