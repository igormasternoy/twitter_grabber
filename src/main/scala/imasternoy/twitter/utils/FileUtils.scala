package imasternoy.twitter.utils

import scala.collection.mutable.HashMap
import scala.io.Source
import org.slf4j.LoggerFactory
import java.util.HashSet

class FileUtils {}
object FileUtils {
  val logger = LoggerFactory.getLogger(classOf[FileUtils])

  val stopWords = loadSetFromFile("stop.dictionary");
  val smiles = loadMapFromFile("emoticon.dictionary");

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

  def loadSetFromFile(fileName: String): HashSet[String] = {
    try {
      val set = new HashSet[String]
      val is = ClassLoader.getSystemResourceAsStream(fileName)
      Source.fromInputStream(is).getLines().foreach(set.add(_))
      set
    } catch {
      case t: Throwable =>
        logger.error("Failed to read file {}", t);
        throw new RuntimeException(t);
    }
  }

}