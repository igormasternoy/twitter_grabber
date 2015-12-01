package imasternoy.twitter.processor

import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.StringCleaning

class WordsPreProcessor extends TokenPreProcess {

  val UKLangFilter = "[^а-яА-ЯєЄіІїЇ`]"

  override def preProcess(token: String): String = {
    StringCleaning.stripPunct(token.replaceAll(UKLangFilter, ""))
  }

}