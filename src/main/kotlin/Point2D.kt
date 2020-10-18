import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

class Point2D {
    var x: Int = 0
    var y: Int = 0

    val isNull: Boolean
        get() = this.x or this.y == 0

    constructor(x: Int = 0, y: Int = x) {
        this.x = x
        this.y = y
    }

    constructor(vect: Point2D) {
        this.x = vect.x
        this.y = vect.y
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || !(other is Point2D)) {
            return false
        }
        return this.x == other.x && this.y == other.y
    }

    operator fun set(x: Int, y: Int): Point2D {
        this.x = x
        this.y = y
        return this
    }

    fun set(a: Point2D): Point2D {
        this.x = a.x
        this.y = a.y
        return this
    }

    fun add(a: Point2D): Point2D {
        this.x += a.x
        this.y += a.y
        return this
    }

    fun sub(a: Point2D): Point2D {
        this.x -= a.x
        this.y -= a.y
        return this
    }

    fun mult(a: Int): Point2D {
        this.x *= a
        this.y *= a
        return this
    }

    operator fun div(a: Int): Point2D {
        this.x /= a
        this.y /= a
        return this
    }

    fun negate(): Point2D {
        this.x = -this.x
        this.y = -this.y
        return this
    }

    fun normalize(): Point2D {
        if (isNull)
            return this

        val absx = abs(this.x)
        val absy = abs(this.y)
        if (absx > absy) {
            this.x /= absx
            this.y = 0
        } else if (absx < absy) {
            this.x = 0
            this.y /= absy
        } else {
            this.x /= absx
            this.y /= absy
        }
        return this
    }


    fun manhattanDistance(): Int {
        return abs(x) + abs(y)
    }

    fun manhattanDistance(a: Point2D): Int {
        return abs(this.x - a.x) + abs(this.y - a.y)
    }

    fun tchebychevDistance(): Int {
        return (x, y)
    }

    fun tchebychevDistance(a: Point2D): Int {
        return max(abs(this.x - a.x), abs(this.y - a.y))
    }

    fun euclidianDistance2(): Double {
        return (x * x + y * y).toDouble()
    }

    fun euclidianDistance2(a: Point2D): Double {
        return pow((this.x - a.x).toDouble(), 2.0) + pow((this.y - a.y).toDouble(), 2.0)
    }

    fun euclidianDistance(): Double {
        return sqrt(euclidianDistance())
    }

    fun euclidianDistance(a: Point2D): Double {
        return sqrt(euclidianDistance2(a))
    }

    override fun toString(): String {
        return "[$x:$y]"
    }

    inline fun applyDir(direction: Direction): Point2D {
        return plus(getPointByDir(direction))
    }

    inline operator fun plus(point: Point2D): Point2D {
        return plus(point.x, point.y)
    }

    inline fun plus(x: Int, y: Int): Point2D {
        this.x += x
        this.y += y
        return this
    }


    inline fun dirTo(target: Point2D): Direction {
        return when {
            x + 1 == target.x -> Direction.RIGHT
            x - 1 == target.x -> Direction.LEFT
            y + 1 == target.y -> Direction.DOWN
            y - 1 == target.y -> Direction.UP
            else -> {
                logDebug { "unable to find dir for $this $target" }
                Direction.UP
            }
        }
    }

    fun copy(): Point2D {
        return Point2D(x, y)
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    fun abs() {
        x = abs(x)
        y = abs(y)
    }


    companion object {

        fun add(a: Point2D, b: Point2D): Point2D {
            return Point2D(a).add(b)
        }

        fun sub(a: Point2D, b: Point2D): Point2D {
            return Point2D(a).sub(b)
        }

        fun mult(a: Point2D, b: Int): Point2D {
            return Point2D(a).mult(b)
        }

        fun div(a: Point2D, b: Int): Point2D {
            return Point2D(a).div(b)
        }

        val UP = Point2D(0, -1)
        val RIGHT = Point2D(1, 0)
        val DOWN = Point2D(0, 1)
        val LEFT = Point2D(-1, 0)

        inline fun getPointByDir(direction: Direction): Point2D {
            return when (direction) {
                Direction.LEFT -> LEFT
                Direction.UP -> UP
                Direction.RIGHT -> RIGHT
                Direction.DOWN -> DOWN
            }
        }

        fun allDirections(): List<Point2D> {
            return listOf(
                UP,
                RIGHT,
                DOWN,
                LEFT
            )
        }

    }
}
