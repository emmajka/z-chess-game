package flow.impl

import flow.MovePieceFlow
import zio.Task

case class MovePieceFlowImpl() extends MovePieceFlow {
  override def run: Task[Unit] = ???
}
