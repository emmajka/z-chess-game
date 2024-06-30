package flow

import model.{PieceType, Position}
import zio.Task

trait DeletePieceFlow {
  def run(gameId: String, pieceId: Int): Task[Unit]
}
