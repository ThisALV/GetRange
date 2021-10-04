package com.thisalv.booyah

import java.security.InvalidParameterException

/**
 * Contains ranges percentages for a specific Booyah character and calculates min/max percentages
 * variations depending on current stage, rage effect and ink %.
 *
 * @property firstRange Mandatory Booyah range which exists on each character
 * @property secondRange Optional Booyah range which exists if Booyah victim starts having too much
 * knockback after the `firstRange` but then is catchable again
 *
 * @constructor Builds new immutable range object directly from previous Booyah ranges, used
 * internally to create copies easier for methods which return new instance object.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class BooyahEntry private constructor(firstRange: Range, secondRange: Range? = null) {
    var firstRange: Range = firstRange
        private set

    var secondRange: Range? = secondRange
        private set

    /**
     * Builds range with given `min` and `max` percentages.
     *
     * @throws InvalidParameterException if `0 <= min <= max` isn't respected
     */
    constructor(min: Int, max: Int): this(Range(min, max)) {}

    /**
     * Builds range with given `min` and `max` percentages, and builds a second range with
     * `secondMin` and `secondMax`
     *
     * @throws InvalidParameterException if `0 <= min <= max < secondMin <= secondMax` isn't
     * respected
     */
    constructor(min: Int, max: Int, secondMin: Int, secondMax: Int) :
            this(Range(min, max), Range(secondMin, secondMax)) {}

    /**
     * Applies `withRage()` for both ranges and new instance with updated ones.
     */
    fun withRage(rageLvl: RageEffect): BooyahEntry {
        throw NotImplementedError()
    }

    /**
     * Applies `withStage()` for both ranges and new instance with updated ones.
     */
    fun withStage(percentsBonus: Int): BooyahEntry {
        throw NotImplementedError()
    }

    /**
     * Applies `withInk()` for both ranges and new instance with updated ones.
     */
    fun withInk(inkCoverage: InkRate): BooyahEntry {
        throw NotImplementedError()
    }
}
