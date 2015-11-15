package imasternoy.twitter.grabber

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session
import imasternoy.twitter.utils.PreferenceManager._
object CassandraConnector {

  var cluster: Cluster = null;
  var session: Session = null;

  def connect: Unit = {
    cluster = Cluster.builder().addContactPoint(CASSANDRA_HOST).build();
    session = cluster.connect();
    
    session.execute("CREATE KEYSPACE IF NOT EXISTS "+ CASSANDRA_KEYSPACE +" WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};");
    
    session.execute("CREATE TABLE IF NOT EXISTS "+CASSANDRA_KEYSPACE+"."+TABLE_RAW_TWEET_DATA+" (id bigint, user text, msg text, PRIMARY KEY (id));");
    session.execute("CREATE TABLE IF NOT EXISTS "+CASSANDRA_KEYSPACE+"."+TABLE_PROCESSED_TWEET_DATA+" (id bigint, user text, csvWords text, PRIMARY KEY (id));");
    session.execute("CREATE TABLE IF NOT EXISTS "+CASSANDRA_KEYSPACE+"."+TABLE_DICTIONARY+" ( word text, PRIMARY KEY (word));");
    
  }
  
  def shutdown: Unit = {
    session.close()
    cluster.close()
  }
    
  
}