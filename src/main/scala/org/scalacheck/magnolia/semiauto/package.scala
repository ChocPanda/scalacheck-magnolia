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
