package models

import scala.collection.mutable

object TemporaryLibrary {
  val tempLibrary = new mutable.ListBuffer[RealBook]()

  tempLibrary += RealBook(
    title = "1Q84: The Complete Trilogy",
    isbn = "978 0 09957 807 9",
    author = "Haruki Murakami",
    publisher = "Vintage Publishing",
    category = "Alternate History, Dystopian Sci-Fi",
    language = "English",
    cover = "P",
    pages = 1328,
    condition = Some("10"),
    grRating = Some(3.9),
    notes = Some("Bought under RY")
  )

  tempLibrary += RealBook(
    title = "Jitterbug Perfume",
    isbn = "978 0 55340 383 1",
    author = "Tom Robbins",
    publisher = "Transworld Publishers Ltd",
    category = "Fantasy Humor,Magical Realism",
    language = "English",
    cover = "P",
    pages = 359,
    condition = Some("2H"),
    grRating = Some(4.24),
    notes = Some("Bought 2nd hand in Petrivka Book Market in Kyiv, Ukraine")
  )

  tempLibrary += RealBook(
    title = "Chernobyl: History of a Tragedy",
    isbn = "978 0 14198 835 1",
    author = "Serhii Plokhy",
    publisher = "Penguin Books Ltd",
    category = "Nonfiction, Political, History, Science, Cultural, Soviet",
    language = "English",
    cover = "P",
    pages = 432,
    condition = Some("9.25"),
    rating = Some(5),
    grRating = Some(4.24),
    read = true
  )
}