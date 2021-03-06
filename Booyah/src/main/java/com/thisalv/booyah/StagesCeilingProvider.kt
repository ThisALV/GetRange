package com.thisalv.booyah

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream

/**
 * @property id Used to identify the stage for which this min % bonus is applying
 * @property booyahPercentageMalus Will be added to min % of a booyah
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
@Serializable
data class StageCeiling(val id: String, val booyahPercentageMalus: Int)

/**
 * Classes that provide data for SSBU stage ceiling data should implements this interface and its
 * call operator (`invoke` method).
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
abstract class StagesCeilingProvider {
    abstract operator fun invoke(): Array<StageCeiling>
}

/**
 * Reads SSBU stages list from an `InputStream` of JSON text having an array as its root value.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class JsonStagesProvider(val jsonSource: InputStream) : StagesCeilingProvider() {
    override fun invoke(): Array<StageCeiling> =
        Json.decodeFromString(jsonSource.bufferedReader().readText())
}
