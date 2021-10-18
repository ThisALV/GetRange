package com.thisalv.getrange

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView

/**
 * Displays basic data about Booyah % percent ranges for a specific character represented by its
 * SSBU stock icon.
 *
 * @property min Left percentage to print on text
 * @property max Right percentage to print on text
 *
 * @constructor Set font size to 30dp, and tries to read `min` and `max` from attributes then update
 * displayed text
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

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
    }

    /**
     * Applies "{min}%-{max}%" to `TextView` text attribute, with min & max extracted from
     * this element attributes, and applies a size of 30sp to this font.
     *
     * @param min New left % to print in the range text
     * @param max New right % to print in the range text
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