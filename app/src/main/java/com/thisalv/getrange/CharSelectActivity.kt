package com.thisalv.getrange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import com.thisalv.booyah.CharDetails
import com.thisalv.booyah.JsonCharsProvider
import com.thisalv.getrange.databinding.ViewCharEntryBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Exception

/**
 * Launcher app activity, where user can select a character within the loaded ones by searching a
 * character using its name.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class CharSelectActivity : AppCompatActivity() {
    // List of loaded characters, empty until activity creation on stack
    private lateinit var database: CharSearchDb

    // Loads and retrieves list character entries from resource file
    private fun loadCharactersDb(): Array<CharDetails> {
        resources.openRawResource(R.raw.characters).use { // Stream auto-closure
            return JsonCharsProvider(it)()
        }
    }

    // Loads and retrieves dictionary of optional aliases on each character who has any
    private fun loadAliases(): Map<String, Array<String>> {
        resources.openRawResource(R.raw.aliases).use { // Stream auto-closure
            // Get reader from stream, then read text with it and decode it using the method return
            // type to infer decodeFromString() return type
            return Json.decodeFromString(it.bufferedReader().readText())
        }
    }

    // Resets LinearLayout holding the previous result and inflates view for the new ones
    private fun updateResults(newResults: Array<CharDetails>) {
        // Provides the ViewGroup holding the characters views
        val charsContainer = findViewById<LinearLayout>(R.id.charsSelectRoot)
        // Resets previous results
        charsContainer.removeAllViews()

        for (charEntry in newResults) { // For each new character inside result, displays it
            ViewCharEntryBinding.inflate(layoutInflater, charsContainer, true).apply {
                this.charEntry = charEntry
            }
        }

        // Layout must be calculated again, and view must be redrawn
        charsContainer.requestLayout()
        charsContainer.invalidate()
    }

    // Handles when nameSearchInput text is modified by user to generated a new characters search
    private inner class NameSearchListener : TextWatcher {
        // Uses the text is the given Editable to make a new search and update activity with this
        // search results
        override fun afterTextChanged(s: Editable?) {
            this@CharSelectActivity.apply {
                // Avoid handling case where no string is provided for whatever reason, also avoid
                // case where search is empty because all characters would fit and it would take a
                // long time to process
                if (s.isNullOrEmpty())
                    return

                // We get the text on the search field and use it to make a new name search, then
                // we update view with these new results
                updateResults(database.startsWith(s.toString()).toTypedArray())
            }
        }

        // Cases not handled
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    }

    /**
     * Loads characters Booyah entries from local `characters.json` ressource and add a
     * corresponding UI element for each one of them.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_char_select)
        setShowWhenLocked(true)

        val charsDb = loadCharactersDb() // Chars are required, errors are fatal

        var aliases = emptyMap<String, Array<String>>()
        try { // Aliases aren't required, so errors should only be logged and app must continue
            aliases = loadAliases()
        } catch (e: Exception) {
            System.err.println("${e.javaClass.canonicalName}: ${e.message}")
        }

        // TODO Avoid reloading at each onCreate() using Bundle arg
        database = CharSearchDb(charsDb, aliases) // Reset db with new data

        // Watches for any change on name search input field, so we can make the search requested
        // by the user
        findViewById<EditText>(R.id.nameSearchInput).addTextChangedListener(NameSearchListener())
    }
}