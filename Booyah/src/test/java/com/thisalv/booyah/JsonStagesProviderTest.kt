package com.thisalv.booyah

import kotlinx.serialization.SerializationException
import org.junit.Assert.*
import org.junit.Test

class JsonStagesProviderTest {
    @Test
    operator fun invoke() {
        val provider1 = JsonStagesProvider("".trimIndent().byteInputStream())

        val provider2 = JsonStagesProvider("""
            [
                { "id": "bf" }
            ]
        """.trimIndent().byteInputStream())

        val provider3 = JsonStagesProvider("""
            [
                { "id": 0, "booyahPercentageMalus": 5 }
            ]
        """.trimIndent().byteInputStream())

        val provider4 = JsonStagesProvider("""
            [
                { "id": "fd", "booyahPercentageMalus": 5 },
                { "id": 0 }
            ]
        """.trimIndent().byteInputStream())

        val provider5 = JsonStagesProvider("""
            [
                { "id": "sv", "booyahPercentageMalus": 5 }
            ]
        """.trimIndent().byteInputStream())

        val provider6 = JsonStagesProvider("""
            [
                { "id": "sbf", "booyahPercentageMalus": 5  },
                { "id": "sv",  "booyahPercentageMalus": -3 },
                { "id": "fd",  "booyahPercentageMalus": 0  }
            ]
        """.trimIndent().byteInputStream())

        assertThrows(SerializationException::class.java) { provider1() }
        assertThrows(SerializationException::class.java) { provider2() }
        assertThrows(SerializationException::class.java) { provider3() }
        assertThrows(SerializationException::class.java) { provider4() }

        assertArrayEquals(
            arrayOf(StageCeiling("sv", 5)),
            provider5()
        )

        assertArrayEquals(
            arrayOf(
                StageCeiling("sbf", 5),
                StageCeiling("sv", -3),
                StageCeiling("fd", 0)
            ),
            provider6()
        )
    }
}
