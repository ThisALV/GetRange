package com.thisalv.booyah

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
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
 * @property working `true` if Booyah could connect and kill on this range, that is, if `min <= max`
 *
 * @constructor Builds range with given min and max percentages
 * @throws InvalidParameterException if either `min` or `max` is negative
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
@Serializable
class Range(val min: Int, val max: Int) {
    val working: Boolean
        get() = min <= max

    init {
        if (min < 0 || max < 0)
            throw InvalidParameterException("min and max must be positives")
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
