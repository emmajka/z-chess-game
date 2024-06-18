package http
import sttp.tapir.ztapir.ZServerEndpoint

sealed trait HttpRoute {
  def routes: Seq[ZServerEndpoint[Any, Any]]
}

trait HealthRoute extends HttpRoute
