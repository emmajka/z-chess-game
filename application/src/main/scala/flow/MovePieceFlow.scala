package flow

import zio.Task

trait MovePieceFlow {
  def run: Task[Unit]
}
