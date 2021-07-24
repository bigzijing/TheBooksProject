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

  val gotBook1 = RealBook(
    title = "A Song of Ice and Fire 1: A Game of Thrones",
    isbn = "978 0 55357 340 4",
    author = "George R. R. Martin",
    publisher = "Bantam",
    category = "Fantasy, Fiction, High Fantasy",
    language = "English",
    cover = "P",
    pages = 864,
    condition = Some("6"),
    rating = Some(4.8),
    grRating = Some(4.44),
    notes = Some("Bought under Megan"),
    read = true
  )

  tempLibrary += gotBook1

  tempLibrary += gotBook1.copy(
    title = "A Song of Ice and Fire 2: A Clash of Kings",
    isbn = "978 0 55357 990 1",
    pages =	1009,
    rating = Some(4.5),
    grRating = Some(4.4)
  )

  tempLibrary += gotBook1.copy(
    title = "A Song of Ice and Fire 3: A Storm of Swords",
    isbn = "978 0 55357 342 8",
    pages =	1216,
    condition = Some("10"),
    rating = Some(4.5),
    grRating = Some(4.54)
  )

  tempLibrary += gotBook1.copy(
    title = "A Song of Ice and Fire 4: A Feast of Crows",
    isbn = "978 0 55358 202 4",
    pages =	1048,
    condition = Some("10"),
    rating = Some(4),
    grRating = Some(4.11)
  )

  tempLibrary += gotBook1.copy(
    title = "A Song of Ice and Fire 5: A Dance with Dragons",
    isbn = "978 0 55358 201 7",
    pages = 1152,
    condition = Some("10"),
    rating = Some(4.5),
    grRating = Some(4.31)
  )

  tempLibrary += gotBook1.copy(
    title = "A Song of Ice and Fire 0: Boxset",
    isbn = "978 0 34553 552 8",
    pages = 5216,
    condition = Some("7.5"),
    rating = Some(4.5),
    grRating = Some(4.63)
  )
}