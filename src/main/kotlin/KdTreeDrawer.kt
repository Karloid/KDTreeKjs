import org.w3c.dom.CanvasRenderingContext2D
import utils.Point2D
import utils.fori
import utils.then

private val INACTIVE_POINT_SIZE = 1.0
private val MOUSE_SIZE = 4.0

class KdTreeDrawer(val core: Core) {
    fun draw(ctx: CanvasRenderingContext2D) {
        ctx.fillStyle = "white";
        ctx.fillRect(0.0, .0, ctx.canvas.width.toDouble(), ctx.canvas.height.toDouble())

        ctx.fillStyle = "blue";
        core.allPoints.fori { p ->
            ctx.fillRect(p, INACTIVE_POINT_SIZE, INACTIVE_POINT_SIZE)
        }

        core.mouseIsDown.then {
            ctx.fillStyle = "red";
            val mousePos = core.mousePos
            ctx.fillRect(mousePos, MOUSE_SIZE, MOUSE_SIZE)
        }

        core.closestPoint?.let { closet ->
            ctx.fillStyle = "green";
            ctx.fillRect(closet, MOUSE_SIZE, MOUSE_SIZE)
        }
    }
}

private fun CanvasRenderingContext2D.fillRect(pos: Point2D, w: Double, h: Double) {
    fillRect(pos.x - w / 2, pos.y - h / 2, w, h)
}
