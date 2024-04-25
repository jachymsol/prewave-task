package jachym.solecky

// Defines the classes of custom objects
package object model {

  case class Alert(id: String, contents: List[AlertContent], date: String, inputType: String)

  case class AlertContent(text: String, kind: String, language: String)

  case class QueryTerm(id: Int, text: String, language: String, keepOrder: Boolean)

  case class MatchedAlert(alert: Alert, queryTerm: QueryTerm)

}
