package flow

import model.{ChessPieceType, PieceCoordinates}
import zio.Task

trait DeleteChessPieceFlow {
  def run(gameId: String, pieceId: Int): Task[Unit]
}
