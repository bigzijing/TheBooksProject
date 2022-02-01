package com.bigzij.mongo

import com.bigzij.mongo.models.BookDBO
import reactivemongo.api.DB
import reactivemongo.api.bson.collection.BSONCollection

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
}

class BooksServiceImpl(mongoService: MongoModule) extends BooksService {
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  val collection: Future[BSONCollection] = mongoService.mongoDB.map(_.collection("books"))

  override def findByOid(oid: String): BookDBO = ???

  override def findByISBN(isbn: String): BookDBO = ???

  override def findByTitleAndAuthor(title: Option[String], author: Option[String]): BookDBO = ???

  override def findBook(_id: Option[String], title: Option[String], isbn: Option[String], author: Option[String], publisher: Option[String], category: Option[String], cover: Option[String], pages: Option[Int], condition: Option[String], rating: Option[Double], grRating: Option[Double], location: Option[String], status: Option[String], note: Option[String], read: Option[Boolean]): List[BookDBO] = ???
}
