package com.thisalv.booyah

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.security.InvalidParameterException

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
    fun constructorSingleRange() {
        val booyah1 = BooyahEntry(130, 150)
        val booyah2 = BooyahEntry(130, 150, Escape(140))
        val booyah3 = BooyahEntry(150, 130)

        assertThrows(InvalidParameterException::class.java) { BooyahEntry(-1, 150) }

        assertEquals(booyah1.firstRange.min, 130)
        assertEquals(booyah1.firstRange.max, 150)
        assertNull(booyah1.secondRange)
        assertNull(booyah1.escape)

        assertEquals(booyah2.firstRange.min, 130)
        assertEquals(booyah2.firstRange.max, 150)
        assertNull(booyah2.secondRange)
        assertEquals(booyah2.escape?.fromPercentage, 140)
        assertNull(booyah2.escape?.move)

        assertEquals(booyah3.firstRange.min, 150)
        assertEquals(booyah3.firstRange.max, 130)
        assertNull(booyah3.secondRange)
        assertNull(booyah3.escape)
    }

    @Test
    fun constructorDoubleRange() {
        val booyah1 = BooyahEntry(130, 150, 160, 170)
        val booyah2 = BooyahEntry(
            130, 150, 160, 170, Escape(140)
        )
        val booyah3 = BooyahEntry(130, 120, 110, 100)

        assertThrows(InvalidParameterException::class.java) { BooyahEntry(-1, 150) }

        assertEquals(booyah1.firstRange.min, 130)
        assertEquals(booyah1.firstRange.max, 150)
        assertEquals(booyah1.secondRange?.min, 160)
        assertEquals(booyah1.secondRange?.max, 170)
        assertNull(booyah1.escape)

        assertEquals(booyah2.firstRange.min, 130)
        assertEquals(booyah2.firstRange.max, 150)
        assertEquals(booyah2.secondRange?.min, 160)
        assertEquals(booyah2.secondRange?.max, 170)
        assertEquals(booyah2.escape?.fromPercentage, 140)
        assertNull(booyah2.escape?.move)

        // Here, no range can make Booyah works and 2nd range is prior to the 1st one, but we should
        // be able to handle this case
        assertEquals(booyah3.firstRange.min, 130)
        assertEquals(booyah3.firstRange.max, 120)
        assertEquals(booyah3.secondRange?.min, 110)
        assertEquals(booyah3.secondRange?.max, 100)
        assertNull(booyah3.escape)
    }

    @Test
    fun withRage() {
        // No optional params 2nd range or escape
        val booyah1 = BooyahEntry(100, 150)
        // Optional params which will be affected by rage effect as well
        val booyah2 = BooyahEntry(
            100, 150, 160, 170, Escape(140)
        )

        val midRageBooyah1 = booyah1.withRage(RageEffect.PERCENTS_100)
        
        val noRageBooyah2 = booyah2.withRage(RageEffect.PERCENTS_0)
        val highRageBooyah2 = booyah2.withRage(RageEffect.PERCENTS_150)
        
        assertEquals(midRageBooyah1.firstRange.min, 88)
        assertEquals(midRageBooyah1.firstRange.max, 138)
        assertNull(midRageBooyah1.secondRange)
        assertNull(midRageBooyah1.escape)
        
        assertEquals(noRageBooyah2.firstRange.min, 100)
        assertEquals(noRageBooyah2.firstRange.max, 150)
        assertEquals(noRageBooyah2.secondRange?.min, 160)
        assertEquals(noRageBooyah2.secondRange?.max, 170)
        assertEquals(noRageBooyah2.escape?.fromPercentage, 140)

        assertEquals(highRageBooyah2.firstRange.min, 78)
        assertEquals(highRageBooyah2.firstRange.max, 128)
        assertEquals(highRageBooyah2.secondRange?.min, 138)
        assertEquals(highRageBooyah2.secondRange?.max, 148)
        assertEquals(highRageBooyah2.escape?.fromPercentage, 118)
    }

    @Test
    fun withStage() {
        // No optional params 2nd range or escape
        val booyah1 = BooyahEntry(100, 150)
        // Optional params which will be affected by rage effect as well
        val booyah2 = BooyahEntry(
            100, 150, 160, 170, Escape(140)
        )

        val tallStageBooyah1 = booyah1.withStage(5)
        val smallStageBooyah2 = booyah2.withStage(-5)  // Negative malus is a bonus

        assertEquals(tallStageBooyah1.firstRange.min, 105)
        assertEquals(tallStageBooyah1.firstRange.max, 150)
        assertNull(tallStageBooyah1.secondRange)
        assertNull(tallStageBooyah1.escape)

        // Only the very 1st Booyah limit: the 1st range min, should be affected by stage height,
        // because Uair will not require hitting too high for killing the opponent
        assertEquals(smallStageBooyah2.firstRange.min, 95)
        assertEquals(smallStageBooyah2.firstRange.max, 150)
        assertEquals(smallStageBooyah2.secondRange?.min, 160)
        assertEquals(smallStageBooyah2.secondRange?.max, 170)
        assertEquals(smallStageBooyah2.escape?.fromPercentage, 140)
    }
}