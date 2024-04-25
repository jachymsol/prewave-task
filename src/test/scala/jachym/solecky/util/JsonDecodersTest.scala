package jachym.solecky.util

import jachym.solecky.model.{Alert, AlertContent, QueryTerm}
import io.circe.parser.parse

class JsonDecodersTest extends munit.FunSuite {
  test("should correctly decode QueryTerm") {
    val queryTermAsString =
      """{
        |  "id": 101,
        |  "text": "IG Metall",
        |  "language": "de",
        |  "keepOrder": true
        |}""".stripMargin
    val queryTermAsJson = parse(queryTermAsString) match {
      case Right(value) => value
      case Left(error) => throw new Exception(s"Failed to parse response: $error")
    }
    val actualQueryTerm = JsonDecoders.queryTermDecoder.decodeJson(queryTermAsJson)

    val expectedQueryTerm = QueryTerm(id = 101, text = "IG Metall", language = "de", keepOrder = true)
    assertEquals(actualQueryTerm, Right(expectedQueryTerm))
  }

  test("should correctly decode Alert") {
    val alertAsString =
      """{
        |  "id": "6gbujhu89786",
        |  "contents": [
        |    {
        |      "text":"Wolfgang Lemb, ig metall Germany stands\nin solidarity with # StrikeForBlackLives",
        |      "type":"text",
        |      "language":"de"
        |    }
        |  ],
        |  "date": "2020-07-24T06:18:45.777Z",
        |  "inputType": "tweet"
        |}""".stripMargin
    val alertAsJson = parse(alertAsString) match {
      case Right(value) => value
      case Left(error) => throw new Exception(s"Failed to parse response: $error")
    }
    val actualAlert = JsonDecoders.alertDecoder.decodeJson(alertAsJson)

    val expectedAlert = Alert(id = "6gbujhu89786", contents = List(AlertContent(text = "Wolfgang Lemb, ig metall Germany stands\nin solidarity with # StrikeForBlackLives", kind = "text", language = "de")), date = "2020-07-24T06:18:45.777Z", inputType = "tweet")
    assertEquals(actualAlert, Right(expectedAlert))
  }
}
