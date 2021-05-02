package models

case class Book(isbn: Long, title: String, view: Int = 0, description: Option[String] = None)

case class NewBook(title: String)