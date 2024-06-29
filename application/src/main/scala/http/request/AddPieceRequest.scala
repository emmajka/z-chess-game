package http.request

import model.{PieceCoordinates, PieceType}
import sttp.tapir.Schema

case class AddPieceRequest(gameId: String, pieceType: PieceType, targetCoordinates: PieceCoordinates)

object AddPieceRequest {
  import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}
  import http.serde.PieceType.given 
  import http.serde.PieceCoordinates.given 
  
  given JsonDecoder[AddPieceRequest] = DeriveJsonDecoder.gen
  given JsonEncoder[AddPieceRequest] = DeriveJsonEncoder.gen
  given Schema[AddPieceRequest] = Schema.derived
}
