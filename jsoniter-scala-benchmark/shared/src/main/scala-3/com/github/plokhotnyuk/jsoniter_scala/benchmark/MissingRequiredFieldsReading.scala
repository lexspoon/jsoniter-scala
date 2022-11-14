package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class MissingRequiredFieldsReading extends MissingRequiredFieldsBenchmark {
  @Benchmark
  def borer(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders._
    import io.bullet.borer.Borer.Error
    import io.bullet.borer.Json

    try {
      Json.decode(jsonBytes).to[MissingRequiredFields].value.toString // toString shouldn't be called
    } catch {
      case ex: Error[_] => ex.getMessage
    }
  }

  @Benchmark
  def circe(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceEncodersDecoders._
    import io.circe.jawn._

    decodeByteArray[MissingRequiredFields](jsonBytes).fold(_.getMessage, _.toString) // toString shouldn't be called
  }

  @Benchmark
  def circeJsoniter(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceEncodersDecoders._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[MissingRequiredFields].decodeJson(readFromArray(jsonBytes)).fold(_.getMessage, _.toString) // toString shouldn't be called
  }

  @Benchmark
  def jacksonScala(): String = {
    import com.fasterxml.jackson.databind.exc.MismatchedInputException
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    try {
      jacksonMapper.readValue[MissingRequiredFields](jsonBytes).toString // toString shouldn't be called
    } catch {
      case ex: MismatchedInputException => ex.getMessage
    }
  }

  @Benchmark
  def json4sJackson(): String = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    try {
      mapper.readValue[JValue](jsonBytes, jValueType).extract[MissingRequiredFields].toString// toString shouldn't be called
    } catch {
      case ex: MappingException => ex.getMessage
    }
  }

  @Benchmark
  def json4sNative(): String = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    try {
      parse(new String(jsonBytes, UTF_8)).extract[MissingRequiredFields].toString// toString shouldn't be called
    } catch {
      case ex: MappingException => ex.getMessage
    }
  }

  @Benchmark
  def jsoniterScala(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    try {
      readFromArray[MissingRequiredFields](jsonBytes).toString // toString shouldn't be called
    } catch {
      case ex: JsonReaderException => ex.getMessage
    }
  }

  @Benchmark
  def jsoniterScalaWithoutDump(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    try {
      readFromArray[MissingRequiredFields](jsonBytes, exceptionWithoutDumpConfig).toString // toString shouldn't be called
    } catch {
      case ex: JsonReaderException => ex.getMessage
    }
  }

  @Benchmark
  def jsoniterScalaWithStacktrace(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    try {
      readFromArray[MissingRequiredFields](jsonBytes, exceptionWithStacktraceConfig).toString // toString shouldn't be called
    } catch {
      case ex: JsonReaderException => ex.getMessage
    }
  }

  @Benchmark
  def smithy4sJson(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import smithy4s.http.PayloadError

    try {
      readFromArray[MissingRequiredFields](jsonBytes).toString // toString shouldn't be called
    } catch {
      case ex: PayloadError => ex.getMessage
    }
  }

  @Benchmark
  def uPickle(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._
    import upickle.core.AbortException

    try {
      read[MissingRequiredFields](jsonBytes).toString // toString shouldn't be called
    } catch {
      case ex: AbortException => ex.getMessage
    }
  }

  @Benchmark
  def weePickle(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.WeePickleFromTos._
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala
    import com.rallyhealth.weepickle.v1.core.TransformException

    try {
      FromJson(jsonBytes).transform(ToScala[MissingRequiredFields]).toString // toString shouldn't be called
    } catch {
      case ex: TransformException => ex.getMessage
    }
  }
/* FIXME: zio-json codec doesn't compile
  @Benchmark
  def zioJson(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.ZioJSONEncoderDecoders._
    import zio.json._
    import zio.json.JsonDecoder._
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[MissingRequiredFields].fold(identity, _.toString) // toString shouldn't be called
  }
*/
}