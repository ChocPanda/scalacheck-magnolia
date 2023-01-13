package org.scalacheck.magnolia

import _root_.magnolia._
import org.scalacheck.{ Arbitrary, Gen }

import scala.language.experimental.macros

package object semiauto {
  type Typeclass[T] = Arbitrary[T]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] =
    Arbitrary {
      Gen.lzy(caseClass.constructMonadic(param => param.typeclass.arbitrary))
    }

  def dispatch[T](sealedTrait: SealedTrait[Typeclass, T]): Typeclass[T] =
    Arbitrary {
      Gen.oneOf(sealedTrait.subtypes.map(_.typeclass.arbitrary)).flatMap(identity)
    }

  def deriveArbitrary[T]: Arbitrary[T] = macro Magnolia.gen[T]
}
