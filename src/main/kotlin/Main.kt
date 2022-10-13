import java.lang.Appendable
import java.security.Provider
import java.security.Provider.Service
import java.util.*

/**
 * Generics
 */


/**
 * Generic function and properties
 */

val <T> List <T>.penultimate: T
    get() = this[size-2]

/**
 * Type parameter constraints
 */

fun <T: Number> oneHalf (value: T): Double{
    return value.toDouble() / 2.0
}

fun <T: Comparable<T>> max (first: T, second: T): T{
    return if (first > second) first else second
}

fun <T> ensureTrailingPeriod(seq: T)
where T: CharSequence, T: Appendable{
    if (!seq.endsWith('.')){
        seq.append('.')
    }
}

class Processor<T: Any>{
    fun process(value: T){
        value.hashCode()
    }
}

/**
 * Generics at runtime
 *
 * star projection - *
 */

fun printSum(c: Collection<*>){
    val intList = c as? List<Int>
        ?: throw IllegalArgumentException("List is expected")
    println(intList.sum())
}

/**
 * Declaring functions with reified type parameters
 *
 * “reified” declares that this type parameter will
 * not be erased at runtime.
 */

inline fun <reified T> isA (value: Any) = value is T

/**
 * Replacing class references with reified type parameters
 */

val serviceImpl = ServiceLoader.load(Service::class.java)

val serviceImpl2 = loadService<Service>()

inline fun <reified T> loadService(): ServiceLoader<T> {
    return ServiceLoader.load(T::class.java)
}

/**
 * Variance: generics and subtyping
 *
 * supertype and subtypes
 *
 * superclass and subclass
 *
 * covariance
 * covariant Producer (out T)
 * subtyping is preserved:
 * Producer<Cat> is a subtype of Producer<Animal>
 * T only in OUT positions
 *
 * contravariance
 * contravariant Consumer (in T)
 * subtyping is reversed:
 * Consumer<Animal> is a subtype of Consumer<Cat>
 * T only in IN positions
 *
 * invariance
 * invariant MutableList<T>
 * no subtyping, T in any positions
 *
 * P stands for the parameter type (in positions)
 *
 * R stands for the return type (out positions)
 *
 * declaration-site variance (Kotlin)
 * use-site variance (Java)
 */

fun printContests(list: List<Any>){
    println(list.joinToString())
}

fun addAnswer(list: MutableList<Any>){
    list.add(42)
}

fun <T> copyData(source: MutableList<T>,
destination: MutableList<T>){
    for (item in source){
        destination.add(item)
    }
}

fun <T: R,R> copyData2(source: MutableList<T>,
                      destination: MutableList<R>){
    for (item in source){
        destination.add(item)
    }
}

fun <T> copyData3(source: MutableList<out T>,
                 destination: MutableList<T>){
    for (item in source){
        destination.add(item)
    }
}

fun <T> copyData4(source: MutableList<T>,
                  destination: MutableList<in T>){
    for (item in source){
        destination.add(item)
    }
}

/**
 * Star projection: using * instead of a type argument
 *
 * MutableList<*> tells that it contains of a specific type,
 * but you don't know what type it is
 */

fun printAll(list: List<*>){
    if (list.isNotEmpty()){
        for (i in list){
            println(i)
        }
    }
}

fun <T> printAll2 (list: List<T>){
    if (list.isNotEmpty()){
        for (i in list){
            println(i)
        }
    }
}

fun printFirst (list: List<*>){
    if (list.isNotEmpty()){
        println(list.first())
    }
}

fun <T>printFirst2 (list: List<T>){
    if (list.isNotEmpty()){
        println(list.first())
    }
}


fun main(){
    val readers: MutableList<String> = mutableListOf()
    val readers2 = mutableListOf<String>()

    val letters = ('a'..'z').toList()
    println(letters.slice<Char>(0..2))

    println(letters.slice(10..13))

   val testGeneric = listOf(1,2,3,4).penultimate
    println(testGeneric)

    println(oneHalf(10))

    println(max("kotlin", "java"))
    val helloWorld = StringBuilder("Hello world")
    ensureTrailingPeriod(helloWorld)
    println(helloWorld)

    printSum(listOf(1,2,3))

    println(isA<String>("abc"))
    println(isA<String>(123))

    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>())

    printContests(listOf("abc", 123))

    val ints = mutableListOf(1,2,3)
    val anyItems = mutableListOf<Any>()
    copyData2(ints, anyItems)
    println(anyItems)

    printAll(listOf("Svetlana", "Dmitry"))

    val testList = listOf("Svetlana", "Dmitry")
    val firstItem = testList.first()
    println(firstItem)

    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)

    println(Validators[String::class].validate("Kotlin"))
    println(Validators[Int::class].validate(42))

    

}