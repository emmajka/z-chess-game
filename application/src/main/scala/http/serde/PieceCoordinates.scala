package http.serde

import model.PieceCoordinates as PieceCoordinatesModel
import sttp.tapir.*
import zio.json.*

object PieceCoordinates {
  given JsonDecoder[PieceCoordinatesModel] = DeriveJsonDecoder.gen
  given JsonEncoder[PieceCoordinatesModel] = DeriveJsonEncoder.gen
  given Schema[PieceCoordinatesModel] = Schema.derived
}
