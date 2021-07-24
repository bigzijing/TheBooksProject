lazy val projectName = "TheBooksProject"
lazy val orgName = "dev.zij"
val scala213 = "2.13.5"

name in ThisBuild := projectName
organization in ThisBuild := orgName
organizationName in ThisBuild := orgName
scalaVersion in ThisBuild := scala213

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .settings(
    name := projectName,
    publish / skip := true,
    crossScalaVersions := Nil
  )
  .aggregate(app, engine)

lazy val app = (project in file("app"))

lazy val engine = (project in file("engine"))

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= Dependencies.reactiveMongoPlay

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
