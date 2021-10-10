package com.thisalv.booyah

import org.junit.Assert.*
import org.junit.Test
import java.security.InvalidParameterException

/**
 * Unit tests for `Range` class methods
 */
class RangeTest {
    @Test
    fun constructor() {
        // Doesn't respect min >= 0
        assertThrows(InvalidParameterException::class.java) { Range(-1, 0) }
        // Doesn't respect max >= min
        assertThrows(InvalidParameterException::class.java) { Range(1, 0) }

        // Does respect 0 <= min <= max
        val testingRange1 = Range(70, 70)
        val testingRange2 = Range(0, 150)
        val testingRange3 = Range(70, 150)

        assertEquals(70, testingRange1.min)
        assertEquals(70, testingRange1.max)

        assertEquals(0, testingRange2.min)
        assertEquals(150, testingRange2.max)

        assertEquals(70, testingRange3.min)
        assertEquals(150, testingRange3.max)
    }

    @Test
    fun withRage() {
        val testingRange = Range(120, 150)

        val noRage = testingRange.withRage(RageEffect.PERCENTS_0)
        val lowRage = testingRange.withRage(RageEffect.PERCENTS_50)
        val midRage = testingRange.withRage(RageEffect.PERCENTS_100)
        val highRage = testingRange.withRage(RageEffect.PERCENTS_150)

        // 0 % -> expects -0 modifies
        assertEquals(120, noRage.min)
        assertEquals(150, noRage.max)

        // 50 % -> expects -3 modifies
        assertEquals(117, lowRage.min)
        assertEquals(147, lowRage.max)

        // 100 % -> expects -12 modifies
        assertEquals(108, midRage.min)
        assertEquals(138, midRage.max)

        // 150 % -> expects -22 modifies
        assertEquals(98, highRage.min)
        assertEquals(128, highRage.max)
    }
}