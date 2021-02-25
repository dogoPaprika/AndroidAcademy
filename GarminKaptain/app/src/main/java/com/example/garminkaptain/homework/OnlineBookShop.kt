package com.example.garminkaptain.data

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class HomeWork {
}

fun main() {
    scope.launch {
        while (true) {
            withContext(Dispatchers.IO) { OrderController.placeOrders() }
        }
    }
    runBlocking {
        delay(1 * 3600 * 1000)
    }
}

enum class BookType(val minutesToWarehouse: Int) {
    ADVENTURE(1),
    MYSTERY(2),
    HISTORICAL(3),
    FANTASY(4),
    DRAMA(5),
    ROMANCE(6)
}

data class Book(
    val id: Int,
    val name: String,
    val type: BookType
)

data class Order(
    val id: Int,
    val books: ArrayList<Book>
)

val scope = CoroutineScope(Job() + Dispatchers.Default)

object BookShop {
    private const val packTime = 4L

    private suspend fun fetchBook(book: Book): Book {
        println("Going to ${book.type} warehouse for ${book.id} book.")
        delay(BookType.valueOf(book.type.toString()).minutesToWarehouse.toLong() * 1000)
        println("Came back with ${book.id} book")

        // maybe modify some fields
        return book
    }

    suspend fun incomingOrder(order: Order) {
        scope.launch {
            println("Start order ${order.id}")

            val requests = ArrayList<Deferred<Book>>()

            order.books.forEach {
                requests.add(async { fetchBook(it) })
            }

            requests.awaitAll()

            // pack this order
            println("Started packing")
            delay(packTime * 1000)
            println("Finished packing")

            println("Finished order ${order.id}")
        }

    }
}

object OrderController {
    private const val maximumBooks = 5
    private const val minsBetweenOrders = 10L
    private const val nrOrders = 3

    // from 1 to 5 books, random types
    private fun createRandomOrder(): Order {
        val books = ArrayList<Book>()

        val size = Random.nextInt(1, maximumBooks + 1)
        for (i in 0 until size) {
            books.add(
                Book(Random.nextInt(), "Book $i",
                    Random.nextInt(BookType.values().size).let { BookType.values()[it] })
            )
        }

        return Order(Random.nextInt(), books)
    }

    // place some orders every 10 minutes
    suspend fun placeOrders() {
        println("Start orders turn ========================")

        val orders = ArrayList<Order>()
        for (i in 0 until nrOrders)
            orders.add(createRandomOrder())

        flow {
            orders.forEach {
                emit(it)
            }
        }
            .onStart { println("Starting the orders") }
            .collect {
                BookShop.incomingOrder(it)
            }

        delay(minsBetweenOrders * 1000)
        println("Next orders turn ===========================")
    }
}