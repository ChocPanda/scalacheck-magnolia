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

import org.scalacheck.{ Arbitrary, Gen }
import org.scalacheck.magnolia.Utils._
import org.scalacheck.magnolia.adt._
import utest.{ ArrowAssert => _, _ }

object MagnoliaTest extends TestSuite {

  private val testSize = 50

  sealed trait TestTrait
  private lazy val expectedTestTraitGen = Gen.oneOf(expectedSimpleGen, expectedNestedGen, expectedRecursiveGen)

  final case class Simple(param1: Int, param2: String, param3: Boolean) extends TestTrait
  private lazy val expectedSimpleGen: Gen[Simple] =
    for {
      p1 <- Arbitrary.arbInt.arbitrary
      p2 <- Arbitrary.arbString.arbitrary
      p3 <- Arbitrary.arbBool.arbitrary
    } yield Simple(p1, p2, p3)

  final case class Nested(param1: Int, param2: String, param3: Simple) extends TestTrait
  private lazy val expectedNestedGen: Gen[Nested] =
    for {
      p1 <- Arbitrary.arbInt.arbitrary
      p2 <- Arbitrary.arbString.arbitrary
      p3 <- expectedSimpleGen
    } yield Nested(p1, p2, p3)

  final case class Recursive(param1: Int, param2: String, param3: TestTrait) extends TestTrait
  private lazy val expectedRecursiveGen: Gen[Recursive] =
    for {
      p1 <- Arbitrary.arbInt.arbitrary
      p2 <- Arbitrary.arbString.arbitrary
      p3 <- expectedTestTraitGen
    } yield Recursive(p1, p2, p3)

  val tests = Tests {
    "Generate" - {

      "EmptyCC" - {
        val arb = org.scalacheck.magnolia.gen[EmptyCC]
        arb.arbitrary.sample ==> Option(EmptyCC())
      }

      "Simple" - {
        val arb = org.scalacheck.magnolia.gen[Simple]
        expectedSimpleGen ==> arb.arbitrary
      }

      "Nested" - {
        val arb = org.scalacheck.magnolia.gen[Nested]
        expectedNestedGen ==> arb.arbitrary
      }

      "TestTrait" - {
        val arb = org.scalacheck.magnolia.gen[TestTrait]
        arb.arbitrary ==> arb.arbitrary
      }

      "Containers" - {

        "Vector" - {
          val arbVectorSimple = Arbitrary.arbitrary[Vector[Simple]]

          Gen.containerOf[Vector, Simple](expectedSimpleGen) ==> arbVectorSimple
        }

        "Map" - {
          val arb = for {
            s1 <- expectedSimpleGen
            s2 <- expectedSimpleGen
          } yield (s1, s2)

          val arbMap = Arbitrary.arbitrary[Map[Simple, Simple]]

          Gen.mapOf[Simple, Simple](arb) ==> arbMap
        }

        "Set" - {
          val arbSet = Arbitrary.arbitrary[Set[Simple]]

          Gen.containerOf[Set, Simple](expectedSimpleGen) ==> arbSet
        }
      }

      "ADTs" - {

        "Letter" - {
          val arb = org.scalacheck.magnolia.gen[Letter]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "Country" - {
          val arb = org.scalacheck.magnolia.gen[Country]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "Language" - {
          val arb = org.scalacheck.magnolia.gen[Language]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "Person" - {
          val arb = org.scalacheck.magnolia.gen[Person]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "Date" - {
          val arb = org.scalacheck.magnolia.gen[Date]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "DateRange" - {
          val arb = org.scalacheck.magnolia.gen[DateRange]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "Tree" - {
          val arb = org.scalacheck.magnolia.gen[Tree]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "Alphabet" - {
          val arb = org.scalacheck.magnolia.gen[Alphabet]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "Entity" - {
          val arb = org.scalacheck.magnolia.gen[Entity]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "Month" - {
          val arb = org.scalacheck.magnolia.gen[Month]
          assert(arb.arbitrary.sample =!=> arb.arbitrary.sample)
        }

        "IntGTree" - {
          val arb = org.scalacheck.magnolia.gen[GTree[Int]]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }

        "EntityGTree" - {
          val arb = org.scalacheck.magnolia.gen[GTree[Entity]]
          List.fill(testSize)(arb.arbitrary.sample).distinct.size > testSize - 3
        }
      }
    }
  }

}
