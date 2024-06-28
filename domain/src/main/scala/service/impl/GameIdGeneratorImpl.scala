package service.impl

import service.GameIdGenerator
import zio.ZLayer

case class GameIdGeneratorImpl() extends GameIdGenerator {
  override def generate: String = System.currentTimeMillis().toString
}

object GameIdGeneratorImpl {
  lazy val live = ZLayer.derive[GameIdGeneratorImpl]
}
