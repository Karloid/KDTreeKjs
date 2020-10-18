import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import utils.Log
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date

class KdTreeLooper {
    private var lastTickTs: Double
    private var core: Core
    var canvas = document.getElementById("myCanvas") as HTMLCanvasElement;
    var ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    init {
        core = Core(canvas.width, canvas.height)

        val rect = canvas.getBoundingClientRect();

        document.onmousemove = { mouseEvent ->
            core.onMouseMove(mouseEvent.x - rect.x, mouseEvent.y - rect.y)
        }
        document.onmousedown = { mouseEvent ->
            core.onMouseDown(mouseEvent.x - rect.x, mouseEvent.y - rect.y, true)
        }
        document.onmouseup = { mouseEvent ->
            core.onMouseDown(mouseEvent.x - rect.x, mouseEvent.y - rect.y, false)
        }

        lastTickTs = Date().getTime()
    }

    fun drawLoop() {
        val currentTs = Date().getTime()
        val delta = currentTs - lastTickTs
        core.onTick(delta)
        draw(delta)
        window.setTimeout({ drawLoop() }, 16) //TODO uncomment
    }

    fun draw(delta: Double) {
        core.drawGame(ctx)
    }

}
