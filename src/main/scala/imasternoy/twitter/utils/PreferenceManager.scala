package imasternoy.twitter.utils

import org.slf4j.LoggerFactory
import java.util.Properties
import scala.io.Source
import java.io.PrintStream

class PreferenceManager {}
object PreferenceManager {
  val logger = LoggerFactory.getLogger(classOf[PreferenceManager])

  val EXIT_KEY = "q"
  lazy val CONSUMER_KEY = properties.getProperty("app.consumerKey")
  lazy val CONSUMER_SECRET = properties.getProperty("app.consumerSecret")
  lazy val ACCESS_TOKEN = properties.getProperty("app.accessToken")
  lazy val ACCESS_TOKEN_SECRET = properties.getProperty("app.accessTokenSecret")

  lazy val CASSANDRA_HOST = properties.getProperty("cassandra.host")
  lazy val CASSANDRA_KEYSPACE = properties.getProperty("cassandra.keyspace")
  lazy val TABLE_RAW_TWEET_DATA = properties.getProperty("cassandra.table.rawTweetData")
  lazy val TABLE_PROCESSED_TWEET_DATA = properties.getProperty("cassandra.table.processedTweetData")
  lazy val TABLE_DICTIONARY = properties.getProperty("cassandra.table.dictionary")

  private val PROPERTIES = "conf.properties"

  var properties: Properties = null;

  def init: Unit = {
    properties = new Properties()
    try {
      properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES))
      logger.info("Properties loaded:\n");
      properties.list(new PrintStream(System.out));
    } catch {
      case t: Throwable =>
        //        logger.error("Failed to load properties", t)
        throw new RuntimeException("Failed to load properties", t)
    }

  }
}