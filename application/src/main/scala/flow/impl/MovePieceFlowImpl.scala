package flow.impl

import flow.MovePieceFlow
import zio.{Task, ZLayer}

case class MovePieceFlowImpl() extends MovePieceFlow {
  override def run: Task[Unit] = ???
}

object MovePieceFlowImpl {
  lazy val live = ZLayer.derive[MovePieceFlowImpl]
}
