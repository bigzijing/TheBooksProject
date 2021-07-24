package models

case class Book(
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
                   read: Boolean = false
                   )