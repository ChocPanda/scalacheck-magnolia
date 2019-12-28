/*
 * Copyright 2018 com.github.chocpanda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scalacheck.magnolia

import _root_.magnolia._
import mercator.Monadic

import scala.language.experimental.macros
import org.scalacheck.Arbitrary
import org.scalacheck.Gen

final case class DerivedArbitrary[T](underlying: Arbitrary[T]) extends AnyVal

object DerivedArbitrary extends LowPriorityArbitrary {
  type Typeclass[T] = DerivedArbitrary[T]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] = DerivedArbitrary {
    Arbitrary {
      Gen.lzy(caseClass.constructMonadic { param =>
        param.typeclass.underlying.arbitrary
      })
    }
  }

  def dispatch[T](sealedTrait: SealedTrait[Typeclass, T]): Typeclass[T] = DerivedArbitrary {
    Arbitrary {
      Gen.oneOf(sealedTrait.subtypes.map(_.typeclass.underlying.arbitrary)).flatMap(identity)
    }
  }

  implicit private val monadicGen: Monadic[Gen] = new Monadic[Gen] {
    override def point[A](value: A): Gen[A] = Gen.const(value)

    override def flatMap[A, B](from: Gen[A])(fn: A => Gen[B]): Gen[B] = from.flatMap(fn)

    override def map[A, B](from: Gen[A])(fn: A => B): Gen[B] = from.map(fn)
  }

  implicit def genArbitrary[T]: DerivedArbitrary[T] = macro Magnolia.gen[T]
}

sealed trait LowPriorityArbitrary {
  implicit def liftArbitraryToDerivedArbitrary[T](implicit underlying: Arbitrary[T]): DerivedArbitrary[T] =
    DerivedArbitrary(underlying)
}
