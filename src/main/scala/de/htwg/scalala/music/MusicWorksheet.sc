package de.htwg.scalala.music

import de.htwg.scalala.actors._
import scala.language.postfixOps
import akka.actor._

object MusicWorksheet {

val voice1 = Tune(e2, e2,|, f2, e2,|, g, g,f, e,|, f2, e2)
                                                  //> voice1  : de.htwg.scalala.music.Tune = (e½, e½, |, f½, e½, |, g, g, f, e
                                                  //| , |, f½, e½)
val voice2 = Tune(c, g, c, g, |, a,f, c, g, |, e, e, d, c, |, d, b, c2)
                                                  //> voice2  : de.htwg.scalala.music.Tune = (c, g, c, g, |, a, f, c, g, |, e, e, 
                                                  //| d, c, |, d, b, c½)-
Piano.play(voice1)
Cello.play(voice2)

}