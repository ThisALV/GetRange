package com.thisalv.getrange

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView

/**
 * Displays basic data about Booyah % percent ranges for a specific character represented by its
 * SSBU stock icon.
 *
 * @constructor Applies "{min}%-{max}%" to `TextView` text attribute, with min & max extracted from
 * this element attributes, and applies a size of 30sp to this font.
 */
class BooyahRangeView constructor(
    context: Context, attrs: AttributeSet
): AppCompatTextView(context, attrs) {
    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.BooyahRangeView, 0, 0
        ).apply {
            try { // getInt() might fail if this TypedArray wasn't recycled by previous operation
                val min = getInt(R.styleable.BooyahRangeView_min, 0)
                val max = getInt(R.styleable.BooyahRangeView_max, 0)

                this@BooyahRangeView.text = context.getString(R.string.range, min, max)
            } finally {
                recycle() // Required for next operations with TypedArray to run as expected
            }
        }

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
    }
}