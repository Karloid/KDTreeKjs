package utils

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
class KDTree(allPoints: MutableList<Point2D>) {
    var root: KDNode

    init {
        measureTime {
            root = KDNode(allPoints, splitByX = true)
        }.let {
            Log.myLog("KDTree creation took $it ms from ${allPoints.size} elements")
        }
    }
}

class KDNode(allPoints: MutableList<Point2D>, val splitByX: Boolean) {

    val value: Point2D?
    val splitAt: Double
    val left: KDNode?
    val right: KDNode?

    init {
        if (allPoints.size == 1) {
            value = allPoints.first()
            left = null
            right = null
            splitAt = 0.0
        } else {
            value = null
            //TODO find running median
            allPoints.sortBy { splitByX.then { it.x } ?: it.y }

            val splitIndex = allPoints.size / 2
            val medianElement = allPoints[splitIndex]
            splitAt = with(medianElement) { splitByX.then { x } ?: y }

            val leftList = allPoints.subList(0, splitIndex)
            val rightList = allPoints.subList(splitIndex, allPoints.size)
            //  Log.myLog("splitIndex=$splitIndex splitByX=$splitByX medianElem=$medianElement " +
            //          "allPointsSize=${allPoints.size} leftList=$leftList rightList=$rightList")
            left = KDNode(leftList.toMutableList(), !splitByX)
            right = KDNode(rightList.toMutableList(), !splitByX)
        }
    }
}
