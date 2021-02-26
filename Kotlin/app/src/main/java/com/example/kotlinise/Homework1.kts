import kotlin.math.sqrt

// Exercise 1
/*
A. Define a Kotlin class representing a bank Account. The class should keep an evidence of
the actual balance and should provide functionality to withdraw and deposit money.
 */

open class BankAccount {

    var balance : Int = 0
    protected set

    fun deposit(money: Int) {
        balance += money
    }

    open fun withdraw(money: Int) {
        balance -= money
    }

    override fun toString(): String {
        return "balance: $balance"
    }
}

/*
B. Specialize (subclass) the Account class into a SavingAccount class which allows
withdrawing only if the balance remains positive.
 */

class SavingAccount : BankAccount() {

    override fun withdraw(money: Int) {
        if (balance - money > 0) {
            balance -= money
        } else {
            println("Sorry, you can`t withdraw that amount of money!")
        }
    }
}

val savingAccount = SavingAccount()
savingAccount.deposit(50)
println(savingAccount)
savingAccount.deposit(100)
println(savingAccount)
savingAccount.withdraw(100)
println(savingAccount)
savingAccount.withdraw(100)
println(savingAccount)

// Exercise 2

fun cardinal(degree: Int = 0): String {
    return when (degree) {
        in (0..90) -> "N"
        in (90..180) -> "W"
        in (180..270) -> "S"
        in (270..360) -> "E"
        else -> "You entered a wrong degree!"
    }
}

println(cardinal(146))
println(cardinal(290))
println(cardinal(10004))

// Exercise 3
/*
Write a function which takes an angle as Int [0, 360) and returns the corresponding cardinal
directions as String (‘N’, ‘W’, ‘S’, ‘E’).
 */

val word = "Opris Vlad"
var count = 0

for (letter in word) {
    if (letter in "aeiouAEIOU")
        count += 1
}

println("There are $count vocals in the word")

// Exercise 4
// Given the following class definition:

class RectangularShape(var x: Int, var y: Int, var width: Int, var height: Int, var color: Int) {
    fun measure() {
        println("Shape measured")
    }

    fun render() {
        println("Shape rendered")
    }

    // A. Rewrite the following control structure using 'when':
    fun validateShape(shape: RectangularShape): Boolean {
        return when {
            (shape.x < 0 || shape.y < 0) -> {
                print("invalid position"); false
            }
            (shape.width > 1024 || shape.height > 1024) -> {
                print("shape too big"); false
            }
            (shape.color < 0 || shape.color > 0xFFFFFF) -> {
                print("invalid colors"); false
            }
            else -> true
        }
    }

    // B. Rewrite initShape using 'apply' or 'run':
    fun initShape(shape: RectangularShape?) {
        shape?.apply {
            x = 10
            y = 20
            width = 100
            height = 200
            color = 0xFF0066
        } ?: throw IllegalArgumentException()
    }

    // C. Rewrite drawShape using 'let' or 'also'
    fun drawShape(shape: RectangularShape?) {
        shape?.let {
            validateShape(it)
            it.measure()
            it.render()
        }
    }
}

val shape = RectangularShape(5, 10, 45, 50, 0xAA1234)
shape.drawShape(shape)


// Exercise 5
// Given the following list:

val data = listOf(4, 6, 34, 9, 2, 4, 7)

// A. Print the list
data.forEach { print("$it ") }

// B. Print the list in reverse order
data.reversed().map { print("$it ") }

// C. Print the list with no duplicates
data.toSet().map { print("$it ") }

// D. Print first 3 elements of the list
data.take(3).map { print("$it ") }

// E. Print if all elements are positive
data.filter { it > 0 }.map { print("$it ") }

// F. Print square root of all elements in the list
data.map { it * it }.map { print("$it ") }

// G. Print even numbers only
data.filter { it.rem(2) == 0 }.map { print("$it ") }

// H. Print index (position in the list) of odd numbers
data.iterator().forEach { if (it.rem(2) == 1) print("${data.indexOf(it)} ") }

// I. Print all prime number
fun Int.isPrime(): Boolean {
    return (this > 1) && (2..sqrt(this.toDouble()).toInt()).none { this % it == 0 }
}

data.filter { it.isPrime() }.map { print("$it ") }

// J. Print last prime number
data.last { it.isPrime() }

// Exercise 6
// Given the following data structure:

data class Student(val name: String, val address: String, val grade: Int)

val students = listOf(
    Student("John", "Boston", 6), Student("Jacob", "Baltimore", 2),
    Student("Edward", "New York", 7), Student("William", "Providence", 6),
    Student("Alice", "Philadelphia", 4), Student("Robert", "Boston", 7),
    Student("Richard", "Boston", 10), Student("Steven", "New York", 3)
)

//A. Print students
students.map { print("$it \n") }

//B. Print students in alphabetical order
students.sortedBy { it.name }.map { print("$it \n") }

//C. Print student names only in alphabetical order
students.sortedBy { it.name }.map { print("${it.name} \n") }

//D. Print students sorted by grade and name
students.sortedWith(compareBy({ it.grade }, { it.name })).map { print("$it \n") }

//E. Print students grouped by their address
students.groupBy { it.address }.map { print("$it \n") }