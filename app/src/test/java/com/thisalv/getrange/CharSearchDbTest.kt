package com.thisalv.getrange

import com.thisalv.booyah.BooyahEntry
import com.thisalv.booyah.CharDetails
import org.junit.Assert.*
import org.junit.Test

class CharSearchDbTest {
    @Test
    fun startsWith() {
        // Test purpose set of characters
        val mario = CharDetails("mario", BooyahEntry(0, 0), false)
        val shizue = CharDetails("shizue", BooyahEntry(0, 0), false)
        val packun_flower = CharDetails(
            "packun_flower", BooyahEntry(0, 0), false
        )
        val pacman = CharDetails("pacman", BooyahEntry(0, 0), false)

        // Respectively has no aliases, one aliases and many aliases with _ separator inside them,
        // then no aliases again for pacman
        val aliases = mapOf(
            Pair("shizue", arrayOf("isabelle")),
            Pair("packun_flower", arrayOf("piranha_plant", "plante_pirahna", "random_testing"))
        )

        val searchDb = CharSearchDb(arrayOf(mario, shizue, packun_flower, pacman), aliases)

        assertArrayEquals(
            arrayOf(mario, shizue, packun_flower, pacman), searchDb.startsWith("").toTypedArray()
        )

        assertArrayEquals(emptyArray(), searchDb.startsWith(" ").toTypedArray())

        assertArrayEquals(arrayOf(packun_flower, pacman), searchDb.startsWith("pac").toTypedArray())

        assertArrayEquals(arrayOf(shizue), searchDb.startsWith("i").toTypedArray())

        assertArrayEquals(arrayOf(packun_flower), searchDb.startsWith("pir").toTypedArray())
        assertArrayEquals(arrayOf(packun_flower), searchDb.startsWith("testing").toTypedArray())
    }
}