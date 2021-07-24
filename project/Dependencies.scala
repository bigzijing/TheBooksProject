import sbt._

object Dependencies {
  /**
   * MongoDB
   */
  val reactiveMongoV = "1.0.5"

  val reactiveMongo = Seq(
    "org.reactivemongo" %% "reactivemongo" % "1.0.5"
  )

  val reactiveMongoPlay = Seq(
    "org.reactivemongo" %% "play2-reactivemongo" % s"${reactiveMongoV}-play28",
    "org.reactivemongo" %% "reactivemongo-play-json-compat" % s"${reactiveMongoV}-play28"
  ) ++ reactiveMongo

  /**
   * Zio stack
   */
  val zioV = "1.0.9"

  val zioMagicV = "0.3.5"

  val zio = Seq(
    "dev.zio"              %% "zio"         % zioV,
    "dev.zio"              %% "zio-streams" % zioV,
    "io.github.kitlangton" %% "zio-magic"   % zioMagicV
  )

  /**
   * Shapeless library
   */

  val shapeless = Seq(
    "com.chuusai" %% "shapeless" % "2.3.7"
  )

  val magnolia = Seq(
    "com.propensive" %% "magnolia" % "0.17.0"
  )

  /**
   * Refined types
   */
  val refined = Seq(
    "eu.timepit" %% "refined" % "0.9.27"
  )

  /**
   * Config stuff
   */
  val typesafeConfigV = "1.4.1"
  val typesafeConfig  = Seq("com.typesafe" % "config" % typesafeConfigV)

  val pureConfigV = "0.16.0"
  val pureConfig  = Seq("com.github.pureconfig" %% "pureconfig" % pureConfigV) ++ typesafeConfig ++ shapeless

  val zioConfigV = "1.0.6"

  val zioConfig = Seq(
    "dev.zio" %% "zio-config"          % zioConfigV,
    "dev.zio" %% "zio-config-magnolia" % zioConfigV,
    "dev.zio" %% "zio-config-typesafe" % zioConfigV,
    "dev.zio" %% "zio-config-refined"  % zioConfigV
  ) ++ typesafeConfig ++ refined ++ zio ++ magnolia

  val engineDeps = zio ++ reactiveMongoPlay ++ zioConfig ++ pureConfig
}