package imasternoy.twitter.grabber

import twitter4j.conf.ConfigurationBuilder
import twitter4j.TwitterFactory
import twitter4j.Twitter
import twitter4j.TwitterStreamFactory
import twitter4j.TwitterStream
import twitter4j.FilterQuery
import java.io.BufferedReader
import java.io.InputStreamReader
import imasternoy.twitter.utils.PreferenceManager._
import imasternoy.twitter.utils.PreferenceManager
import java.util.Arrays
import java.util.Collections
import scala.collection.JavaConversions
import java.util.ArrayList

/**
 * @author ${user.name}
 */
object App {

  var twitterStream: TwitterStream = null;

  def main(args: Array[String]) {
    println("Starting application...\n")
    println("To exit jsut press 'q' key\n")
    thread.start()

    PreferenceManager.init

    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(PreferenceManager.CONSUMER_KEY)
      .setOAuthConsumerSecret(PreferenceManager.CONSUMER_SECRET)
      .setOAuthAccessToken(PreferenceManager.ACCESS_TOKEN)
      .setOAuthAccessTokenSecret(PreferenceManager.ACCESS_TOKEN)

    //    twitterStream = new TwitterStreamFactory(cb.build()).getInstance

    val fq = new FilterQuery()

    fq.track(args: _*)
    println("Filtered words are: " + args.foreach { println(_) })
  }

  val thread = new Thread {
    override def run {
      val in = new BufferedReader(new InputStreamReader(System.in));
      var line = "";

      while (line.equalsIgnoreCase("q") == false) {
        line = in.readLine()
        CassandraConnector.shutdown
        twitterStream.shutdown()
        Thread.sleep(1000);
        System.exit(0);
      }
      in.close();
    }
  }

}
