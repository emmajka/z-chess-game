package flow

import model.{Position, PieceType}
import zio.Task

trait MovePieceFlow {
  def run(gameId: String, pieceId: Int, targetPosition: Position): Task[Unit]
}
