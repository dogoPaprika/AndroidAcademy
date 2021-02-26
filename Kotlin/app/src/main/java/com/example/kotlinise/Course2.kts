import java.lang.Exception

// String
val str = "abc"
str[0]
str.length
str.substring(1, 2)
val num = 6
"My favorite number is ${num * num}"

for (letter in str) print("$letter ")
str.forEach { print("$it ") }

// Functions
fun isEven(x: Int) = x % 2 == 0
isEven(5)

fun consonants(str: String): String {
    var result = ""
    for (letter in str) {
        if (letter !in "aeiouAEIOU")
            result += letter
    }
    return result
}

fun con(str: String) = str.filterNot { it in "aeiouAEIOU" }

println(consonants("opris"))
println(con("opris"))

// Extension functions
fun String.con2() = filterNot { it in "aeiouAEIOU" }
println("opris".con2())

fun Int.isEven2() = rem(2) == 0
println(4.isEven2())

// Higher order functions
data class Product(
    val name: String,
    val type: Type,
    val unitPrice: Double,
    val quantity: Double = 1.0
)

enum class Type {
    FOOD,
    OTHER
}

val milk = Product("milk", Type.FOOD, 3.5)
Product("car", Type.OTHER, 7000.0, 2.0)

fun Product.calculatePrice() = quantity * unitPrice * ((if (type == Type.FOOD) 119 else 105) / 100)

milk.calculatePrice()


fun Product.calculatePrice2(applyTax: (Product) -> Double) = quantity * unitPrice * applyTax(this)

milk.calculatePrice2 { product ->
    (if (product.type == Type.FOOD) 105.0 else 119.0) / 100
}

val romanianTax: Product.() -> Double = { if (type == Type.FOOD) 105.0 / 100 else 119.0 / 100 }
val usaTax: Product.() -> Double = { 115.0 / 100 }

val discount: Product.() -> Double = { if (quantity > 2) 2.0 else 0.0 }

milk.calculatePrice2(usaTax)
milk.calculatePrice2(romanianTax)

// Scope functions with, also, let, run, apply

class RectangularShape(var x: Int, var y: Int, var width: Int, var height: Int, var color: Int) {
    fun measure() {
    }

    fun render() {
    }
}

fun initShape(shape: RectangularShape?) {
    shape?.apply {
        x = 10; y = 20
        width = 100; height = 200
        color = 0xFF0066
    } ?: throw java.lang.IllegalArgumentException()
}

fun <T> T.apply(block: T.() -> Unit): T {
    block()
    return this
}

// Lists
val data = listOf(1, 2, 4, 'a')
val data1 = listOf<Int>(1, 2, 3)

data[0]
data + data1
data.forEach { print(it) }
data1.map { it.isEven2() }.forEach { print(it) }
data1.filter { it.isEven2() }.forEach { print(it) }

val data2 = listOf(4, 6, 34, 9, 2, 54, 7)
data2.mapIndexedNotNull { index, it -> if (!it.isEven2()) index else null }

fun Int.isPrime(): Boolean {
    return (2..this / 2).none { this % it == 0 }
}

2.isPrime()

// Objects

object DatabaseConnection {
    init {
        // connect to db
    }

    fun getData(): String = ""
}

DatabaseConnection.getData()

// Enum vs Sealed Classes

enum class Direction {
    N,
    S,
    E,
    W
}

fun printDirection(direction: Direction) =
    when (direction) {
        Direction.N -> print("north")
        Direction.S -> TODO()
        Direction.E -> TODO()
        Direction.W -> TODO()
    }

sealed class ServerResponse {
    class Success(val data: String) : ServerResponse()
    class Failed(val ex: Exception) : ServerResponse()
    object NoInternetConnection : ServerResponse()
}

fun handleResponse(response: ServerResponse) : Unit = when(response) {
    is ServerResponse.Success -> print(response.data)
    is ServerResponse.Failed -> throw response.ex
    ServerResponse.NoInternetConnection -> print("no internet")
}

handleResponse(ServerResponse.Success("ok"))
handleResponse(ServerResponse.NoInternetConnection)





