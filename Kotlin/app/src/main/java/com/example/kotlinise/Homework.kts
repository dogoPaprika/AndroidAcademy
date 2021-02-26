import androidx.constraintlayout.solver.widgets.Rectangle

/*
1. Write a small program which generates pairs of systolic and diastolic blood pressure
values and prints a textual interpretation of the values, according to the following table
 */
val NORMAL = "NORMAL"
val ELEVATED = "ELEVATED"
val HIGH_BLOOD_PRESSURE_1 = "HIGH BLOOD PRESSURE: STAGE 1"
val HIGH_BLOOD_PRESSURE_2 = "HIGH BLOOD PRESSURE: STAGE 2"
val HIPERTENSIVE_CRISIS = "HIPERTENSIVE CRISIS"
val NO_SUCH_BLOOD_CATEGORY = "NO SUCH BLOOD CATEGORY"

fun checkBloodPressure(upperNumber: Int, lowerNumber: Int) = when {
    upperNumber < 120 && lowerNumber < 80 -> NORMAL
    upperNumber in 120..129 && lowerNumber < 80 -> ELEVATED
    upperNumber in 130..139 || lowerNumber in 80..89 -> HIGH_BLOOD_PRESSURE_1
    upperNumber in 140..180 || lowerNumber in 90..120 -> HIGH_BLOOD_PRESSURE_2
    upperNumber > 180 || lowerNumber > 120 -> HIPERTENSIVE_CRISIS
    else -> NO_SUCH_BLOOD_CATEGORY
}

fun mainEx1() {
    val systolicArray = intArrayOf(90, 125, 135, 145, 190)
    val distolicArray = intArrayOf(70, 75, 85, 95, 125)

    for (i in systolicArray.indices)
        for (j in distolicArray.indices) {
            val bloodCategory = checkBloodPressure(systolicArray[i], distolicArray[j])

            println("For systolic = ${systolicArray[i]} and distolic = ${distolicArray[j]}," +
                    " \n the blood pressure category is: ${bloodCategory}")
            if (bloodCategory.equals(HIPERTENSIVE_CRISIS))
                println("Consult your doctor immediately!")
        }
}

//mainEx1()

/*
2.Please model the followings:

A bank can issue debit and credit cards.

Cards in general have the following operations:
-	Pay (amount) -> [success/failure] – when card is used directly in payment transactions
-	Withdraw (amount) -> [success/failure] – withdraw money from an ATM
-	Deposit (amount) – deposit money to an ATM

Debit cards only work if balance is > 0.

Credit cards are allowed to access a credit line in a limit specific to each card instance.

Credit cards reward payment actions (not withdrawals) with some bonus, in the percentage of the amount payed. T
he percentage is global, according to the bank’s actual benefit plan.
 */

abstract class Card() {
    protected var money: Int = 0

    open fun pay(amount: Int): Boolean {
        if (checkCard(amount)) {
            money -= amount
            return true
        }
        return false
    }

    open fun withdraw(amount: Int): Boolean {
        if (checkCard(amount)) {
            money -= amount
            return true
        }
        return false
    }

    fun deposit(amount: Int) {
        money += amount
    }

    abstract fun checkCard(amount: Int): Boolean
}

class DebitCard: Card() {
    override fun checkCard(amount: Int): Boolean {
        return money >= amount
    }
}

class CreditCard(private val limit: Int): Card() {
    override fun checkCard(amount: Int): Boolean {
        return money + amount >= limit
    }

    override fun pay(amount: Int): Boolean {
        return if (super.pay(amount)) {
            money += (amount + Bank.BONUS).toInt()
            true
        } else
            false
    }
}

object Bank {
    const val BONUS = 5.5

    val creditCards = ArrayList<CreditCard>()
    val debitCards = ArrayList<DebitCard>()
}

fun mainEx2() {
    val creditCard1 = CreditCard(50)
    creditCard1.deposit(1000)

    creditCard1.withdraw(50)
    creditCard1.pay(90)

    val debitCard1 = DebitCard()
    debitCard1.deposit(500)

    debitCard1.withdraw(200)
    debitCard1.pay(100)

    Bank.creditCards.add(creditCard1)
    Bank.debitCards.add(debitCard1)
}

mainEx2()

/*
3. Write a Kotlin function which calculates the factorial of a positive integer number
 */
fun fact(x: Int): Int = if (x == 1) 1 else x * fact(x-1)

fun mainEx3() {
    val x = 5

    println("Factorial of $x is: ${fact(x)}")
}

//mainEx3()


/*
Exercise 4

Make the ‘render’ function more concise from the below snippet:
 */

data class Rectangle(val x: Int, val y: Int, val w: Int, val h: Int)

class Paint {
    var color: Long = 0x00FF00
    var strokeWidth: Int = 5
    fun drawRectangle(rect: Rectangle) {
        println("Drawing $rect color: $color stroke: $strokeWidth")
    }
}

fun render(paint: Paint?, rectangles: List<Rectangle?>) {
    if (paint != null) {
        paint.color = 0xFF0000
        for (rect in rectangles) {
            if (rect != null) {
                paint.drawRectangle(rect)
            }
        }
    }
}

fun render2(paint: Paint?, rectangles: List<Rectangle?>) {
    paint?.color = 0xFF0000
    rectangles.filterNotNull().forEach{paint?.drawRectangle(it)}
}

val rectangles = listOf(Rectangle(1, 2, 3, 4), Rectangle(4, 5, 6, 7),
    Rectangle(7, 8, 9, 10))

render(Paint(), rectangles)
render2(Paint(), rectangles)


//Exercise 5. Given the following data structure:
//
//1.	Print the minimum heart rate value
//2.	Print the average heart rate value
//3.	Print all heart rate values above 100
//4.	Print heart rate values grouped into a map, where keys are the dates, and values are lists of heart rate values measured on a specific date (represented by the key)
//5.	Print maximum heart rate values per each day
//

data class HeartRateEntry(val date: Long, val value: Int)

fun populateData(vararg dataPair: Pair<Long, Int>): List<HeartRateEntry> =
    dataPair.map { HeartRateEntry(it.first, it.second) }

val data = populateData(
    1612310400L to 76,
    1612310400L to 89,
    1612310400L to 44,
    1612224000L to 47,
    1612224000L to 115,
    1612224000L to 76,
    1612224000L to 87,
    1612137600L to 90,
    1612137600L to 167)

// 1.	Print the minimum heart rate value

println(data.minByOrNull { it.value }?.value)

//2.	Print the average heart rate value

println(data.map{it.value}.average())

//3.	Print all heart rate values above 100

data.filter{it.value > 100}.forEach { println(it.value) }

//4.	Print heart rate values grouped into a map, where keys are the dates,
// and values are lists of heart rate values measured on a specific date (represented by the key)

println(data.groupBy({it.date}, {it.value}))

//5.	Print maximum heart rate values per each day

data.groupBy({it.date}, {it.value}).forEach{ it -> it.value.maxByOrNull{it}.also { print(it) }}
