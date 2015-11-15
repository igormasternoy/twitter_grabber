package imasternoy.twitter.grabber

import imasternoy.twitter.grabber.Domain._
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.ResultSet
import com.datastax.driver.core.Row

class TweetsRepo {

  CassandraConnector.connect

  val INSERT_RAW_TWEET = CassandraConnector.session.prepare("INSERT INTO " + CassandraConnector.CASSANDRA_KEYSPACE + "."
    + CassandraConnector.RAW_TWEET_DATA + " (id,user,msg)"
    + " VALUES (?,?,?);")

  val INSERT_TWEET = CassandraConnector.session.prepare("INSERT INTO " + CassandraConnector.CASSANDRA_KEYSPACE + "."
    + CassandraConnector.PROCESSED_TWEET_DATA + " (id,user,csvWords)"
    + " VALUES (?,?,?);")

  val INSERT_WORD = CassandraConnector.session.prepare("INSERT INTO " + CassandraConnector.CASSANDRA_KEYSPACE + "."
    + CassandraConnector.DICTIONARY + " (word)"
    + " VALUES (?);")

  val GET_RAW_TWEETS = "SELECT * FROM " + CassandraConnector.CASSANDRA_KEYSPACE + "." + CassandraConnector.RAW_TWEET_DATA;

  val GET_PROCESSET_TWEETS = "SELECT * FROM " + CassandraConnector.CASSANDRA_KEYSPACE + "." + CassandraConnector.PROCESSED_TWEET_DATA;

  val GET_WORDS = "SELECT * FROM " + CassandraConnector.CASSANDRA_KEYSPACE + "." + CassandraConnector.DICTIONARY;

  val GET_RAW_TWEET_BY_ID = CassandraConnector.session.prepare("SELECT * FROM " + CassandraConnector.CASSANDRA_KEYSPACE + "." + CassandraConnector.RAW_TWEET_DATA +
    " WHERE id = ?;");

  val GET_PROCESSED_TWEET_BY_ID = CassandraConnector.session.prepare("SELECT * FROM " + CassandraConnector.CASSANDRA_KEYSPACE + "." + CassandraConnector.PROCESSED_TWEET_DATA +
    " WHERE id = ?;");

  def saveRawTweet(tweet: RawTweet): Unit = {
    val bounded = INSERT_RAW_TWEET.bind(new java.lang.Long(tweet.id), tweet.user, tweet.msg);
    CassandraConnector.session.execute(bounded);
  }

  def saveProcessedTweet(tweet: Tweet): Unit = {
    val bounded = INSERT_TWEET.bind(new java.lang.Long(tweet.id), tweet.user, tweet.csvWords);
    CassandraConnector.session.execute(bounded);
  }

  def saveWord(word: Word) = {
    val bounded = INSERT_WORD.bind(word.word);
    CassandraConnector.session.execute(bounded);
  }

  def getRawTweets: java.util.ArrayList[RawTweet] = {
    val set: ResultSet = CassandraConnector.session.execute(GET_RAW_TWEETS);
    //TODO: Maybe use some reactive stuff here?
    //TODO: UGLY NON SCALA CODE
    val iterator = set.all().iterator()

    var result = new java.util.ArrayList[RawTweet]();

    do {
      val row = iterator.next()
      result.add(new RawTweet(row.getLong(1),
        row.getString(2),
        row.getString(3)));

    } while (iterator.hasNext())
    result
  }

  def getProcessedTweet: Unit = ???

  def getRawTweetById(id: Long) = ???

  def getTweetById(id: Long) = ???

  def getWords: Stream[Word] = ???

}