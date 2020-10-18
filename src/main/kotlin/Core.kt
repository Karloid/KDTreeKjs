import org.w3c.dom.CanvasRenderingContext2D
import utils.Log
import utils.Point2D
import kotlin.random.Random

class Core(val width: Int, val height: Int) {

    val mousePos = Point2D(0, 0)
    var mouseIsDown = false

    private val drawer = KdTreeDrawer(this)

    val allPoints = mutableListOf<Point2D>()

    init {
        repeat(100) {
            val x = Random.nextDouble() * width
            val y = Random.nextDouble() * height
            allPoints.add(Point2D(x, y))
        }
    }

    fun onTick(delta: Double) {

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
