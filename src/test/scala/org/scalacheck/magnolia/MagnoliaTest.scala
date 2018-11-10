/*
 * Copyright 2018 Matt Searle
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

import org.scalacheck.Arbitrary
import org.scalacheck.magnolia.Utils._
import org.scalacheck.magnolia.adt._
import utest.{ ArrowAssert => _, _ }

object MagnoliaTest extends TestSuite {

  private val testSize = 50

  val tests = Tests {
    'Generate - {
      'EmptyCC - {
        val arb = Arbitrary.arbitrary[EmptyCC]
        arb.sample ==> Option(EmptyCC())
      }

      'Letter - {
        val arb = Arbitrary.arbitrary[Letter]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'Country - {
        val arb = Arbitrary.arbitrary[Country]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'Language - {
        val arb = Arbitrary.arbitrary[Language]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'Person - {
        val arb = Arbitrary.arbitrary[Person]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'Date - {
        val arb = Arbitrary.arbitrary[Date]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'DateRange - {
        val arb = Arbitrary.arbitrary[DateRange]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'Tree - {
        val arb = Arbitrary.arbitrary[Tree]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'Alphabet - {
        val arb = Arbitrary.arbitrary[Alphabet]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'Entity - {
        val arb = Arbitrary.arbitrary[Entity]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'Month - {
        val arb = Arbitrary.arbitrary[Month]
        assert(arb.sample != arb.sample)
      }

      'IntGTree - {
        val arb = Arbitrary.arbitrary[GTree[Int]]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }

      'EntityGTree - {
        val arb = Arbitrary.arbitrary[GTree[Entity]]
        List.fill(testSize)(arb.sample).distinct.size ==> testSize
      }
    }
  }

}
