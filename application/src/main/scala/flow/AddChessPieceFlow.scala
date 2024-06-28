package flow

import model.{ChessPieceType, PieceCoordinates}
import zio.Task

trait AddChessPieceFlow {
  def run(gameId: String, pieceType: ChessPieceType, pieceCoordinates: PieceCoordinates): Task[Unit]
}
