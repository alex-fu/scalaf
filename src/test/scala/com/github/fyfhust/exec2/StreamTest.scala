package com.github.fyfhust.exec2

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by fuyf on 6/27/16.
  */
class StreamTest extends FlatSpec with Matchers {
  val stream: Stream[Int] = Stream( 1,2,3,4)
  "A Stream" should "has the ability to convert to a List" in {
    stream.toListRec shouldEqual List(1,2,3,4)
    stream.toListRecFast shouldEqual List(1,2,3,4)
  }
  it should "has the ability to take some from it" in {
    stream.take(1).toListRecFast shouldEqual Stream(1).toListRecFast
  }
  it should "apply takeWhile" in {
    stream.takeWhile(_ % 2 == 0).toListRecFast shouldEqual Stream.empty.toListRecFast
    stream.takeWhile(_ < 3).toListRecFast shouldEqual Stream(1,2).toListRecFast
  }
  it should "apply dropWhile" in {
    stream.dropWhile(_ % 2 == 0).toListRecFast shouldEqual Stream(1,2,3,4).toListRecFast
    stream.dropWhile(_ < 3).toListRecFast shouldEqual Stream(3,4).toListRecFast
  }
  it should "got head if exist" in {
    val stream = Stream(122,33,344,55)
    stream.headOption shouldEqual Some(122)
  }
  it should "got tail if exist" in {
    stream.tail.toListRecFast shouldEqual List(2,3,4)
  }
  it should "can do foldRight" in {
    val l = stream.foldRight(List(0): List[Int])((x, y) => x :: y)
    l shouldEqual List(1,2,3,4,0)

    stream.foldRight(0)(_ + _) shouldEqual 10
  }
  it should "has the ability to do foldLeft" in {
    val l = stream.foldLeft(List(0))((x,y) => y :: x).reverse
    l shouldEqual List(0,1,2,3,4)
  }
  it should "has the ability to append another Stream" in {
    val s = Stream(11,12,13)
    stream.append(s).equals(Stream(1,2,3,4,11,12,13)) shouldEqual true
  }
  it should "do combination convertion" in {
    stream.map(_ + 10).filter(_ % 2 == 0).map(_ * 3).toListRecFast shouldEqual Stream(36,42).toListRecFast
  }

  val emptyStream: Stream[Int] = Stream.empty
  "A empty stream" should "convert to a empty List" in {
    emptyStream.toListRec shouldEqual List()
    emptyStream.toListRecFast shouldEqual List()
  }
  it should "can't get head" in {
    emptyStream.headOption shouldEqual None
  }


}
