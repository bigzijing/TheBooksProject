name := """TheBookProject"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"

/**
 * MongoDB
 */
val reactiveMongoV = "1.0.5"

val reactiveMongo = Seq(
  "org.reactivemongo" %% "reactivemongo" % reactiveMongoV
)

val reactiveMongoPlay = Seq(
  "org.reactivemongo" %% "play2-reactivemongo"            % s"${reactiveMongoV}-play28",
  "org.reactivemongo" %% "reactivemongo-play-json-compat" % s"${reactiveMongoV}-play28"
) ++ reactiveMongo

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= reactiveMongoPlay

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
