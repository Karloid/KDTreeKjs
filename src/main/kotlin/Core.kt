import org.w3c.dom.CanvasRenderingContext2D
import utils.KDTree
import utils.Log
import utils.Point2D
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

private val POINTS_COUNT = 1_000

class Core(val width: Int, val height: Int) {

    var changed = true

    val mousePos = Point2D(0, 0)
    var mouseIsDown = false

    private val drawer = KdTreeDrawer(this)

    val allPoints = mutableListOf<Point2D>()
    val kdTreePoints: KDTree
    var closestPoint: Point2D? = null

    init {
        repeat(POINTS_COUNT) {
            val x = Random.nextDouble() * width
            val y = Random.nextDouble() * height
            allPoints.add(Point2D(x, y))
        }

        kdTreePoints = KDTree(allPoints)
    }

    @OptIn(ExperimentalTime::class)
    fun onTick(delta: Double) {
        if (mouseIsDown) {

            closestPoint = measureTimedValue { linearLookup() }.let {
                Log.myLog("found closest in ${it.duration.inMilliseconds}ms method=linear")
                it.value
            }
            val linearResult = closestPoint
            closestPoint = measureTimedValue { kdTreePoints.lookupClosestAccurate(mousePos) }.let {
                Log.myLog("found closest in ${it.duration.inMilliseconds}ms method=kdtree")
                it.value
            }

        } else {
            closestPoint = null
        }
    }

    private fun linearLookup() = allPoints.minBy { it.distance(mousePos) }

    fun drawGame(ctx: CanvasRenderingContext2D) {
        if (!changed) {
            return
        }
        drawer.draw(ctx)
        changed = false
    }

    fun onMouseMove(x: Double, y: Double) {
        //Log.myLog("onMouseMove $x $y")
        mousePos.x = x
        mousePos.y = y
        changed = true
    }

    fun onMouseDown(x: Double, y: Double, isDown: Boolean) {
        //Log.myLog("onMouseDown $x $y $isDown")
        mousePos.x = x
        mousePos.y = y
        mouseIsDown = isDown
        changed = true
    }
}
