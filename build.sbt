ThisBuild / version := "0.0.1"

ThisBuild / scalaVersion := "3.3.3"

lazy val application = (project in file("application"))
  .dependsOn(infrastructure % "compile->compile;test->test")
  .settings(
    name := "application",
    libraryDependencies ++= Dependencies.application
  )
lazy val infrastructure = (project in file("infrastructure"))
  .settings(
    name := "infrastructure",
    libraryDependencies ++= Dependencies.infrastructure
  )

//lazy val client = (project in file("client"))
//  .settings(
//    name := "client",
//    libraryDependencies ++= Dependencies.client
//  )

lazy val root = (project in file("."))
  .aggregate(application, infrastructure)
  .settings(
    name := "recruitment-task-beone",
    run := (application / Compile / run).evaluated,
    Global / cancelable := false
  )
