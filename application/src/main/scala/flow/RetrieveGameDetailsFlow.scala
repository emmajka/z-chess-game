package flow

import zio.Task

trait RetrieveGameDetailsFlow {
  def run(gameId: String): Task[String]
}
