package imasternoy.twitter.processor

import scala.io.Source
import scala.collection.mutable.HashSet
import org.slf4j.LoggerFactory
import java.util.StringTokenizer
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import imasternoy.twitter.utils.FileUtils._;

class TwitterMessageProcessor {
  val logger = LoggerFactory.getLogger(classOf[TwitterMessageProcessor])

  import ExecutionContext.Implicits.global

  def processMessage(message: String): Future[Array[String]] = Future {
    val processed = new ArrayBuffer[String]()
    val regex = """(^(@|RT)|(^(http|https):\/\/))""".r
    val punctuation = "(:\\)|:\\(|[^\\p{Punct}]+|\\s+)".r
    
    message.replaceAll("[^а-яА-ЯєЄіІїЇ` ]", "").split(" ").filter(word => 
      !stopWords.contains(word) &&
        !word.startsWith("@") &&
        !word.equals("RT") &&
        !word.startsWith("http://") &&
        !word.startsWith("https://"))
      .foreach { x =>
        if (!smiles.contains(x)) {
          processed += x
        } else {
//          processed += smiles.get(x)
        }
      }
    processed.toArray
  }
  


}