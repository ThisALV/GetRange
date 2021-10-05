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
data class Escape(val fromPercentage: Int, val move: String? = null)

/**
 * Contains ranges percentages for a specific Booyah character and calculates min/max percentages
 * variations depending on current stage, rage effect and ink %. Also contains escape percentage, if
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
            this(Range(min, max), null, escape) {}

    /**
     * Builds range with given `min` and `max` percentages, and builds a second range with
     * `secondMin` and `secondMax`, and with an option `escape` option.
     *
     * @throws InvalidParameterException if `0 <= min <= max < secondMin <= secondMax` isn't
     * respected
     */
    constructor(min: Int, max: Int, secondMin: Int, secondMax: Int, escape: Escape?) :
            this(Range(min, max), Range(secondMin, secondMax), escape) {}

    /**
     * Applies `withRage()` for both ranges and escape min % if any then retrieves new instance with
     * updated ones.
     */
    fun withRage(rageLvl: RageEffect): BooyahEntry {
        throw NotImplementedError()
    }

    /**
     * @param percentsBonus +/- required percentage for the Booyah to kill on the top blastzone,
     * applies only to the 1st range min % value
     *
     * @return A new Booyah range for this context
     */
    fun withStage(percentsBonus: Int): Range {
        throw NotImplementedError()
    }

    /**
     * Applies `withRage()` for both ranges and escape min % if any then retrieves new instance with
     * updated ones.
     */
    fun withInk(inkCoverage: InkRate): BooyahEntry {
        throw NotImplementedError()
    }
}
