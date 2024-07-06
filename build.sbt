ThisBuild / version := "0.0.1"

ThisBuild / scalaVersion := "3.3.3"

lazy val application = (project in file("application"))
  .dependsOn(domain % "compile->compile;test->test", infrastructure % "compile->compile;test->test")
  .settings(
    name := "application",
    libraryDependencies ++= Dependencies.application
  )
lazy val infrastructure = (project in file("infrastructure"))
  .dependsOn(domain % "compile->compile;test->test")
  .settings(
    name := "infrastructure",
    libraryDependencies ++= Dependencies.infrastructure
  )
lazy val domain = (project in file("domain"))
  .settings(
    name := "domain",
    libraryDependencies ++= Dependencies.domain
  )

lazy val root = (project in file("."))
  .aggregate(application, infrastructure, domain)
  .settings(
    name                := "recruitment-task-beone",
    run                 := (application / Compile / run).evaluated,
    Global / cancelable := false
  )
