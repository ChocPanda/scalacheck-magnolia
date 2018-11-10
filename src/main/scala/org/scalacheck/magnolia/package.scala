package org.scalacheck

import _root_.magnolia._
import mercator.Monadic

import scala.language.experimental.macros

package object magnolia {

  type Typeclass[T] = Arbitrary[T]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] = ???

  def dispatch[T](sealedTrait: SealedTrait[Typeclass, T]): Typeclass[T] = ???

  private implicit val monadicGen: Monadic[Gen] = ???

  def gen[T]: Typeclass[T] = macro Magnolia.gen[T]
}
