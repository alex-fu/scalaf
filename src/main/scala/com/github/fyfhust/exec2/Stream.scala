package com.github.fyfhust.exec2

import scala.annotation.tailrec

/**
  * Created by fuyf on 6/27/16.
  */
// unsuitable Stream structure
// because it use a () => A which is a function, so it will be not convenient for us to use them
//trait Stream[+A]
//case object Empty extends Stream[Nothing]
//case class Cons[+A](head: () => A, tail: () => Stream[A]) extends Stream[A]

trait Stream[+A] {
  def uncons: Option[(A, Stream[A])]
  def isEmpty: Boolean = uncons.isEmpty

  import Stream._
  def toListRec: List[A] = {
    @tailrec
    def go(s: Stream[A], acc: List[A]): List[A] = s.uncons match {
      case None => acc
      case Some((h,t)) => go(t, h :: acc)
    }
    go(this, Nil: List[A]).reverse
  }

  // omit reverse
  def toListRecFast: List[A] = {
    val buf = new collection.mutable.ListBuffer[A]
    @tailrec
    def go(s: Stream[A]): List[A] = s.uncons match {
      case Some((h,t)) => {
        buf += h
        go(t)
      }
      case _ => buf.toList
    }
    go(this)
  }

  def take(n: Int): Stream[A] = {
    if(n == 0) Stream.empty
    else
      uncons match {
        case None => Stream.empty
        case Some((h,t)) => {
          Stream.cons(h,t.take(n-1))
        }
      }
  }

  def drop(n: Int): Stream[A] = {
    if(n == 0) this
    else
      uncons match {
        case Some((h,t)) => t.drop(n-1)
        case _ => Stream.empty
      }
  }

  def takeWhile(f: A => Boolean): Stream[A] = {
    uncons match {
      case Some((h,t)) => if(f(h)) cons(h, t.takeWhile(f)) else empty
      case _ => empty
    }
  }

  def dropWhile(f: A => Boolean): Stream[A] = {
    uncons match {
      case Some((h,t)) => if(f(h)) t.dropWhile(f) else this
      case _ => empty
    }
  }

  def headOption: Option[A] = uncons match {
    case Some((h,t)) => Some(h)
    case _ => None
  }

  def tail: Stream[A] = uncons match {
    case Some((h,t)) => t
    case _ => empty
  }

  def foldRight[B](z: B)(op: (A, => B) => B): B = uncons match {
    case Some((h,t)) => op(h, t.foldRight(z)(op))
    case None => z
  }
  def foldLeft[B](z: B)(op: (=> B, A) => B): B = uncons match {
    case Some((h,t)) => t.foldLeft(op(z,h))(op)
    case None => z
  }

  // use foldRight can leverage the advantage of lazy evaluation on "=> B"
  def exists(p: A => Boolean): Boolean = foldRight(false){(a, b) =>
    p(a) || b
  }
  // don't use foldLeft because foldLeft will traverse all elements
//  def exists1(p: A => Boolean): Boolean = foldLeft(false){(b, a) =>
//    p(a) || b
//  }

  def forAll(p: A => Boolean): Boolean = foldRight(true){ (a,b) =>
    p(a) && b
  }

  def append[B >: A](b: Stream[B]): Stream[B] = {
    uncons match {
      case Some((h,t)) => cons(h, t.append(b))
      case None => b
    }
  }
  def #++[B >: A](b: Stream[B]): Stream[B] = append(b)

  def equals[A](a: Stream[A]): Boolean = (this.uncons, a.uncons) match {
    case (None, None) => true
    case (None, _) => false
    case (_, None) => false
    case (Some((h1,t1)), Some((h2,t2))) => (h1 == h2) && t1.equals(t2)
  }

  def map[B](f: A => B): Stream[B] = uncons match {
    case None => empty
    case Some((h,t)) => cons(f(h), t.map(f))
  }

  def flatMap[B](f: A => Stream[B]): Stream[B] = uncons match {
    case None => empty
    case Some((h,t)) => f(h) #++ t.flatMap(f)
  }
  def flatMap_1[B](f: A => Stream[B]): Stream[B] = foldRight(empty: Stream[B]){
    (a,b) => f(a) #++ b
  }
  // use foldRight is better than foldLeft, because #++ operation is well to apply on foldRight
  def flatMap_2[B](f: A => Stream[B]): Stream[B] = foldLeft(empty: Stream[B]){
    (b,a) => b #++ f(a)
  }

  def filter(f: A => Boolean): Stream[A] = {
    uncons match {
      case None => empty
      case Some((h,t)) => if(f(h)) cons(h, t.filter(f)) else t.filter(f)
    }
  }
  def filter_1(f: A => Boolean): Stream[A] = {
    foldRight(empty: Stream[A]){
      (a, b) => if(f(a)) cons(a, b) else b
    }
  }
  // use foldRight is better than foldLeft, because 'cons' is more efficient than #++
  def filter_2(f: A => Boolean): Stream[A] = {
    foldLeft(empty: Stream[A]) { (b, a) =>
      if(f(a)) b #++ Stream(a) else b
    }
  }
}

object Stream {
  val empty: Stream[Nothing] = new Stream[Nothing] {
    def uncons = None
  }
  def cons[A](h: => A, t: => Stream[A]): Stream[A] = new Stream[A] {
    def uncons = Some((h,t))
  }
  def apply[A](as: A*): Stream[A] = {
    if(as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))
  }
}
