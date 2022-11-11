package com.github.plokhotnyuk.jsoniter_scala.benchmark

class BigDecimalWritingSpec extends BenchmarkSpecBase {
  def benchmark: BigDecimalWriting = new BigDecimalWriting {
    setup()
  }

  "BigDecimalWriting" should {
    "write properly" in {
      val b = benchmark
      toString(b.borer()) shouldBe b.jsonString
      toString(b.circe()) shouldBe b.jsonString
      toString(b.circeJsoniter()) shouldBe b.jsonString
      toString(b.jsoniterScala()) shouldBe b.jsonString
      toString(b.preallocatedBuf, 64, b.jsoniterScalaPrealloc()) shouldBe b.jsonString
      toString(b.smithy4sJson()) shouldBe b.jsonString
    }
  }
}