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
import org.scalacheck.Cogen
import org.scalacheck.Gen

final case class DerivedCogen[T](underlying: Cogen[T]) extends AnyVal

object DerivedCogen extends LowPriorityCogen {
  type Typeclass[T] = DerivedCogen[T]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] = DerivedCogen {
    Cogen { (seed, t) =>
      caseClass.parameters.foldLeft(seed) { (seed, param) =>
        param.typeclass.underlying.perturb(seed, param.dereference(t))
      }
    }
  }

  def dispatch[T](sealedTrait: SealedTrait[Typeclass, T]): Typeclass[T] = DerivedCogen {
    Cogen { (seed, t) =>
      sealedTrait.dispatch(t) { subtype =>
        subtype.typeclass.underlying.perturb(seed, subtype.cast(t))
      }
    }
  }

  implicit def genCogen[T]: DerivedCogen[T] = macro Magnolia.gen[T]
}

sealed trait LowPriorityCogen {
  implicit def liftCogenToDerivedCogen[T](implicit underlying: Cogen[T]): DerivedCogen[T] =
    DerivedCogen(underlying)
}
