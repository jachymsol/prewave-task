package jachym.solecky

import jachym.solecky.model._

object Search {
  def findMatchedQueryTerms(alert: Alert)(implicit queryTerms: List[QueryTerm]): List[MatchedAlert] = {
    queryTerms
      .filter(queryTerm => alert.contents.exists(isContentAndQueryTermMatching(_, queryTerm)))
      .map(MatchedAlert(alert, _))
  }

  private def isContentAndQueryTermMatching(content: AlertContent, queryTerm: QueryTerm): Boolean = {
    if (queryTerm.language != content.language) {
      return false
    }

    if (queryTerm.keepOrder) {
      // match the entire query term token
      content.text.toLowerCase.contains(queryTerm.text.toLowerCase)
    } else {
      // match each token separately
      queryTerm.text.split(" ").map(_.toLowerCase).forall(content.text.toLowerCase.contains(_))
    }
  }
}
