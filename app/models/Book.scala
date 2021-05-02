package models

case class Book(isbn: Long, title: String, view: Int = 0, description: Option[String] = None)

case class NewBook(title: String)

case class RealBook(
                   title: String,
                   isbn: String,
                   author: String,
                   publisher: String,
                   category: String,
                   language: String,
                   cover: String,
                   pages: Int,
                   condition: Option[Double] = None,
                   rating: Option[Double] = None,
                   grRating: Option[Double] = None,
                   location: Option[String] = None,
                   status: Option[String] = None,
                   notes: Option[String] = None,
                   read: Boolean = false
                   )