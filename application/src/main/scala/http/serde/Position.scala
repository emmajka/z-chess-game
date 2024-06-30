package http.serde

import model.Position as PieceCoordinatesModel
import sttp.tapir.*
import zio.json.*

object Position {
  given JsonDecoder[PieceCoordinatesModel] = DeriveJsonDecoder.gen
  given JsonEncoder[PieceCoordinatesModel] = DeriveJsonEncoder.gen
  given Schema[PieceCoordinatesModel] = Schema.derived
}
