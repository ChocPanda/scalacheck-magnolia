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
