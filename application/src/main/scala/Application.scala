import cfg.{ConfigProvider, HttpConfig}
import flow.impl.{AddPieceFlowImpl, CreateNewGameFlowImpl, GetGameDetailsFlowImpl}
import http.Http4sServer
import http.handler.impl.GameHandlerImpl
import http.route.impl.{GameRouteImpl, HealthRouteImpl, OpenApiRouteImpl}
import http.route.{GameRoute, HealthRoute, HttpRoute}
import mysql.{MysqlConnection, MysqlCtx}
import repository.impl.GameRepositoryImpl
import service.impl.{GameServiceImpl, GameIdGeneratorImpl, PieceIdCreatorImpl, PieceIdGeneratorImpl}
import validator.impl.GamePieceValidatorImpl
import zio.{Fiber, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object Application extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = ConfigProvider.hocon

  override def run: ZIO[ZIOAppArgs, Any, Any] = {

    for
      _ <- ZIO.log("hello world")
      routes <- getAllRoutes
      httpServerPort <- ZIO.serviceWith[HttpConfig](_.port)
      _ <- ZIO.logInfo(s"Setting up http server on port $httpServerPort...")
      f1 <- Http4sServer.run(routes = routes, httpServerPort).fork
      _ <- ZIO.logInfo("Http server up & running")
      _ <- Fiber.collectAll(List(f1)).join
    yield ()

  }.provide(
    HttpConfig.live,
    HealthRouteImpl.live,
    GameRouteImpl.live,
    GameHandlerImpl.live,
    GameRepositoryImpl.live,
    GameServiceImpl.live,
    CreateNewGameFlowImpl.live,
    GetGameDetailsFlowImpl.live,
    AddPieceFlowImpl.live,
    GameIdGeneratorImpl.live,
    PieceIdGeneratorImpl.live,
    GamePieceValidatorImpl.live,
    PieceIdCreatorImpl.live,
    MysqlConnection.ctx,
    MysqlConnection.ds
  )

  private def getAllRoutes = {
    for
      healthRoute <- ZIO.service[HealthRoute]
      gameRoute <- ZIO.service[GameRoute]
      appRoutes = Seq(healthRoute, gameRoute)
      openApiRoute = OpenApiRouteImpl(appRoutes)
    yield Seq(healthRoute, gameRoute, openApiRoute)
  }
}
