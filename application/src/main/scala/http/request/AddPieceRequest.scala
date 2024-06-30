package http.request

import model.{Position, PieceType}
import sttp.tapir.Schema

case class AddPieceRequest(pieceType: PieceType, targetCoordinates: Position)

object AddPieceRequest {
  import http.serde.Position.given
  import http.serde.PieceType.given
  import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

  given JsonDecoder[AddPieceRequest] = DeriveJsonDecoder.gen
  given JsonEncoder[AddPieceRequest] = DeriveJsonEncoder.gen
  given Schema[AddPieceRequest] = Schema.derived
}
