package thread

import java.util.concurrent.CountDownLatch

object CountDownLatchLockExample1 extends App {
  var globalIndex = 0
  val numberTasks = 10
  val startLatch = new CountDownLatch(1)
  val doneLatch = new CountDownLatch(numberTasks)
  (0 until 10).foreach(_ => {
    new Thread(Worker(startLatch, doneLatch)).start()
  })
  doneLatch.await()
}

case class Worker(startLatch: CountDownLatch, doneLatch: CountDownLatch) extends Runnable {
  override def run(): Unit = {
    startLatch.countDown()
    doWork()
    if (CountDownLatchLockExample1.globalIndex <= 10) {
      doneLatch.countDown()
    }
  }

  def doWork(): Unit = {
    CountDownLatchLockExample1.globalIndex += 1
    println("Global index is incremented by Worker", CountDownLatchLockExample1.globalIndex)
  }
}
