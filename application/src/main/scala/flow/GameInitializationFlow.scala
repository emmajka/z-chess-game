package flow

import zio.Task

trait GameInitializationFlow {
  def run(): Task[Unit]
}
