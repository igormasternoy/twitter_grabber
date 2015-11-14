package imasternoy.twitter.grabber

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session

object CassandraConnector {

  val CASSANDRA_HOST = "127.0.0.1"
  val CASSANDRA_KEYSPACE = "tweets_ks"
  val RAW_TWEET_DATA = "rawTweetData"
  val PROCESSED_TWEET_DATA = "proccedTweetData"
  val DICTIONARY = "dictionary"

  var cluster: Cluster = null;
  var session: Session = null;

  def connect: Unit = {
    cluster = Cluster.builder().addContactPoint(CASSANDRA_HOST).build();
    session = cluster.connect();
    
    session.execute("CREATE KEYSPACE IF NOT EXISTS "+ CASSANDRA_KEYSPACE +" WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};");
    
    session.execute("CREATE TABLE IF NOT EXISTS "+CASSANDRA_KEYSPACE+"."+RAW_TWEET_DATA+" (id bigint, user text, msg text, PRIMARY KEY (id));");
    session.execute("CREATE TABLE IF NOT EXISTS "+CASSANDRA_KEYSPACE+"."+PROCESSED_TWEET_DATA+" (id bigint, user text, csvWords text, PRIMARY KEY (id));");
    session.execute("CREATE TABLE IF NOT EXISTS "+CASSANDRA_KEYSPACE+"."+DICTIONARY+" ( word text, PRIMARY KEY (word));");
    
  }
    
    
  
}