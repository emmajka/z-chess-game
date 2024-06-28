package flow.impl

import flow.MovePieceFlow
import repository.ChessGameRepository
import zio.{Task, ZLayer}

case class MovePieceFlowImpl(gameServi: ChessGameRepository) extends MovePieceFlow {
  override def run: Task[Unit] = ???
}

object MovePieceFlowImpl {
  lazy val live = ZLayer.derive[MovePieceFlowImpl]
}
