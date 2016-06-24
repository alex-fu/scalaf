package com.github.fyfhust.exec2

/**
  * Created by fuyf on 6/24/16.
  */
trait Tree[+A] {
  def size(): Int = this match {
    case EmptyNode => 0
    case Leaf(_) => 1
    case Branch(l,r) => 1 + l.size() + r.size()
  }

  def maxValue(): Int  = this match {
    case EmptyNode => Int.MinValue
    case Leaf(a: Int) => a
    case Branch(l,r) => l.maxValue max r.maxValue
  }

  def minValue(): Int = this match {
    case EmptyNode => Int.MaxValue
    case Leaf(a: Int) => a
    case Branch(l,r) => l.minValue() min r.minValue()
  }

  def depth(): Int = this match {
    case EmptyNode => 0
    case Leaf(_) => 1
    case Branch(l,r) => 1 + (l.depth() max r.depth())
  }

  def deepTraverse(): List[A] = this match {
    case EmptyNode => Nil
    case Leaf(a) => a :: Nil
    case Branch(l,r) => l.deepTraverse() ++ r.deepTraverse()
  }

  def fold[B](e: => B)(f: A => B)(g: (B,B) => B): B = this match {
    case EmptyNode => e
    case Leaf(a) => f(a)
    case Branch(l,r) => g(l.fold(e)(f)(g), r.fold(e)(f)(g))
  }

  def sizeF(): Int = fold(0)(a => 1)(1 + _ + _)
  def depthF(): Int = fold(0)(a => 1)((x, y) => 1 + (x max y))
  def deepTraverseF(): List[A] = fold(List[A]())(a => a :: Nil)(_ ++ _)

  def map[B](f: A => B): Tree[B] = this match {
    case EmptyNode => EmptyNode
    case Leaf(a) => Leaf(f(a))
    case Branch(l,r) => Branch(l.map(f), r.map(f))
  }

  def flatMap[B](f: A => Tree[B]): Tree[B] = this match {
    case EmptyNode => EmptyNode
    case Leaf(a) => f(a)
    case Branch(l,r) => Branch(l.flatMap(f), r.flatMap(f))
  }
}

case object EmptyNode extends Tree[Nothing]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]


object Tree {
  def maxValueF(tree: Tree[Int]): Int = tree.fold(Int.MinValue)(a => a)(_ max _)
  def minValueF(tree: Tree[Int]): Int = tree.fold(Int.MaxValue)(a => a)(_ min _)
}
