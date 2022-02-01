package com.bigzij.mongo.models

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, BSONObjectID, Macros}

import java.time.Instant

case class BookDBO(
                 _id: Option[BSONObjectID] = None,
                 createdDate: Option[Instant] = None,
                 title: String,
                 isbn: String,
                 author: String,
                 publisher: String,
                 category: String,
                 language: String,
                 cover: String,
                 pages: Int,
                 condition: Option[String] = None,
                 rating: Option[Double] = None,
                 grRating: Option[Double] = None,
                 location: Option[String] = None,
                 status: Option[String] = None,
                 notes: Option[String] = None,
                 read: Boolean = false,
                 dateBought: Option[Instant] = None
               )

object BookDBO {
  implicit def bookWriter: BSONDocumentWriter[BookDBO] = Macros.writer[BookDBO]
  implicit def bookReader: BSONDocumentReader[BookDBO] = Macros.reader[BookDBO]
}
