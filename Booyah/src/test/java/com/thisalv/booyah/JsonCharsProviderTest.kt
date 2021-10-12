package com.thisalv.booyah

import kotlinx.serialization.SerializationException
import org.junit.Assert.*
import org.junit.Test

class JsonCharsProviderTest {
    @Test
    operator fun invoke() {
        val provider1 = JsonCharsProvider("".trimIndent().byteInputStream())

        val provider2 = JsonCharsProvider("""
            [
                {
                    "id": "joker",
                    "booyahData": {},
                    "canUsmashBurried": 0
                }
            ]
        """.trimIndent().byteInputStream())

        val provider3 = JsonCharsProvider("""
            [
                {
                    "id": "joker",
                    "booyahData": {
                        "firstRange": {
                            "min": 110,
                            "max": 130
                        },
                        "escape": {
                            "fromPercentage": 125,
                            "move": "Down B (counter)"
                        }
                    },
                    "canUsmashBurried": true
                },
                {
                    "id": "cpt_falcon",
                    "booyahData": {
                        "firstRange": {
                            "min": 120,
                            "max": 135
                        },
                        "secondRange": {
                            "min": 136,
                            "max": 150
                        }
                    },
                    "canUsmashBurried": false
                }
            ]
        """.trimIndent().byteInputStream())

        assertThrows(SerializationException::class.java) { provider1() }
        assertThrows(SerializationException::class.java) { provider2() }

        // Cannot use assertArrayEquals() because of CharDetails having class instances properties

        val charsDb = provider3()
        assertEquals(2, charsDb.size)

        val firstChar = charsDb[0]
        assertEquals("joker", firstChar.id)
        assertEquals(110, firstChar.booyahData.firstRange.min)
        assertEquals(130, firstChar.booyahData.firstRange.max)
        assertNull(firstChar.booyahData.secondRange)
        assertEquals(125, firstChar.booyahData.escape?.fromPercentage)
        assertEquals("Down B (counter)", firstChar.booyahData.escape?.move)
        assert(firstChar.canUsmashBurried)
        
        val secondChar = charsDb[1]
        assertEquals("cpt_falcon", secondChar.id)
        assertEquals(120, secondChar.booyahData.firstRange.min)
        assertEquals(135, secondChar.booyahData.firstRange.max)
        assertEquals(136, secondChar.booyahData.secondRange?.min)
        assertEquals(150, secondChar.booyahData.secondRange?.max)
        assertNull(secondChar.booyahData.escape)
        assertFalse(secondChar.canUsmashBurried)
    }
}