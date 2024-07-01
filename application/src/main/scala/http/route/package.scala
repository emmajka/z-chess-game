package http

import exception.GameException
import http.response.ErrorResponse
import zio.*

import scala.language.implicitConversions

package object route {
  implicit def zServerCatchAll[R,A](zServerLogic: ZIO[R, Throwable, A]): ZIO[R, ErrorResponse, A] =
    zServerLogic.catchAll( {
      case exc: GameException => {
        ZIO.logWarningCause(s"Game exception! $exc", Cause.fail(exc)) *>
        ZIO.fail(ErrorResponse(message = s"Failure! ${exc.msg}"))
      }
      case exc: Throwable => {
        ZIO.logErrorCause(s"Uncaught exception! $exc", Cause.fail(exc)) *>
        ZIO.fail(ErrorResponse(message = s"Uncaught exception! ${exc.getMessage}"))
      }
    })
}
