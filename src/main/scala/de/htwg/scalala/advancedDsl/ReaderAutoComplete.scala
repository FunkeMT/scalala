package de.htwg.scalala.advancedDsl

import scala.collection.mutable.ListBuffer
import scala.language.postfixOps
import scala.util.parsing.combinator.ImplicitConversions
import scala.util.parsing.combinator.syntactical._

class ReaderAutoComplete extends Reader with ImplicitConversions {

  val tried_keywords: ListBuffer[String] = new ListBuffer[String]
  var matched: String = ""

  def matchKeyword(expect: String)(e: Elem): Boolean = {
    println("matching: " + e.chars + " - expect: " + expect)
    if (expect == e.chars) {
      println("---> " + expect + " == " + e)
      matched = expect
      true
    } else if (expect.startsWith(e.chars)) {
      println("---> " + expect + " startsWith " + e)
      //keep only matched words in list tried_keywords
      for (v <- tried_keywords if !v.startsWith(e.chars)) tried_keywords -= v
      tried_keywords += expect
      matched = ""
      false
    } else
      false
  }

  def errReport(e: Elem): String = {
    "Err: " + e + " found."
  }

  override implicit def keyword(chars: String): Parser[String] = {
    //println("keyword: " + chars)
    acceptIf(matchKeyword(chars))(errReport) ^^ (_.chars)
  }

  def autoCompletion[T](p: Parser[T], in: String): List[String] = {
    tried_keywords.clear
    matched = ""
    phrase(p)(new lexical.Scanner(in))
    tried_keywords.toList
  }
}
