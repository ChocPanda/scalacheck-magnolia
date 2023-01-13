# ScalaCheck Magnolia (with auto/semi-auto semantics) #

This fork exists to add semiauto/auto derivation semantics, similar to [circe](https://github.com/circe/circe):

```scala
import org.scalacheck.Prop.forAll

case class Foo(i: Int)
case class Bar(i: Int)

// Autoderivation - equivalent to old 'gen' method.
// Arbitrary[Foo] generated twice during compilation - can be slow if Foo is large/deeply nested.
object Test1 {
  import org.scalacheck.magnolia.auto._
  
  // Implicit Arbitrary[Foo] required by forAll
  forAll { (f: Foo) => ... }  // Test1
  forAll { (f: Foo) => ... }  // Test2
}

// Instances of Arbitrary[Foo] derived once, explicitly - usually kept somewhere outside the test.
object Test2 {
  import org.scalacheck.magnolia.semiauto._
  
  implicit val arbFoo: Arbitrary[Foo]  = deriveArbitrary[Foo]

  // Implicit Arbitrary[Foo] required by forAll
  forAll { (f: Foo) => ... }  // Test1
  forAll { (f: Foo) => ... }  // Test2
}
```

I've seen this reduce compile times by over 80%.

# Instructions

It's very simple to use simply add to your build.sbt:
```scala
    libraryDependencies += "com.github.chocpanda" %% "scalacheck-magnolia" % "0.5.1"
```

and import:
```scala
   import org.scalacheck.magnolia._
```

into the relevant test files for instances of Arbitrary for all your testing needs

## Examples ##

It can derive arbitrary instances of product or sum types, and because magnolia does so recursively
it can also derive instances of nested data types.

```scala
package org.scalacheck.magnolia.example

import org.scalacheck.magnolia._
import org.scalacheck.Prop._
import utest._

object ExampleTest extends TestSuite {
    
    sealed trait Entity
    
    final case class Company(name: String)                      extends Entity
    final case class Human(name: String, age: Int)              extends Entity
    final case class Address(line1: String, occupant: Human)    extends Entity

    val tests = Tests {
        "My first test description" - forAll {
            company: Company =>
            // Test for an arbitrary company...
        }.check()
        
        "My second test description" - forAll {
            entity: Entity =>
            // Test for an arbitrary Entity... Could be any Company, Human or Address
        }.check()
    }
}
```
    
It works with recursive types:

```scala
package org.scalacheck.magnolia.example

import org.scalacheck.magnolia._
import org.scalacheck.Prop._
import utest._

object ExampleTest extends TestSuite {

    sealed trait Tree
    final case class Leaf(value: String)             extends Tree
    final case class Branch(left: Tree, right: Tree) extends Tree

    val tests = Tests {
        "My test description" - forAll {
            tree: Tree =>
            // Test for an arbitrary tree...
        }.check()
    }
}
```

It even works with Generics:

```scala
package org.scalacheck.magnolia.example

import org.scalacheck.magnolia._
import org.scalacheck.Prop._
import utest._

object ExampleTest extends TestSuite {
    
    sealed trait GTree[+T]
    final case class GLeaf[+T](value: T)                          extends GTree[T]
    final case class GBranch[+T](left: GTree[T], right: GTree[T]) extends GTree[T]

    val tests = Tests {
        "My first test description" - forAll {
            gTree: GTree[Int] =>
            // Test for an arbitrary IntTree...
        }.check()
    }
}
```

## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with
any pull requests, please state that the contribution is your original work and that you license
the work to the project under the project's open source license. Whether or not you state this
explicitly, by submitting any copyrighted material via pull request, email, or other means you
agree to license the material under the project's open source license and warrant that you have the
legal authority to do so.

## License ##

This code is open source software licensed under the
[Apache-2.0](http://www.apache.org/licenses/LICENSE-2.0) license.
