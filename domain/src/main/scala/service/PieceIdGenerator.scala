package service

import model.ChessGamePiecesDetails

trait PieceIdGenerator {
  def generate(pieces: Seq[ChessGamePiecesDetails]): Int
}
