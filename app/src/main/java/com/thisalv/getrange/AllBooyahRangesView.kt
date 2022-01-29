package com.thisalv.getrange

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.thisalv.getrange.databinding.ViewAllBooyahRangesBinding

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
): ConstraintLayout(context, attrs) {
    private var firstMin = 0
    private var firstMax = 0

    // second* properties must be initialized for hasSecondRange getter to work
    private var secondMin = -1
    private var secondMax = -1
    private var hasSecondRange: Boolean = secondMin != -1 || secondMax != -1

    // Accesses properties to refresh booyah ranges values on underlying BooyahRangeView elements
    private val binding: ViewAllBooyahRangesBinding

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.AllBooyahRangesView, 0, 0
        ).apply {layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            try { // getInt() might fail if this TypedArray wasn't recycled by previous operation
                firstMin = getInt(R.styleable.AllBooyahRangesView_firstMin, 0)
                firstMax = getInt(R.styleable.AllBooyahRangesView_firstMax, 0)

                // Missing 2nd range value will lead to -1 value which is disabling this range
                secondMin = getInt(R.styleable.AllBooyahRangesView_secondMin, -1)
                secondMax = getInt(R.styleable.AllBooyahRangesView_secondMax, -1)

                // Creates and display the child LinearLayout containing the 2 ranges textviews
                binding = ViewAllBooyahRangesBinding.inflate(
                    LayoutInflater.from(context), this@AllBooyahRangesView, true
                ).apply {
                    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                }

                update()
            } finally {
                recycle()
            }
        }
    }

    private fun update() {
        binding.apply {
            firstMin = this@AllBooyahRangesView.firstMin
            firstMax = this@AllBooyahRangesView.firstMax

            secondMin = this@AllBooyahRangesView.secondMin
            secondMax = this@AllBooyahRangesView.secondMax

            hasSecondRange = this@AllBooyahRangesView.hasSecondRange
        }

        // Layout must be calculated again, and view must be redrawn
        requestLayout()
    }

    /**
     * Updates `firstMin`.
     */
    fun setFirstMin(firstMin: Int) {
        this.firstMin = firstMin
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
        this.secondMin = secondMin
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