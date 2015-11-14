package imasternoy.twitter.grabber

object Domain {
  case class RawTweet(id: Long, user: String, msg: String) extends Serializable
  case class Tweet(id: Long, user: String, csvWords: String) extends Serializable
  case class Word(word: String) extends Serializable
}

