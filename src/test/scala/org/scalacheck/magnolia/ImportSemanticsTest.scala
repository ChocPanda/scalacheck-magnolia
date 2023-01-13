package org.scalacheck.magnolia

import org.scalacheck.Arbitrary
import utest.TestSuite
import utest.{ArrowAssert => _, _}

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
