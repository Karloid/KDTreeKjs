package utils

import kotlin.math.abs
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
class KDTree<T : KDTree.KDValue>(allPoints: MutableList<T>) {
    var root: KDNode<T>

    init {
        measureTime {
            root = KDNode(allPoints, depth = 0)
        }.let {
            Log.myLog("KDTree creation took $it ms from ${allPoints.size} elements")
        }
    }

    fun lookupClosestNaive(lookupPosition: T): T {
        var currentNode = root
        var iterations = 0
        while (true) {
            iterations++
            currentNode.value?.let {
                Log.myLog("found point in iterations=$iterations")
                return it
            }

            //TODO refactor
            currentNode = if (lookupPosition.getDimen(currentNode.depth) > currentNode.splitAt) {
                currentNode.right!!
            } else {
                currentNode.left!!
            }
        }
    }

    fun lookupClosestAccurate(lookupPosition: T): T {
        return root.lookupClosestAccurate(lookupPosition)!!
    }

    class KDNode<T : KDValue>(allElements: MutableList<T>, val depth: Int) {

        val value: T?
        val splitAt: Double
        val left: KDNode<T>?
        val right: KDNode<T>?

        init {
            if (allElements.size == 1) {
                value = allElements.first()
                left = null
                right = null
                splitAt = 0.0
            } else {
                value = null
                //TODO find running median
                allElements.sortBy { it.getDimen(depth) }

                val splitIndex = allElements.size / 2
                val medianElement = allElements[splitIndex]
                splitAt = with(medianElement) { getDimen(depth) }

                val leftList = allElements.subList(0, splitIndex)
                val rightList = allElements.subList(splitIndex, allElements.size)
                //  Log.myLog("splitIndex=$splitIndex splitByX=$splitByX medianElem=$medianElement " +
                //          "allPointsSize=${allPoints.size} leftList=$leftList rightList=$rightList")
                left = KDNode(leftList, depth + 1)
                right = KDNode(rightList, depth + 1)
            }
        }


        fun lookupClosestAccurate(lookupPosition: T): T? {
            value?.let { return it }

            val lookupToAxisDist: Double = abs(lookupPosition.getDimen(depth) - splitAt)
            val nextNode: KDNode<T> = if (lookupPosition.getDimen(depth) > splitAt) {
                    this.right!!
                } else {
                    this.left!!
                }
            val oppositeNode: KDNode<T>? = if (nextNode == right) left else right

            var best = nextNode.lookupClosestAccurate(lookupPosition)

            if (best == null || best.distance(lookupPosition) > lookupToAxisDist) {
                val oppositeBest = oppositeNode?.lookupClosestAccurate(lookupPosition)
                best = closer(best, oppositeBest, lookupPosition)
            }

            return best
        }

        private fun closer(p1: T?, p2: T?, lookupPosition: T): T? {
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

    interface KDValue {
        fun getDimen(dimenIndex: Int): Double
        fun distance(other: KDValue): Double
    }
}
