package com.thisalv.booyah

import java.io.InputStream

/**
 * Contains every data required to describe an Inkling opponent character details useful for the MU.
 *
 * @param id Used to identify which character is detailed here
 * @param booyahData Details about Booyah kill range for this opponent
 * @param canUsmashBurried If `true` the Usmash pop up this opponent from ground after roller and
 * therefore can kill when side B hits, otherwise, the opponent will stay burried and will be hit
 * by the sourspot
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
data class CharDetails(val id: String, val booyahData: BooyahEntry, val canUsmashBurried: Boolean)

/**
 * Classes that provide data for SSBU characters details should implements this interface and its
 * call operator (`invoke` method).
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
abstract class CharsDetailsProvider {
    /**
     * @return An array of Inkling MU details (Booyah and burried Usmash viability) for each SSBU
     * character
     */
    abstract fun invoke(): Array<CharDetails>
}

/**
 * Reads SSBU characters details list from an `InputStream` of JSON text having an array as its root
 * value.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class JsonCharsProvider(val jsonSource: InputStream) : CharsDetailsProvider() {
    override fun invoke(): Array<CharDetails> {
        throw NotImplementedError()
    }
}
