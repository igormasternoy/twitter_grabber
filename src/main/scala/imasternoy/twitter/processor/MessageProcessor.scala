package imasternoy.twitter.processor

import scala.io.Source
import scala.collection.mutable.HashSet
import org.slf4j.LoggerFactory
import java.util.StringTokenizer
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

class MessageProcessor {
  val logger = LoggerFactory.getLogger(classOf[MessageProcessor])

  var stopWords: Set[String] = null;
  var smiles: Map[String, String] = null;

  def init: Unit = {
    stopWords = loadSetFromFile("stop.dictionary")
    smiles = loadMapFromFile("emoticon.dictionary")
  }

  import ExecutionContext.Implicits.global

  def processMessage(message: String): Future[Array[String]] = Future {
    val processed = new ArrayBuffer[String]()
    val regex = """(^(@|RT)|(^(http|https):\/\/))""".r
    val punctuation = "(:\\)|:\\(|[^\\p{Punct}]+|\\s+)".r
    
    
    
    message.replaceAll("[^а-яА-Я ]", "").split(" ").filter(word => 
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
  
  def loadMapFromFile(fileName: String): Map[String, String] = {
    try {
      val map: HashMap[String, String] = new scala.collection.mutable.HashMap();
      val is = ClassLoader.getSystemResourceAsStream(fileName)
      Source.fromInputStream(is).getLines().foreach { line =>
        val entry = line.split(",")
        map.put(entry(0), entry(1))
      }
      map.toMap
    } catch {
      case t: Throwable =>
        logger.error("Failed to read file {}", t);
        throw new RuntimeException(t);
    }
  }

  def loadSetFromFile(fileName: String): Set[String] = {
    try {
      val set: HashSet[String] = new scala.collection.mutable.HashSet();
      val is = ClassLoader.getSystemResourceAsStream(fileName)
      Source.fromInputStream(is).getLines().foreach(set.add(_))
      set.toSet
    } catch {
      case t: Throwable =>
        logger.error("Failed to read file {}", t);
        throw new RuntimeException(t);
    }
  }

}