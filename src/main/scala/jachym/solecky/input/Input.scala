package jachym.solecky.input

import jachym.solecky.model.{Alert, QueryTerm}
import jachym.solecky.util.JsonDecoders._
import io.circe.Decoder
import io.circe.parser.parse
import sttp.client3._

object Input {
  // The token is stored in an environment variable for security
  private val token: String = sys.env.getOrElse("PREWAVE_TOKEN", throw new Exception("PREWAVE_TOKEN environment variable not set"))

  private val backend: SttpBackend[Identity, Any] = HttpClientSyncBackend()
  private def getUrl(content: String) = uri"https://services.prewave.ai/adminInterface/api/$content?key=$token"

  def getQueryTerms: List[QueryTerm] = {
    val response = basicRequest.get(getUrl("testQueryTerm")).send(backend)
    response.body.fold(error => throw new Exception(error), parseResponse[QueryTerm])
  }

  def getAlerts: List[Alert] = {
    val response = basicRequest.get(getUrl("testAlerts")).send(backend)
    println(response.body)
    response.body.fold(error => throw new Exception(error), parseResponse[Alert])
  }

  private def parseResponse[T](response: String)(implicit decoder: Decoder[List[T]]): List[T] = {
    val responseJson = parse(response)
      .fold(throw _, identity)

    decoder.decodeJson(responseJson)
      .fold(throw _, identity)
  }

}