package com.thisalv.booyah

import java.security.InvalidParameterException

/**
 * Designates each rage effect known % associated with its % modificator.
 *
 * @property percentageModificator % to subtract to every min/max/escape % on a `BooyahEntry`
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
enum class RageEffect(val percentageModificator: Int) {
    PERCENTS_0(0),
    PERCENTS_50(3),
    PERCENTS_100(12),
    PERCENTS_150(22)
}

/**
 * Contains valid min/max percentages for a specific Booyah range. `BooyahEntry` might contain two
 * of them.
 *
 * @property min Minimum % for Booyah to connect and kill
 * @property max Maximum % for Booyah to connect
 *
 * @constructor Builds valid range with given min and max percentages
 * @throws InvalidParameterException if `0 <= min <= max` isn't respected
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class Range(min: Int, max: Int) {
    var min: Int = min
        private set

    var max: Int = max
        private set

    init {
        if (min < 0)
            throw InvalidParameterException("min must be positive")
        if (max < min)
            throw InvalidParameterException("max cannot be less than min")
    }

    /**
     * Rage affects knockback so confirm will kill sooner, but it doesn't affect hitstun so it will
     * also stop to connect earlier.
     *
     * @param rage Rage % the returned range will be suited for
     *
     * @return A new Booyah range for this context
     */
    fun withRage(rage: RageEffect) =
        Range(min - rage.percentageModificator, max - rage.percentageModificator)
}
