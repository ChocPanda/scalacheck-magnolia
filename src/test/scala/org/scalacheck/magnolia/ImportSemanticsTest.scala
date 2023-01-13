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

import org.scalacheck.Arbitrary
import utest.TestSuite
import utest.{ ArrowAssert => _, _ }

object ImportSemanticsTest extends TestSuite {
  case class Foo(i: Int)

  val tests: Tests = Tests {
    "Generate" - {
      "Via auto derivation" - {
        import org.scalacheck.magnolia.auto._
        val generator = Arbitrary.arbitrary[Foo]
        assert(generator.sample.isDefined)
      }

      "Via semiauto derivation" - {
        import org.scalacheck.magnolia.semiauto._
        val generator = deriveArbitrary[Foo].arbitrary
        assert(generator.sample.isDefined)
      }
    }
  }
}
