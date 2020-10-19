import org.w3c.dom.CanvasRenderingContext2D
import utils.*

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

        ctx.beginPath()
        ctx.strokeStyle = Color.toStr(0.5f, 0f, 0.6f, 0f)
        drawKdtree(ctx, core.kdTreePoints)
        ctx.stroke()
    }

    private fun drawKdtree(ctx: CanvasRenderingContext2D, kdTreePoints: KDTree) {
        drawLine(ctx, kdTreePoints.root, null, null)
    }

    private fun drawLine(ctx: CanvasRenderingContext2D, node: KDNode, x: Double?, y: Double?) {
        var newX = x
        var newY = y
        if (node.value != null) {
            newX = node.value.x
            newY = node.value.y
        } else {
            node.splitByX.then { newX = node.splitAt } ?: kotlin.run { newY = node.splitAt }
        }

        if (x != null && y != null && newX != null && newY != null) {
            ctx.moveTo(x, y)
            ctx.lineTo(newX!!, newY!!)
        }

        node.left?.let { drawLine(ctx, it, newX, newY) }
        node.right?.let { drawLine(ctx, it, newX, newY) }
    }
}

private fun CanvasRenderingContext2D.fillRect(pos: Point2D, w: Double, h: Double) {
    fillRect(pos.x - w / 2, pos.y - h / 2, w, h)
}
