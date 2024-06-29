package flow

import zio.Task

trait CreateNewGameFlow {
  def run(): Task[String]
}
