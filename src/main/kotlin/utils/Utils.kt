package utils

import kotlin.math.PI
import kotlin.random.Random

val TWO_PI: Float = (PI * 2f).toFloat()
val HALF_PI: Float = (PI / 2f).toFloat()

fun normalizeAngle(angle: Float): Float {
    // reduce the angle
    var result = angle % TWO_PI

    //force it to be the positive remainder, so that 0 <= angle < 360
    result = (result + TWO_PI) % TWO_PI

    //force into the minimum absolute value residue class, so that-180 < angle <= 180
    if (result > PI) {
        result -= TWO_PI
    }
    return result
}

fun f(v: Double): String {
    return v.format(2)
}

fun f(v: Float): String {
    return v.format(2)
}

fun Number.f(): String {
    return this.format(2)
}

fun Number.f1(): String {
    return this.format(1)
}

fun Float.asPi(): Number {
    return this / PI
}

fun Double.asPi(): Number {
    return this / PI
}

fun <E> List<E>.fori(function: (E) -> Unit) {
    var i = 0
    while (i < size) {
        function(get(i))
        i++;
    }
}


fun <E> List<E>?.isNullOrEmpty(): Boolean {
    if (this == null) {
        return true
    }

    return this.isEmpty()
}

fun Double.format(digits: Int): String = this.asDynamic().toFixed(digits)
fun Number.format(digits: Int): String = this.asDynamic().toFixed(digits)
fun Float.format(digits: Int): String = this.asDynamic().toFixed(digits)

@Suppress("NOTHING_TO_INLINE")
inline fun String?.nonEmptyOr(s: String) = if (this.isNullOrEmpty()) {
    s
} else {
    this
}

inline fun <T> Boolean?.then(function: () -> T): T? {
    return if (this != null && this) {
        function()
    } else {
        null
    }
}

inline fun <T> T?.elze(function: () -> T): T {
    return if (this == null) {
        function()
    } else {
        this
    }
}


inline fun <T> Boolean?.then(mainFun: () -> T, elseFun: () -> T): T {
    return if (this == true) {
        mainFun()
    } else {
        elseFun()
    }
}

inline fun ignoreTryCatch(function: () -> Unit) {
    try {
        function()
    } catch (e: Throwable) {
        //ignore
    }
}

fun <E> List<E>.myRandom(): E {
    return get((this.size * Random.nextDouble()).toInt())
}

fun <E> List<E>.myRandomOrNull(): E? {
    if (isEmpty()) return null
    
    return myRandom()
}


