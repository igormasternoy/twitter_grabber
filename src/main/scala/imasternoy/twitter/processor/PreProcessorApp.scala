package imasternoy.twitter.processor

import org.deeplearning4j.models.embeddings.WeightLookupTable
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.models.word2vec.Word2Vec
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache
import org.deeplearning4j.text.sentenceiterator.{ SentenceIterator, UimaSentenceIterator }
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor
import org.deeplearning4j.text.tokenization.tokenizerfactory.{ DefaultTokenizerFactory, TokenizerFactory }
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.util.{ ArrayList, Collection }
import imasternoy.twitter.utils.FileUtils._;
import org.deeplearning4j.clustering.kmeans.KMeansClustering
object PreProcessorApp {
  lazy val log = LoggerFactory.getLogger(PreProcessorApp.getClass)

    def main(args: Array[String]) {

        val filePath = new ClassPathResource("uk-10000.dictionary").getFile().getAbsolutePath()

        log.info("Load & Vectorize Sentences....")
        // Strip white space before and after for each line
        val iter: SentenceIterator = UimaSentenceIterator.createWithPath(filePath)
        // Split on white spaces in the line to get words
        val t = new DefaultTokenizerFactory()
        t.setTokenPreProcessor(new WordsPreProcessor)

        val cache = new InMemoryLookupCache()
        val table: WeightLookupTable = new InMemoryLookupTable.Builder()
                .vectorLength(100)
                .useAdaGrad(false)
                .cache(cache)
                .lr(0.025f).build()

        log.info("Building model....")
        val vec = new Word2Vec.Builder()
                .epochs(1).saveVocab(true)
                .minWordFrequency(5)
                .iterations(1)
                .learningRate(0.025) // 
                .minLearningRate(1e-2) 
                .layerSize(100).lookupTable(table)
                .stopWords(new ArrayList(stopWords))
                .vocabCache(cache)
//                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t).build()

        log.info("Fitting Word2Vec model....")
        vec.fit()

        log.info("Writing word vectors to text file....")
        // Write word
        WordVectorSerializer.writeWordVectors(vec, "model.txt")
        
      
//        clustering.applyTo()
        

        log.info("Closest Words:")
        val lst: Collection[String] = vec.wordsNearest("шевченко", 10)
        
        System.out.println(lst)
        
    }
}


