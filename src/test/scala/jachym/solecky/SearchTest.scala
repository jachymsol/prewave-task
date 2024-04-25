package jachym.solecky

import jachym.solecky.model.{Alert, AlertContent, MatchedAlert, QueryTerm}

class SearchTest extends munit.FunSuite {
  val alert: Alert = Alert(
    id = "6gbujhu89786",
    contents = List(AlertContent(
      text = "In Germany, Wolfgang Lemb, ig metall stands\nin solidarity with # StrikeForBlackLives",
      kind = "text",
      language = "de")),
    date = "2020-07-24T06:18:45.777Z",
    inputType = "tweet")

  test("match correctly finds a keepOrder match") {
    val queryTerm = QueryTerm(id = 101, text = "IG Metall", language = "de", keepOrder = true)
    implicit val queryTerms: List[QueryTerm] = List(queryTerm)

    val matchedAlerts: List[MatchedAlert] = Search.findMatchedQueryTerms(alert)
    assertEquals(matchedAlerts, List(MatchedAlert(alert, queryTerm)))
  }

  test("match correctly finds a non-keepOrder match") {
    val queryTerm = QueryTerm(id = 102, text = "Metall Germany", language = "de", keepOrder = false)
    implicit val queryTerms: List[QueryTerm] = List(queryTerm)

    val matchedAlerts: List[MatchedAlert] = Search.findMatchedQueryTerms(alert)
    assertEquals(matchedAlerts, List(MatchedAlert(alert, queryTerm)))
  }

  test("match correctly misses a different-language match") {
    val queryTerm = QueryTerm(id = 103, text = "Metall Germany", language = "en", keepOrder = false)
    implicit val queryTerms: List[QueryTerm] = List(queryTerm)

    val matchedAlerts: List[MatchedAlert] = Search.findMatchedQueryTerms(alert)
    assertEquals(matchedAlerts, List())
  }

  test("match correctly misses a keepOrder match") {
    val queryTerm = QueryTerm(id = 104, text = "Metall Germany", language = "de", keepOrder = true)
    implicit val queryTerms: List[QueryTerm] = List(queryTerm)

    val matchedAlerts: List[MatchedAlert] = Search.findMatchedQueryTerms(alert)
    assertEquals(matchedAlerts, List())
  }

  test("match two query terms") {
    val queryTerm1 = QueryTerm(id = 101, text = "IG Metall", language = "de", keepOrder = true)
    val queryTerm2 = QueryTerm(id = 102, text = "Metall Germany", language = "de", keepOrder = false)
    implicit val queryTerms: List[QueryTerm] = List(queryTerm1, queryTerm2)

    val matchedAlerts: List[MatchedAlert] = Search.findMatchedQueryTerms(alert)
    assertEquals(matchedAlerts, List(MatchedAlert(alert, queryTerm1), MatchedAlert(alert, queryTerm2)))
  }
}
