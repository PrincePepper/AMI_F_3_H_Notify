package sample

import javafx.concurrent.Task

class test {
    val task = object : Task<Void>() {
        @Throws(InterruptedException::class)
        override fun call(): Void? {
            //Thread.sleep(config.waitTime.toLong())

            return null
        }
    }
    //Thread(task).start()
}