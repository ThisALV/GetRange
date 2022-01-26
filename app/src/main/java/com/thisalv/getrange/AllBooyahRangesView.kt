package com.thisalv.getrange

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Displays a first Booyah range, and a second one if it exists.
 *
 * @property firstMin % to show for `min` of the first range text view
 * @property firstMax % to show for `max` of the first range text view
 * @property secondMin % to show for `min` of the second range text view. `-1` if no second range
 * @property secondMax % to show for `max` of the second range text view. `-1` if no second range
 * @property hasSecondRange `true` if `secondMin` or `secondMax` is `-1` (then there is no
 * second range)
 *
 * @constructor Tries to read properties from attributes, then updating the underlying text views
 * displaying the booyah ranges. Default value is 0 for 1st range and -1 for 2nd range, meaning
 * that missing 2nd range value, min or max, means 2nd range doesn't exist.
 */
class AllBooyahRangesView constructor(
    context: Context, attrs: AttributeSet
): LinearLayout(context, attrs) {
    private var firstMin: Int
    private var firstMax: Int

    // second* properties must be initialized for hasSecondRange getter to work
    private var secondMin: Int = -1
    private var secondMax: Int = -1
    private var hasSecondRange: Boolean = secondMin != -1 || secondMax != -1

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.AllBooyahRangesView, 0, 0
        ).apply {
            try { // getInt() might fail if this TypedArray wasn't recycled by previous operation
                firstMin = getInt(R.styleable.AllBooyahRangesView_firstMin, 0)
                firstMax = getInt(R.styleable.AllBooyahRangesView_firstMax, 0)

                // Missing 2nd range value will lead to -1 value which is disabling this range
                secondMin = getInt(R.styleable.AllBooyahRangesView_secondMin, -1)
                secondMax = getInt(R.styleable.AllBooyahRangesView_secondMax, -1)
            } finally {
                recycle()
            }
        }
    }

    private fun update() {
        // Layout must be calculated again, and view must be redrawn
        requestLayout()
        invalidate()
    }

    /**
     * Updates `firstMin`.
     */
    fun setFirstMin(firstMin: Int) {
        this.firstMin = firstMin // Ça fait Min-min ptdrrr
        update()
    }

    /**
     * Updates `firstMax`.
     */
    fun setFirstMax(firstMax: Int) {
        this.firstMax = firstMax
        update()
    }

    /**
     * Updates `secondMin`.
     */
    fun setSecondMin(secondMin: Int) {
        this.secondMin = secondMin // Ça fait Min-min ptdrrr
        update()
    }

    /**
     * Updates `secondMax`.
     */
    fun setSecondMax(secondMax: Int) {
        this.secondMax = secondMax
        update()
    }
}