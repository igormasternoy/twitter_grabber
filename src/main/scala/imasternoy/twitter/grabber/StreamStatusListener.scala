package imasternoy.twitter.grabber

import twitter4j.StatusListener
import imasternoy.twitter.grabber.Domain.RawTweet
import org.slf4j.LoggerFactory
import imasternoy.twitter.processor.TwitterMessageProcessor 
import scala.concurrent.ExecutionContext

class StreamStatusListener extends StatusListener {
  val logger = LoggerFactory.getLogger(classOf[StreamStatusListener])

  //  val tweetRepo: TweetsRepo = new TweetsRepo;

  val wordProcessor = new TwitterMessageProcessor

  override def onStatus(status: twitter4j.Status): Unit = {

    val user = status.getUser()
    val userName = status.getUser().getScreenName()
    val profileLocation = user.getLocation
    val tweetId = status.getId
    val content = status.getText
    import ExecutionContext.Implicits.global
//    logger.info("Processed tweet {}", content);
    //    tweetRepo.saveRawTweet(RawTweet(status.getId, status.getUser.getScreenName, status.getText))
    wordProcessor.processMessage(content).map(content => logger.info("Processed tweet {}", content))

  }

  override def onDeletionNotice(status: twitter4j.StatusDeletionNotice): Unit = logger.warn("Status deletion notice statId {}, userId {}", status.getStatusId, status.getUserId)

  override def onScrubGeo(latitude: Long, longtitude: Long): Unit = logger.info("Scrub GEO x: {}, y: {}", latitude, longtitude)

  override def onStallWarning(warning: twitter4j.StallWarning): Unit = logger.warn("Twtitter Stall Warning {}", warning.getMessage)

  override def onTrackLimitationNotice(status: Int): Unit = logger.error("Twtitter limitation Notice {}", status)

  override def onException(e: Exception): Unit = logger.error("Twtitter listener error: ", e)
}