package org.scalacheck.magnolia

import _root_.magnolia._
import org.scalacheck.Arbitrary
import org.scalacheck.magnolia.semiauto

import scala.language.experimental.macros

// Same as semiauto, but deriveArbitrary is implicit.
// Be very careful with this as it can lead to large compile times since macro expansions aren't cached.
// Everywhere you need an implicit Arbitrary[Foo] can regenerate the same code, prior to compilation.
// If Foo has lots of fields or subtypes and you have lots of tests it can eat up compiler time.
package object auto {
  type Typeclass[T] = semiauto.Typeclass[T]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] = semiauto.combine(caseClass)

  def dispatch[T](sealedTrait: SealedTrait[Typeclass, T]): Typeclass[T] = semiauto.dispatch(sealedTrait)

  implicit def deriveArbitrary[T]: Arbitrary[T] = macro Magnolia.gen[T]
}
