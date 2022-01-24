package com.thisalv.getrange

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Displays basic data about Booyah % percent ranges for a specific character represented by its
 * SSBU stock icon.
 *
 * @property min Left percentage to print on text
 * @property max Right percentage to print on text
 *
 * @constructor Tries to read `min` and `max` from attributes then update displayed text
 */
class BooyahRangeView constructor(
    context: Context, attrs: AttributeSet
): AppCompatTextView(context, attrs) {
    private var min: Int = 0
    private var max: Int = 0

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.BooyahRangeView, 0, 0
        ).apply {
            try { // getInt() might fail if this TypedArray wasn't recycled by previous operation
                min = getInt(R.styleable.BooyahRangeView_min, 0)
                max = getInt(R.styleable.BooyahRangeView_max, 0)

                update()
            } finally {
                recycle() // Required for next operations with TypedArray to run as expected
            }
        }
    }

    /**
     * Applies "{min}%-{max}%" to `TextView` text attribute, with `min` & `max` being private
     * properties assigned by last attribute set.
     */
    private fun update() {
        this@BooyahRangeView.text = context.getString(R.string.range, min, max)
    }

    /**
     * Updates `min`, then updates text and ask for redraw
     */
    fun setMin(min: Int) {
        this.min = min // Ça fait Min-min ptdrrr
        update()
    }

    /**
     * Updates `max`, then updates text and ask for redraw
     */
    fun setMax(max: Int) {
        this.max = max
        update()
    }
}