package com.thisalv.getrange

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView

class BooyahRangeView constructor(
    context: Context, attrs: AttributeSet
): AppCompatTextView(context, attrs) {
    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.BooyahRangeView, 0, 0
        ).apply {
            try {
                val min = getInt(R.styleable.BooyahRangeView_min, 0)
                val max = getInt(R.styleable.BooyahRangeView_max, 0)

                this@BooyahRangeView.text = context.getString(R.string.range, min, max)
            } finally {
                recycle()
            }
        }

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
    }
}