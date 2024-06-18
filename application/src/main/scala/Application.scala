import http.Http4sServer
import http.route.impl.{HealthRouteImpl, OpenApiRouteImpl}
import http.route.{HealthRoute, HttpRoute}
import zio.{Fiber, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object Application extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {

    (for _ <- ZIO.log("hello world")
         routes <- getAllRoutes
         _ <- ZIO.logInfo("Setting up http server...")
         f1 <- Http4sServer.run(routes = routes, 8081).fork
         _ <- ZIO.logInfo("Http server up & running")
         _ <- Fiber.collectAll(List(f1)).join
    yield ()
      ).provide()
  }

  private def getAllRoutes = {
    for healthRoute <- ZIO.service[HealthRoute]
        appRoutes: Seq[HttpRoute] = Seq(healthRoute)
        openApiRoute = OpenApiRouteImpl(appRoutes)
    yield Seq(healthRoute, openApiRoute)
  }.provide(HealthRouteImpl.layer)
}
