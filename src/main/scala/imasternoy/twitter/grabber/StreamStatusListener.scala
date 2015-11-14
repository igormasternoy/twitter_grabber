package imasternoy.twitter.grabber

import twitter4j.StatusListener

class StreamStatusListener extends StatusListener {

  override def onStatus(status: twitter4j.Status): Unit = {

    val user = status.getUser()
    val userName = status.getUser().getScreenName()
    val profileLocation = user.getLocation
    val tweetId = status.getId
    val content = status.getText
    
    
    println(content + "\n");

  }

  override def onDeletionNotice(status: twitter4j.StatusDeletionNotice): Unit = ???
  override def onScrubGeo(latitude: Long, longtitude: Long): Unit = ???
  override def onStallWarning(warning: twitter4j.StallWarning): Unit = ???
  override def onTrackLimitationNotice(status: Int): Unit = ???
  override def onException(e: Exception): Unit = ???
}