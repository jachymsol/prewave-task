package jachym.solecky.output

import jachym.solecky.model.MatchedAlert

object Output {
  def outputMatch(matchedAlert: MatchedAlert): Unit = {
    // This method can be used to send the data to Pub/Sub or write it in a file or database
    // For the purposes of this task I will just print the IDs in the console
    println(s"Found match! AlertID: ${matchedAlert.alert.id}, QueryTermID: ${matchedAlert.queryTerm.id}")
  }
}
