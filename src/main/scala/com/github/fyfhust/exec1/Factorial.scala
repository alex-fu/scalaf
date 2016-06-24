package com.github.fyfhust.exec1

/**
  * Created by fuyf on 6/24/16.
  */
object Factorial {
  def factorial(n: Long): Long = {
    def go(n: Long, f: Long): Long = n match {
      case 1 => f
      case c => go(c-1, f * c)
    }
    go(n, 1)
  }

  import CommonPrint._
  def main(args: Array[String]): Unit = {
    println(formatFunctionResult("factorial", 5, factorial))
  }
}
