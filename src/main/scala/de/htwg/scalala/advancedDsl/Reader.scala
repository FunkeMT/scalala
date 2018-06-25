package de.htwg.scalala.advancedDsl

import scala.language.postfixOps
import scala.util.parsing.combinator.syntactical._

class Reader extends StandardTokenParsers {
  lexical.reserved += ("loop",
    "chord",
    "notes",
    "instrument",
    "musician",
    "with",
    "play",
    "plays",
    "tempo",
    "at")
  lexical.delimiters += (",", "(", ")")

  def song: Parser[Song] = rep(track | musician) ^^ {
    s => new Song(s)
  }

  def track: Parser[Track] = "play" ~ opt(tempo) ~ musicVars ^^ {
    case p ~ None ~ m => new Track(m)
    case p ~ t ~ m => new Track(m, t.get)
  }

  def tempo: Parser[Int] = "with" ~> "tempo" ~> numericLit ^^ {
    n => n.toInt
  }

  def musicVars: Parser[List[MusicVar]] = repsep(musicVar, ",") ^^ {
    m => m
  }

  def musicVar: Parser[MusicVar] = ident ~ opt(playAt) ^^ {
    case i ~ None => new MusicVar(i)
    case i ~ a => new MusicVar(i, a.get)
  }

  def playAt: Parser[Int] = "at" ~> numericLit ^^ {
    n => n.toInt
  }

  def musician: Parser[Musician] = ("musician" ~> ident) ~ ("instrument" ~> instrument) ~ ("plays" ~> (loopElements | noteElements)) ^^ {
    case m ~ i ~ e => new Musician(m, i, e)
  }

  def instrument: Parser[Instrument] = ident ^^ {
    case i => new Instrument(i)
  }

  def noteElements: Parser[NoteElements] = repsep(noteElement, ",") ^^ {
    case e => new NoteElements(e)
  }

  def noteElement: Parser[NoteElement] = chord | note ^^ {
    e => e
  }

  def loopElements: Parser[LoopElement] = ("loop" ~> "(") ~> noteElements <~ ")" ^^ {
    case l => new LoopElement(l)
  }

  def chord: Parser[Chord] = ("chord" ~> "(") ~> repsep(note, ",") <~ ")" ^^ {
    case n => new Chord(n)
  }

  def note: Parser[Note] = ident ^^ {
    case i => new Note(i)
  }

  def parseAll[T](p: Parser[T], in: String): ParseResult[T] = {
    phrase(p)(new lexical.Scanner(in))
  }
}
