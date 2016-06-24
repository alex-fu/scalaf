package com.github.fyfhust.exec2

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by fuyf on 6/24/16.
  */
class TreeTest extends FlatSpec with Matchers {
  import Tree._

  val tree: Tree[Int] = Branch(Branch(Leaf(1), Branch(Leaf(2), Leaf(3))), Branch(Leaf(4),Branch(Leaf(5),Leaf(6))))
  "A Non-empty Full Tree" can "count its size" in {
    tree.size shouldEqual 11
    tree.sizeF shouldEqual 11
  }
  it can "count the max value" in {
    tree.maxValue shouldEqual 6
    maxValueF(tree) shouldEqual 6
  }
  it can "count the min value" in {
    tree.minValue shouldEqual 1
    minValueF(tree) shouldEqual 1
  }
  it can "count the depth" in {
    tree.depth shouldEqual 4
    tree.depthF shouldEqual(4)
  }
  it can "traverse to a List" in {
    tree.deepTraverse shouldEqual List(1,2,3,4,5,6)
    tree.deepTraverseF shouldEqual List(1,2,3,4,5,6)
  }
  it can "map a function to each element" in {
    tree.map(_ + 1).deepTraverse shouldEqual List(2,3,4,5,6,7)
    tree.map(_ + 1).deepTraverseF shouldEqual List(2,3,4,5,6,7)
  }
  it can "flatMap a function to each element" in {
    val testFlatMap: Int => Tree[Int] = {
      case a => Leaf(a * 2)
    }
    tree.flatMap(testFlatMap).deepTraverse shouldEqual List(2,4,6,8,10,12)
    tree.flatMap(testFlatMap).deepTraverseF shouldEqual List(2,4,6,8,10,12)
  }

  val nfTree: Tree[Int] = Branch(Branch(Leaf(1), Branch(Leaf(2), EmptyNode)), Branch(Leaf(3),Branch(EmptyNode,Leaf(4))))
  "A Non-empty non-full Tree" can "count its size" in {
    nfTree.size should === (9)
    nfTree.sizeF shouldBe 9
  }
  it can "count the max value" in {
    nfTree.maxValue shouldEqual 4
    maxValueF(nfTree) shouldBe 4
  }
  it can "count the min value" in {
    nfTree.minValue shouldEqual 1
    minValueF(nfTree) shouldBe 1
  }
  it can "count the depth" in {
    nfTree.depth shouldEqual 4
    nfTree.depthF shouldBe 4
  }
  it can "traverse to a list" in {
    nfTree.deepTraverse() shouldEqual List(1,2,3,4)
    nfTree.deepTraverseF() shouldEqual List(1,2,3,4)
  }
  it can "map a function to each element" in {
    nfTree.map(_ * 2).deepTraverse() shouldEqual List(2,4,6,8)
    nfTree.map(_ * 2).deepTraverseF() shouldEqual List(2,4,6,8)
  }
  it can "flatMap a function to each element" in {
    val testFlatMap: Int => Tree[Int] = {
      case a => Leaf(a+2)
    }
    nfTree.flatMap(testFlatMap).deepTraverse() shouldEqual List(3,4,5,6)
    nfTree.flatMap(testFlatMap).deepTraverseF() shouldEqual List(3,4,5,6)
  }

  val empTree: Tree[Int] = EmptyNode
  "A empty Tree" should "have 0 size" in {
    empTree.size shouldEqual 0
    empTree.sizeF shouldEqual 0
  }
  it should "have a depth of 0" in {
    empTree.depth should === (0)
    empTree.depthF should === (0)
  }
  it should "traverse to a empty List" in {
    empTree.deepTraverse should equal (List())
    empTree.deepTraverseF should equal (List())
  }
}
