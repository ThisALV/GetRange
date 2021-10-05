package com.thisalv.booyah

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for Escape class
 */
class EscapeTest {
    @Test
    fun withRage() {
        val testingEscape1 = Escape(100)
        val testingEscape2 = Escape(90, "Up B")

        val noRageEscape1 = testingEscape1.withRage(RageEffect.PERCENTS_0)
        val lowRageEscape1 = testingEscape1.withRage(RageEffect.PERCENTS_50)
        val midRageEscape1 = testingEscape1.withRage(RageEffect.PERCENTS_100)
        val highRageEscape1 = testingEscape1.withRage(RageEffect.PERCENTS_150)

        val lowRageEscape2 = testingEscape2.withRage(RageEffect.PERCENTS_50)

        assertEquals(100, noRageEscape1.fromPercentage)
        assertNull(noRageEscape1.move)

        assertEquals(97, lowRageEscape1.fromPercentage)
        assertNull(lowRageEscape1.move)

        assertEquals(88, midRageEscape1.fromPercentage)
        assertNull(midRageEscape1.move)

        assertEquals(78, highRageEscape1.fromPercentage)
        assertNull(highRageEscape1.move)

        assertEquals(87, lowRageEscape2.fromPercentage)
        assertEquals("Up B", lowRageEscape2.move)
    }
}

/**
 * Unit tests for BooyahEntry class
 */
class BooyahEntryTest {
    @Test
    fun withRage() {
    }

    @Test
    fun withStage() {
    }

    @Test
    fun withInk() {
    }
}