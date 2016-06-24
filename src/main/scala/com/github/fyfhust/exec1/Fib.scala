package com.github.fyfhust.exec1

/**
  * Created by fuyf on 6/24/16.
  */

object CommonPrint {
  def formatFunctionResult(name: String, n: Long, f: Long => Long): String = {
    val msg = "The %s of %d is %d"
    msg.format(name, n, f(n))
  }
}

object Fib {
  def fib(n: Long): Long = {
    @annotation.tailrec
    def go(cnt: Long, prev: Long, curr: Long): Long = cnt match {
      case m if m < 0 => sys.error("Negative number not allowed")
      case 0 => prev
      case c => go(c-1, curr, prev + curr)
    }
    go(n, 0, 1)
  }

  import CommonPrint._
  def main(args: Array[String]) = {
    println(formatFunctionResult("fib", 5, fib))
    println(formatFunctionResult("fib", 50, fib))
  }
}
