package com.github.plokhotnyuk.jsoniter_scala.benchmark

import java.nio.charset.StandardCharsets.UTF_8

class NestedStructsReadingSpec extends BenchmarkSpecBase {
  def benchmark: NestedStructsReading = new NestedStructsReading {
    setup()
  }

  "NestedStructsReading" should {
    "read properly" in {
      //FIXME: Borer throws io.bullet.borer.Borer$Error$Overflow: This JSON parser does not support more than 64 Array/Object nesting levels
      //benchmark.borer() shouldBe benchmark.obj
      benchmark.circe() shouldBe benchmark.obj
      benchmark.circeJsoniter() shouldBe benchmark.obj
      benchmark.jacksonScala() shouldBe benchmark.obj
      benchmark.json4sJackson() shouldBe benchmark.obj
      benchmark.json4sNative() shouldBe benchmark.obj
      benchmark.jsoniterScala() shouldBe benchmark.obj
      benchmark.smithy4sJson() shouldBe benchmark.obj
      //FIXME: uPuckle hungs in endless loop
      //benchmark.uPickle() shouldBe benchmark.obj
      benchmark.weePickle() shouldBe benchmark.obj
      benchmark.zioJson() shouldBe benchmark.obj
    }
    "fail on invalid input" in {
      val b = benchmark
      b.jsonBytes = "[]".getBytes(UTF_8)
      intercept[Throwable](b.circe())
      intercept[Throwable](b.circeJsoniter())
      intercept[Throwable](b.jacksonScala())
      b.jsonBytes = "x".getBytes(UTF_8)
      intercept[Throwable](b.json4sJackson())
      intercept[Throwable](b.json4sNative())
      b.jsonBytes = "[]".getBytes(UTF_8)
      intercept[Throwable](b.jsoniterScala())
      intercept[Throwable](b.smithy4sJson())
      //FIXME: uPuckle hungs in endless loop
      //intercept[Throwable](b.uPickle())
      intercept[Throwable](b.weePickle())
      intercept[Throwable](b.zioJson())
    }
  }
}