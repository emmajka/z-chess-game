package http.route

import sttp.tapir.ztapir.ZServerEndpoint

sealed trait HttpRoute {
  def routes: Seq[ZServerEndpoint[Any, Any]]
}

trait HealthRoute extends HttpRoute
trait OpenApiRoute extends HttpRoute
trait GameRoute extends HttpRoute