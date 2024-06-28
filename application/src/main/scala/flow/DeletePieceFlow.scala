package flow

import model.{ChessPieceType, PieceCoordinates}
import zio.Task

trait DeletePieceFlow {
  def run(gameId: String, pieceId: Int): Task[Unit]
}
