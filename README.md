# Hamsters

A micro Scala utility library. Compatible with functional programming beginners :)

![hamster picture](http://loicdescotte.github.io/images/hamster.jpg)

## Simple validation

Validation is right biased, i.e. Right is used for the "success side" and left for the "failure" side.

```scala
 val e1: Either[String, Int] = Right(1)
 val e2: Either[String, Int] = Left("error 1")
 val e3: Either[String, Int] = Left("error 2")
 
 val validation = Validation(e1,e2, e3)
 val failures = validation.failures //List[String] : List("error 1", "error 2")
 val successes = validation.successes //List[Int] : List(1)
```
 
##  Simple monad transformers

Example : combine Future and Option types then make it work in a for comprehension.  
More information on why it's useful [here](http://loicdescotte.github.io/posts/scala-compose-option-future/).

```scala
def foa: Future[Option[String]] = Future(Some("a"))
def fob(a: String): Future[Option[String]] = Future(Some(a+"b"))

val composedAB: Future[Option[String]] = (for {
  a <- FutureOption(foa)
  ab <- FutureOption(fob(a))
} yield ab).future
```
Currently hamsters only supports FutureEither and FutureOption monad transformers but more will come!

## HList

HLists can contain heterogeneous data types. It's like tuples on steroids!
 
```scala
val hlist = 2.0 :: "hi" :: HNil

hlist ++ ("hello" :: true :: HNil) //(2.0 :: (hi :: (hello :: (true :: HNil))))

(2.0 :: "hi" :: HNil).foldLeft("")(_+_) // "2.0hi"

(2.0 :: "hi" :: HNil).map(_.toString) // "2.0" :: "hi" :: HNil

(2.0 :: "hi" :: HNil).filter{
      case s: String if s.startsWith("h") => true
      case _ => false
    } //"hi" :: HNil

hlist.head // 2.0
hlist.tail // "hi" :: HNil
```

## Union types

You can define functions or methods that are able to return several types, depending on the context.

```scala
//json element can contain a String, a Int or a Double
def jsonElement(x: Int): Union3[String, Int, Double] = {
  if(x == 0) "0"
  else if (x % 2 == 0) 1
  else 2.0
}
```

## Right biased Either

Either is not biased is standard Scala library. With this helper, `map` and `flatMap` can be used by default as on the right side of Either, for example in for comprehension. 

```scala
import io.github.hamsters.Implicits._

val e1: Either[String, Int] = Right(1)
val e2: Either[String, Int] = Left("nan")
val e3: Either[String, Int] = Left("nan2")

// Stop at first error
for {
  v1 <- e1
  v2 <- e2
  v3 <- e3
} yield(s"$v1-$v2-$v3")  //Left("nan")
```