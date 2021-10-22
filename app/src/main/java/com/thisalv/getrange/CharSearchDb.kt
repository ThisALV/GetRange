package com.thisalv.getrange

import com.thisalv.booyah.CharDetails

/**
 * Establishes a searching database with given SSBU characters list and option aliases (secondary
 * name) list for each char, then allows to search for a name within primary and secondary names to
 * retrieve to matching `CharDetails` entries.
 *
 * Please note that `_` symbol on ID or aliases act as a word separator and will therefore cause
 * multiple keywords to be added.
 *
 * @constructor For every given char entry, use its `id` and the elements inside the associated
 * `aliases` entry to build keywords list providing access to that Booyah char entry
 *
 * @param charsDb List of chars read from Db that can be searched for
 * @param aliases For some chars `id`, a list of keywords that can also be used to find the char entry
 * using the key which is an `id` property of a char entry
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class CharSearchDb constructor(charsDb: Array<CharDetails>, aliases: Map<String, Array<String>>) {
    // List of char entries, where first pair elem is keywords used to reach to second pair elem
    // which is the associated Booyah entry
    private val entries: MutableList<Pair<MutableList<String>, CharDetails>> = mutableListOf()

    init {
        for (charEntry in charsDb) {
            // Current char ID contains keywords for its entry
            val keywords = charEntry.id.split('_') as MutableList<String>

            val charAliases = aliases[charEntry.id] // Aliases for this char entry
            if (charAliases != null) {// If aliases are there, add theme as keywords
                keywords.addAll(charAliases.map { alias: String ->
                    alias.split('_')
                }.flatten())
            }

            entries.add(Pair(keywords, charEntry)) // Push entry into the db so it can be searched
        }
    }

    /**
     * Searches for every `CharDetails` entry with at least one keyword starting with this string.
     *
     * Please note case is ignored.
     *
     * @param searchedName Beginning keywords must match with for a char entry to be retrieved
     *
     * @return Every char entry which has at least one keyword matching
     */
    fun startsWith(searchedName: String): List<CharDetails> {
        val matches = mutableListOf<CharDetails>()

        for (searchEntry in entries) { // Inside each entry of the database...
            var matching = false

            val keywordIt = searchEntry.first.iterator()
            // ...test for every keyword until we're sure this char entry is matching
            while (!matching && keywordIt.hasNext())
                matching = keywordIt.next().startsWith(searchedName, true)

            if (matching) // If it matches, add it to the research results
                matches.add(searchEntry.second)
        }

        return matches
    }
}
