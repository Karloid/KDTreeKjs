import org.w3c.dom.CanvasRenderingContext2D
import utils.Log
import utils.Point2D
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

private val POINTS_COUNT = 1_00_000

class Core(val width: Int, val height: Int) {

    val mousePos = Point2D(0, 0)
    var mouseIsDown = false

    private val drawer = KdTreeDrawer(this)

    val allPoints = mutableListOf<Point2D>()
    var closestPoint: Point2D? = null

    init {
        repeat(POINTS_COUNT) {
            val x = Random.nextDouble() * width
            val y = Random.nextDouble() * height
            allPoints.add(Point2D(x, y))
        }
    }

    @OptIn(ExperimentalTime::class)
    fun onTick(delta: Double) {
        if (mouseIsDown) {

            closestPoint = measureTimedValue { allPoints.minBy { it.distance(mousePos) } }.let {
                Log.myLog("found closest in ${it.duration.inMilliseconds}ms method=linear")
                it.value
            }
        } else {
            closestPoint = null
        }
    }

    fun drawGame(ctx: CanvasRenderingContext2D) {
        drawer.draw(ctx)
    }

    fun onMouseMove(x: Double, y: Double) {
        //Log.myLog("onMouseMove $x $y")
        mousePos.x = x
        mousePos.y = y
    }

    fun onMouseDown(x: Double, y: Double, isDown: Boolean) {
        //Log.myLog("onMouseDown $x $y $isDown")
        mousePos.x = x
        mousePos.y = y
        mouseIsDown = isDown
    }
}
