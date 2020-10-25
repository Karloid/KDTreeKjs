package utils

import kotlin.math.abs
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

    fun lookupClosestNaive(lookupPosition: Point2D): Point2D {
        var currentNode = root
        var iterations = 0
        while (true) {
            iterations++
            currentNode.value?.let {
                Log.myLog("found point in iterations=$iterations")
                return it
            }

            //TODO refactor
            currentNode = if (currentNode.splitByX) {
                if (lookupPosition.x > currentNode.splitAt) {
                    currentNode.right!!
                } else {
                    currentNode.left!!
                }
            } else {
                if (lookupPosition.y > currentNode.splitAt) {
                    currentNode.right!!
                } else {
                    currentNode.left!!
                }
            }
        }
    }

    fun lookupClosestAccurate(lookupPosition: Point2D): Point2D {
        return root.lookupClosestAccurate(lookupPosition)!!
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
                left = KDNode(leftList, !splitByX)
                right = KDNode(rightList, !splitByX)
            }
        }


        fun lookupClosestAccurate(lookupPosition: Point2D): Point2D? {
            value?.let { return it }

            var nextNode: KDNode? = null
            var oppositeNode: KDNode? = null

            val lookupToAxisDist: Double
            nextNode = if (splitByX) {
                lookupToAxisDist = abs(lookupPosition.x - splitAt)
                if (lookupPosition.x > splitAt) {
                    this.right!!
                } else {
                    this.left!!
                }
            } else {
                lookupToAxisDist = abs(lookupPosition.y - splitAt)
                if (lookupPosition.y > this.splitAt) {
                    this.right!!
                } else {
                    this.left!!
                }
            }
            oppositeNode = if (nextNode == right) left else right

            var best = nextNode.lookupClosestAccurate(lookupPosition)

            if (best == null || best.distance(lookupPosition) > lookupToAxisDist) {
                val oppositeBest = oppositeNode?.lookupClosestAccurate(lookupPosition)
                best = closer(best, oppositeBest, lookupPosition)
            }

            return best
        }

        private fun closer(p1: Point2D?, p2: Point2D?, lookupPosition: Point2D): Point2D? {
            if (p1 == null) {
                return p2
            }
            if (p2 == null) {
                return p1
            }

            if (p1.distance(lookupPosition) > p2.distance(lookupPosition)) {
                return p2
            } else {
                return p1
            }
        }
    }
}
