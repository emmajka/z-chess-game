package http.response

import sttp.tapir.json.zio.jsonBody
import sttp.tapir.{EndpointIO, Schema, json}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class ErrorResponse(message: String)

object ErrorResponse {
  val errorBody: EndpointIO.Body[String, ErrorResponse] = jsonBody[ErrorResponse]
  given JsonDecoder[ErrorResponse] = DeriveJsonDecoder.gen
  given JsonEncoder[ErrorResponse] = DeriveJsonEncoder.gen
  given Schema[ErrorResponse] = Schema.derived
}
