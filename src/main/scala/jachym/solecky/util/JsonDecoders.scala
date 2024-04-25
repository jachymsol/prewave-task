package jachym.solecky.util

import jachym.solecky.model.{Alert, AlertContent, QueryTerm}
import io.circe._
import io.circe.Decoder.decodeBoolean

object JsonDecoders {
  // Need to define custom decoder because "type" is a keyword in Scala
  implicit val alertContentDecoder: Decoder[AlertContent] = (c: HCursor) => for {
    text <- c.downField("text").as[String]
    kind <- c.downField("type").as[String]
    language <- c.downField("language").as[String]
  } yield {
    AlertContent(text, kind, language)
  }

  implicit val alertDecoder: Decoder[Alert] = Decoder.forProduct4("id", "contents", "date", "inputType")(Alert.apply)

  implicit val alertListDecoder: Decoder[List[Alert]] = Decoder.decodeList[Alert]

  implicit val queryTermDecoder: Decoder[QueryTerm] = Decoder.forProduct4("id", "text", "language", "keepOrder")(QueryTerm.apply)

  implicit val queryTermListDecoder: Decoder[List[QueryTerm]] = Decoder.decodeList[QueryTerm]

}
