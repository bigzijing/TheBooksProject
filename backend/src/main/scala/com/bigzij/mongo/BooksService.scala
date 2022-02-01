package com.bigzij.mongo

import com.bigzij.mongo.models.BookDBO
import reactivemongo.api.DB
import reactivemongo.api.bson.collection.BSONCollection
import zio.ZIO

import scala.concurrent.Future

trait BooksService {
  def findByOid(oid: String): BookDBO

  def findByISBN(isbn: String): BookDBO

  def findByTitleAndAuthor(title: Option[String], author: Option[String]): BookDBO

  def findBook(
              _id: Option[String],
              title: Option[String],
              isbn: Option[String],
              author: Option[String],
              publisher: Option[String],
              category: Option[String],
              cover: Option[String],
              pages: Option[Int],
              condition: Option[String],
              rating: Option[Double],
              grRating: Option[Double],
              location: Option[String],
              status: Option[String],
              note: Option[String],
              read: Option[Boolean]
              ): List[BookDBO]

  def insertOne(book: BookDBO): Future[Unit]

  def insertOne2(book: BookDBO): Future[Unit]
}

class BooksServiceImpl(mongoService: MongoModule) extends BooksService with MongoDSL {
  implicit val ec2: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  override protected def onError(error: Throwable): Unit = println(s"Error encountered: ${error.getMessage}")

  val collection: Future[BSONCollection] = mongoService.mongoDB.map(_.collection("books"))

  override def findByOid(oid: String): BookDBO = ???

  override def findByISBN(isbn: String): BookDBO = ???

  override def findByTitleAndAuthor(title: Option[String], author: Option[String]): BookDBO = ???

  override def findBook(_id: Option[String], title: Option[String], isbn: Option[String], author: Option[String], publisher: Option[String], category: Option[String], cover: Option[String], pages: Option[Int], condition: Option[String], rating: Option[Double], grRating: Option[Double], location: Option[String], status: Option[String], note: Option[String], read: Option[Boolean]): List[BookDBO] = ???

  def insertOne(book: BookDBO): Future[Unit] = this.insert(book).map(_ => ())

  def insertOne2(book: BookDBO): Future[Unit] = collection.flatMap( coll => coll.insert.one(book)).map(_ => ())
}
