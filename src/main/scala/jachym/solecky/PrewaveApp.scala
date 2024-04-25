package jachym.solecky

import jachym.solecky.input.Input
import jachym.solecky.model.QueryTerm
import jachym.solecky.output.Output

object PrewaveApp extends App {
    implicit val queryTerms: List[QueryTerm] = Input.getQueryTerms

    val alerts = Input.getAlerts
    alerts
      .flatMap(Search.findMatchedQueryTerms)
      .foreach(Output.outputMatch)
}