package service.impl

import service.IdGenerator
import zio.ZLayer

case class GameIdGeneratorImpl() extends IdGenerator {
  override def generate: String = System.currentTimeMillis().toString
}

object GameIdGeneratorImpl {
  lazy val live = ZLayer.derive[GameIdGeneratorImpl]
}
