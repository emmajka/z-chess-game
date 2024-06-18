package http.route.impl

import http.HealthRoute
import http.model.ErrorResponse
import http.route.impl.HealthRouteImpl.healthEndpoint
import sttp.tapir.ztapir.*
import sttp.tapir.{Endpoint, EndpointOutput, PublicEndpoint, endpoint}
import zio.*

case class HealthRouteImpl() extends HealthRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    healthEndpoint.zServerLogic(_ => ZIO.logInfo("I'm healthy!") *> ZIO.unit)
  )
}

object HealthRouteImpl {
  val healthEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.get
    .in("health")
    .errorOut(ErrorResponse.errorBody)

  lazy val layer = ZLayer.derive[HealthRouteImpl]
}
