package com.thisalv.booyah

import java.security.InvalidParameterException

/**
 * Designates each rage effect level associated with its % for which we know the Booyah range
 * modificator.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
enum class RageEffect(val percents: Int) {
    NONE(0),
    LOW(50),
    AVERAGE(100),
    HIGH(150)
}

/**
 * Designates Inkling opponent ink-covered rate. Opponent is ink-covered by Inkling's special moves.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
enum class InkRate {
    NONE,
    HALF,
    FULL
}

/**
 * Contains valid min/max percentages for a specific Booyah range. `BooyahEntry` might contain two
 * of them.
 *
 * @property min Minimum % for Booyah to connect and kill
 * @property max Maximum % for Booyah to connect
 *
 * @constructor Builds valid range with given min and max percentages
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class Range {
    var min: Int = 0
        private set

    var max: Int = 0
        private set

    /**
     * @throws InvalidParameterException if `0 <= min <= max` isn't respected
     */
    constructor(min: Int, max: Int) {}

    /**
     * Rage affects knockback so confirm will kill sooner, but it doesn't affect hitstun so it will
     * also stop to connect earlier.
     *
     * @param rageLvl Rage Lvl the returned range will be suited for
     *
     * @return A new Booyah range for this context
     */
    fun withRage(rageLvl: RageEffect): Range {
        throw NotImplementedError()
    }

    /**
     * @param percentsBonus +/- required percentage for the Booyah to kill on the top blastzone
     *
     * @return A new Booyah range for this context
     */
    fun withStage(percentsBonus: Int): Range {
        throw NotImplementedError()
    }

    /**
     * Inkling's ink affects knockback AND hitstun because Booyah is a combo (multi-hits) and ink
     * modifies damage output.
     *
     * @param inkCoverage Inkling opponent ink-covered rate the returned range will be suited for
     *
     * @return A new Booyah range for this context
     */
    fun withInk(inkCoverage: InkRate): Range {
        throw NotImplementedError()
    }
}
