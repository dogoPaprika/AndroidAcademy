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

    suspend fun incomingOrder(orders: ArrayList<Order>) {
        // flow for every order in the list
        flow {
            orders.forEach {
                emit(it)
            }
        }
            .onStart { println("Starting the orders") }
            .onEach { order ->
                // flow for every book in the order
                flow {
                    order.books.forEach { book ->
                        println("Going to ${book.type} warehouse.")

                        // delay for going to the book`s warehouse
                        delay(BookType.valueOf(book.type.toString()).minutesToWarehouse.toLong() * 60 * 1000)
                        emit(book)
                    }
                }
                    .onStart { println("Start order ${order.id}") }
                    .onEach { println("Came back with ${it.id} book") }
                    .onCompletion {
                        // pack this order
                        println("Started packing")
                        delay(packTime * 60 * 1000)
                        println("Finished packing")

                        println("Finished order ${order.id}")
                    }
                    .collect {  }
            }
            .onCompletion { println("Orders are ready!") }
            .collect {  }
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
        println("Start order turn")

        val orders = ArrayList<Order>()
        for (i in 0 until nrOrders)
            orders.add(createRandomOrder())

        withContext(Dispatchers.IO) { BookShop.incomingOrder(orders) }

        delay(minsBetweenOrders * 60 * 1000)
        println("Next order turn")

    }
}