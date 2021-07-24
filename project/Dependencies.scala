import sbt._

object Dependencies {
  /**
   * MongoDB
   */
  val reactiveMongoV = "1.0.5"

  val reactiveMongo = Seq(
    "org.reactivemongo" %% "reactivemongo" % reactiveMongoV
  )

  val reactiveMongoPlay = Seq(
    "org.reactivemongo" %% "play2-reactivemongo" % s"${reactiveMongoV}-play28",
    "org.reactivemongo" %% "reactivemongo-play-json-compat" % s"${reactiveMongoV}-play28"
  ) ++ reactiveMongo
}