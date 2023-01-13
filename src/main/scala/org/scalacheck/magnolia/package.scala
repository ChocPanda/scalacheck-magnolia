package org.scalacheck

import mercator.Monadic

package object magnolia {
  private[magnolia] implicit val monadicGen: Monadic[Gen] = new Monadic[Gen] {
    override def point[A](value: A): Gen[A] = Gen.const(value)

    override def flatMap[A, B](from: Gen[A])(fn: A => Gen[B]): Gen[B] = from.flatMap(fn)

    override def map[A, B](from: Gen[A])(fn: A => B): Gen[B] = from.map(fn)
  }
}
