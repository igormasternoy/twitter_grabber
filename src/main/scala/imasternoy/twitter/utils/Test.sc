package imasternoy.twitter.utils

object Test {

  val tweet = "RT @AN_ArtBer: :) ... , ! Бабушка-террористка с Западной Украины рассказала, за что надо разорвать Порошенко"
                                                  //> tweet  : String = RT @AN_ArtBer: :) ... , ! Ð‘Ð°Ð±ÑƒÑˆÐºÐ°-Ñ‚ÐµÑ€Ñ€Ð¾Ñ€Ð¸Ñ�Ñ
                                                  //| ‚ÐºÐ° Ñ� Ð—Ð°Ð¿Ð°Ð´Ð½Ð¾Ð¹ Ð£ÐºÑ€Ð°Ð¸Ð½Ñ‹ Ñ€Ð°Ñ�Ñ�ÐºÐ°Ð·Ð°Ð»Ð°, Ð·Ð° Ñ‡Ñ‚Ð¾ Ð
                                                  //| ½Ð°Ð´Ð¾ Ñ€Ð°Ð·Ð¾Ñ€Ð²Ð°Ñ‚ÑŒ ÐŸÐ¾Ñ€Ð¾ÑˆÐµÐ½ÐºÐ¾
  val regex = """^(RT|@)""".r                     //> regex  : scala.util.matching.Regex = ^(RT|@)
  tweet.split(" ").filterNot {
    _ match {
      case regex => false
      case _     => true
    }
  }                                               //> res0: Array[String] = Array(RT, @AN_ArtBer:, :), ..., ,, !, Ð‘Ð°Ð±ÑƒÑˆÐºÐ°-Ñ
                                                  //| ‚ÐµÑ€Ñ€Ð¾Ñ€Ð¸Ñ�Ñ‚ÐºÐ°, Ñ�, Ð—Ð°Ð¿Ð°Ð´Ð½Ð¾Ð¹, Ð£ÐºÑ€Ð°Ð¸Ð½Ñ‹, Ñ€Ð°Ñ�Ñ�ÐºÐ°Ð·Ð
                                                  //| °Ð»Ð°,, Ð·Ð°, Ñ‡Ñ‚Ð¾, Ð½Ð°Ð´Ð¾, Ñ€Ð°Ð·Ð¾Ñ€Ð²Ð°Ñ‚ÑŒ, ÐŸÐ¾Ñ€Ð¾ÑˆÐµÐ½ÐºÐ¾)
  val s = ":):)How are you today?...:( ";         //> s  : String = ":):)How are you today?...:( "
  val punctuation = "(:\\)|:\\(|[^\\p{Punct}]+|\\s+)".r
                                                  //> punctuation  : scala.util.matching.Regex = (:\)|:\(|[^\p{Punct}]+|\s+)
  //s.replaceAll("""[\p{Punct}&&[^.]]""", "")
tweet.replaceAll("""(?![a-zA-Zа-яА-Я ]|=\)|=\]|:P).(?<!=\)|=\]|:P)""", "")
                                                  //> res1: String = RT ANArtBer     Ð‘Ð°Ð±ÑƒÑˆÐºÐ°Ñ‚ÐµÑ€Ñ€Ð¾Ñ€Ð¸Ñ�Ñ‚ÐºÐ° Ñ� Ð—Ð°Ð
                                                  //| ¿Ð°Ð´Ð½Ð¾Ð¹ Ð£ÐºÑ€Ð°Ð¸Ð½Ñ‹ Ñ€Ð°Ñ�Ñ�ÐºÐ°Ð·Ð°Ð»Ð° Ð·Ð° Ñ‡Ñ‚Ð¾ Ð½Ð°Ð´Ð¾ Ñ€Ð°Ð·Ð
                                                  //| ¾Ñ€Ð²Ð°Ñ‚ÑŒ ÐŸÐ¾Ñ€Ð¾ÑˆÐµÐ½ÐºÐ¾
}