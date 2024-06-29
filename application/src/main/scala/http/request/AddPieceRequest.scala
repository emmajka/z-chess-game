package http.request

import model.{PieceCoordinates, PieceType}
import sttp.tapir.Schema

case class AddPieceRequest(pieceType: PieceType, targetCoordinates: PieceCoordinates)

object AddPieceRequest {
  import http.serde.PieceCoordinates.given
  import http.serde.PieceType.given
  import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

  given JsonDecoder[AddPieceRequest] = DeriveJsonDecoder.gen
  given JsonEncoder[AddPieceRequest] = DeriveJsonEncoder.gen
  given Schema[AddPieceRequest] = Schema.derived
}
