# Scalacheck Magnolia #

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b6042a90ee4947da83606933e800b122)](https://app.codacy.com/app/ChocPanda/scalacheck-magnolia?utm_source=github.com&utm_medium=referral&utm_content=ChocPanda/scalacheck-magnolia&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.org/ChocPanda/scalacheck-magnolia.svg?branch=master)](https://travis-ci.org/ChocPanda/scalacheck-magnolia)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.chocpanda/scalacheck-magnolia_2.12/badge.svg)](https://search.maven.org/search?q=a:scalacheck-magnolia_2.12)

This library will derive instances of the Arbitrary type class from [scalacheck](https://github.com/rickynils/scalacheck)
using [Magnolia](https://github.com/propensive/magnolia). The functionality would be very similar to
[scalacheck-shapeless](https://github.com/alexarchambault/scalacheck-shapeless) but hopefully with the
compile time benefits that magnolia provides over shapeless.

It's very simple to use simply add to your build.sbt:
```scala
    libraryDependencies += "com.github.chocpanda" %% "scalacheck-magnolia" % "0.2.2"
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
    
    final case class Company(name: String)         extends Entity
    final case class Human(name: String, age: Int) extends Entity
    final case class Address(line1: String, occupant: Human) extends Entity

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
