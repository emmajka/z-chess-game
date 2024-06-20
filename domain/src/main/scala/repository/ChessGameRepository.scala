package repository

import model.ChessGameDetails
import zio.*

import java.sql.SQLException
trait ChessGameRepository {
  def getChessGameDetails(gameId: String): IO[SQLException, List[ChessGameDetails]]
}
