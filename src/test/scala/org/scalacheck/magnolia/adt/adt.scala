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

package org.scalacheck.magnolia.adt

final case class EmptyCC()

/*
 * For testing purposes I am using the adt's used to benchmark magnolia
 * These were copied and pasted from https://github.com/propensive/magnolia/blob/master/benchmarks/2.12/src/adt.scala
 */

sealed trait Tree
final case class Leaf(value: String)             extends Tree
final case class Branch(left: Tree, right: Tree) extends Tree

sealed trait GTree[+T]
final case class GLeaf[+T](value: T)                          extends GTree[T]
final case class GBranch[+T](left: GTree[T], right: GTree[T]) extends GTree[T]

sealed trait Entity

final case class Company(name: String)         extends Entity
final case class Human(name: String, age: Int) extends Entity
final case class Address(line1: String, occupant: Human)

sealed trait Alphabet

final case class Greek(`άλφα`: Letter,
                       `βήτα`: Letter,
                       `γάμα`: Letter,
                       `δέλτ`: Letter,
                       `έψιλον`: Letter,
                       `ζήτα`: Letter,
                       `ήτ`: Letter,
                       `θήτα`: Letter)
    extends Alphabet

final case class Cyrillic(`б`: Letter, `в`: Letter, `г`: Letter, `д`: Letter, `ж`: Letter, `з`: Letter) extends Alphabet

final case class Latin(a: Letter,
                       b: Letter,
                       c: Letter,
                       d: Letter,
                       e: Letter,
                       f: Letter,
                       g: Letter,
                       h: Letter,
                       i: Letter,
                       j: Letter,
                       k: Letter,
                       l: Letter,
                       m: Letter,
                       n: Letter,
                       o: Letter,
                       p: Letter,
                       q: Letter,
                       r: Letter,
                       s: Letter,
                       t: Letter,
                       u: Letter,
                       v: Letter)
    extends Alphabet

final case class Letter(name: String, phonetic: String)
final case class Country(name: String, language: List[Language], leader: Person, existence: DateRange)
final case class Language(name: String, code: String, alphabet: Alphabet)
final case class Person(name: String, dateOfBirth: Date)
final case class Date(year: Int, month: Month, day: Int)
final case class DateRange(from: Date, toDate: Date)

sealed trait Month
case object Jan extends Month
case object Feb extends Month
case object Mar extends Month
case object Apr extends Month
case object May extends Month
case object Jun extends Month
case object Jul extends Month
case object Aug extends Month
case object Sep extends Month
case object Oct extends Month
case object Nov extends Month
case object Dec extends Month
