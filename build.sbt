name := """TheBookProject"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

lazy val backend = (project in file("backend"))
  .settings(
    name := "backend",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0-RC1",
      "dev.zio" %% "zio-test" % "2.0.0-RC1" % Test,
      "org.reactivemongo" %% "reactivemongo" % "1.0.3",
      "de.flapdoodle.embed" % "de.flapdoodle.embed.mongo" % "3.0.0" % Test,
      "com.github.pureconfig" %% "pureconfig" % "0.17.1",
      "com.github.tototoshi" %% "scala-csv" % "1.3.10"
    ),
    libraryDependencies ++= calibanZioDeps,
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

scalaVersion := "2.13.8"

Compile / herokuAppName := "the-books-project"
Universal / javaOptions ++= Seq(
  "-Dpidfile.path=/dev/null"
)

// Caliban Zio Dependencies
val calibanZioDeps = Seq("com.github.ghostdogpr" %% "caliban" % "1.3.3", "com.github.ghostdogpr" %% "caliban-zio-http"   % "1.3.3")

libraryDependencies ++= calibanZioDeps
libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
