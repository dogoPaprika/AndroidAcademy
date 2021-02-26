val x: Int = 5
println(x)

var name: String = "Miau"
name = "miau23"
println(name)

var nameNull: String? = null
val length = nameNull?.length ?: -1
println(length)

//val length2 = nameNull!!.length //not good

fun sum(x: Int, y: Int): Int {
    return x + y
}

fun sum2(x: Int, y: Int) = x + y
println(sum2(4, 5))

fun printName(name: String) {
    println(name)
}
printName("Milka")

fun printFormat(x: String, y: Int) {
    println("string: $x, integer: $y")
}
printFormat("miau", 890)

fun getGreatestValue(x: Int, y: Int): Int {
    return if (x > y) {
        x
    } else {
        y
    }
}

fun printValue(name: String) {
    when (name) {
        "miau" -> println("pisi")
        "ham" -> println("dogo")
        else -> println("altceva")
    }
}

printValue("ham")

fun printNumber() {
    for (i in 1..4) {
        println(i)
    }
}

printNumber()

class MyClass

val myObject = MyClass()

open class Person constructor(var name: String, val age: Int) {
    var address: String? = null

    lateinit var sex: String

    open fun printSex() {
        sex = "M"
        print(sex)
    }
}

val newPerson = Person("John", 20)
newPerson.address = "new address"

println("Name is ${newPerson.name}")
newPerson.printSex()

class Student(name1: String, age1: Int) : Person(name1, age1) {

    override fun printSex() {
        print("F")
    }
}

val student = Student("Jona", 20)
student.printSex()



