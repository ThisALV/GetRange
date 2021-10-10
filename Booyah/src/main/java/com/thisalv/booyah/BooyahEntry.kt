package com.thisalv.booyah

import java.security.InvalidParameterException

/**
 * Describes a character escape option to Booyah confirm.
 *
 * @property fromPercentage Minimum % for escape `move` to be effective.
 * @property move Not always known, but designate the move performed to escape the Booyah confirm
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class Escape(val fromPercentage: Int, val move: String? = null) {
    /**
     * @param rageLvl Inkling rage effect, will subtract % modificator to escape move required
     * percentage, as rage only increases knockback
     *
     * @return An new escape with min % adjusted for current Inkling rage effect
     */
    fun withRage(rageLvl: RageEffect) =
        Escape(fromPercentage - rageLvl.percentageModificator, move)
}

/**
 * Contains ranges percentages for a specific Booyah character and calculates min/max percentages
 * variations depending on current stage and rage effect. Also contains escape percentage, if
 * it exists for this entry.
 *
 * @property firstRange Mandatory Booyah range which exists on each character
 * @property secondRange Optional Booyah range which exists if Booyah victim starts having too much
 * knockback after the `firstRange` but then is catchable again
 * @property escape If defined, an escape option to the Booyah confirm
 *
 * @constructor Builds new immutable range object directly from previous Booyah ranges, used
 * internally to create copies easier for methods which return new instance object.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class BooyahEntry private constructor(
    firstRange: Range, secondRange: Range? = null,
    val escape: Escape? = null
) {
    var firstRange: Range = firstRange
        private set

    var secondRange: Range? = secondRange
        private set

    /**
     * Builds range with given `min`, `max` percentages and with optional `escape` option.
     *
     * @throws InvalidParameterException if `0 <= min <= max` isn't respected
     */
    constructor(min: Int, max: Int, escape: Escape? = null):
            this(Range(min, max), null, escape) // Range ctor check min and max validity

    /**
     * Builds range with given `min` and `max` percentages, and builds a second range with
     * `secondMin` and `secondMax`, and with an option `escape` option.
     *
     * @throws InvalidParameterException if `0 <= min <= max < secondMin <= secondMax` isn't
     * respected
     */
    constructor(
        min: Int, max: Int, secondMin: Int, secondMax: Int, escape: Escape? = null
    ) : this(Range(min, max), Range(secondMin, secondMax), escape) {
        // min/max and secondMin/secondMax validity checks are delegated to the Range ctor
        // Here we only need to check for consistency between the two ranges border
        if (secondMin <= max)
            throw InvalidParameterException("secondMin must be greater than max")
    }

    /**
     * Applies `withRage()` for both ranges and escape min % if any then retrieves new instance with
     * updated ones.
     */
    fun withRage(rageLvl: RageEffect) = BooyahEntry(
        firstRange.withRage(rageLvl), secondRange?.withRage(rageLvl), escape?.withRage(rageLvl)
    )

    /**
     * @param minPercentageMalus +/- required percentage for the Booyah to kill on the top blastzone,
     * applies only to the 1st range min % value
     *
     * @return A new Booyah range for this context
     */
    fun withStage(minPercentageMalus: Int) =
        BooyahEntry(Range(firstRange.min + minPercentageMalus, firstRange.max), secondRange, escape)
}
