package imasternoy.twitter.grabber

import twitter4j.conf.ConfigurationBuilder
import twitter4j.TwitterFactory
import twitter4j.Twitter
import twitter4j.TwitterStreamFactory
import twitter4j.TwitterStream
import twitter4j.FilterQuery

/**
 * @author ${user.name}
 */
object App {

  val consumerKey = "B1TDpGvlzjjULa0r1AKdLF6k8"
  val consumerSecret = "ZIrU9vxKB0TIUric0Ws8bcrPO3H64ZvTbfykaaMeHfmF2F4aPy"
  val accessToken = "2219287969-nxMqxXELAByfD5OlMpgfQpRCRo5XaMpon7SVc3t"
  val accessTokenSecret = "qzOwEzcZjs0y0oK94oT12PyyK6r8BGUOZvaXHbrUGMKqK"

  val FILTER_WORDS = { "ukraine" }

  def main(args: Array[String]) {
    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)

    val twitterStream: TwitterStream = new TwitterStreamFactory(cb.build()).getInstance

    val fq = new FilterQuery()
    fq.track(FILTER_WORDS)

    twitterStream.addListener(new StreamStatusListener);
    twitterStream.filter(fq)

  }

}
