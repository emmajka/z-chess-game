package flow.impl

import flow.MovePieceFlow
import repository.GameRepository
import zio.{Task, ZLayer}

case class MovePieceFlowImpl(gameServi: GameRepository) extends MovePieceFlow {
  override def run: Task[Unit] = ???
}

object MovePieceFlowImpl {
  lazy val live = ZLayer.derive[MovePieceFlowImpl]
}
