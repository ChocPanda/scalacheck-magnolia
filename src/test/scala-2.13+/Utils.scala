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

import org.scalacheck.Gen.Parameters
import org.scalacheck._
import org.scalacheck.rng.Seed

object Utils {
  /**
    * This was largely copy and pasted from:
    * https://github.com/lihaoyi/utest/blob/5b382ae0a4bb3a25d8cb64d332b7bcb7fc73ace2/utest/shared/src/main/scala/utest/asserts/Asserts.scala#L185
    *
    * I have just extended it a little for interoperability with org.scalacheck by returning true at the end
    */
  implicit class ArrowAssert[T](lhs: T) {
    def ==>[V](rhs: V): Boolean = {
      (lhs, rhs) match {
        // Hack to make Arrays compare sanely; at some point we may want some
        // custom, extensible, typesafe equality check but for now this will do
        case (lhs: Array[_], rhs: Array[_]) =>
          Predef.assert(lhs.toSeq == rhs.toSeq, s"==> assertion failed: ${lhs.toSeq} != ${rhs.toSeq}")
        case (lhs: Gen[_], rhs: Gen[_]) =>
          compareGen(lhs, rhs)(50)
        case (lhs: Arbitrary[_], rhs: Arbitrary[_]) =>
          compareGen(lhs.arbitrary, rhs.arbitrary)(50)
        case (_, _) =>
          Predef.assert(lhs == rhs, s"==> assertion failed: $lhs != $rhs")
      }

      true
    }

    def =!=>[V](rhs: V): Boolean = {
      (lhs, rhs) match {
        // Hack to make Arrays compare sanely; at some point we may want some
        // custom, extensible, typesafe equality check but for now this will do
        case (lhs: Array[_], rhs: Array[_]) =>
          Predef.assert(lhs.toSeq != rhs.toSeq, s"==> assertion failed: ${lhs.toSeq} != ${rhs.toSeq}")
        case (lhs: Gen[_], rhs: Gen[_]) =>
          compareGenNot(lhs, rhs)(50)
        case (lhs: Arbitrary[_], rhs: Arbitrary[_]) =>
          compareGenNot(lhs.arbitrary, rhs.arbitrary)(50)
        case (_, _) =>
          Predef.assert(lhs != rhs, s"==> assertion failed: $lhs != $rhs")
      }

      true
    }
  }

  def compareGen[T](first: Gen[T], second: Gen[T])(len: Int): Unit = {
    val parameters = Parameters.default
    val seed       = Seed.random()

    val generated =
      LazyList
        .continually(first.doApply(parameters, seed).retrieve)
        .zip(LazyList.continually(second.doApply(parameters, seed).retrieve))
        .take(len)

    generated.foreach { case (a, b) => a ==> b }
  }

  def compareGenNot[T](first: Gen[T], second: Gen[T])(len: Int): Unit = {
    val parameters = Parameters.default
    val seed       = Seed.random()

    val generated =
      LazyList
        .continually(first.doApply(parameters, seed).retrieve)
        .zip(LazyList.continually(second.doApply(parameters, seed).retrieve))
        .take(len)

    generated.foreach { case (a, b) => a =!=> b }
  }
}
