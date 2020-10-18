import utils.Log
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    window.onload = {
        Log.myLog("can ${document.getElementById("myCanvas")}")
        KdTreeLooper().drawLoop()
    }
}