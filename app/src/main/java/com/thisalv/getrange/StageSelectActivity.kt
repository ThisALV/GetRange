package com.thisalv.getrange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.thisalv.booyah.BooyahEntry
import com.thisalv.booyah.JsonStagesProvider
import com.thisalv.booyah.Range
import com.thisalv.booyah.StageCeiling
import com.thisalv.getrange.databinding.ViewStageBinding


// Prefix for every intent extra on this app
const val EXTRA_PREFIX = "com.thisalv.getrange."
// Some of the extras passed by the intent so we known about the Booyah range on the selected
// character
const val FIRST_MIN = EXTRA_PREFIX + "FIRST_MIN"
const val FIRST_MAX = EXTRA_PREFIX + "FIRST_MAX"
const val SECOND_MIN = EXTRA_PREFIX + "SECOND_MIN"
const val SECOND_MAX = EXTRA_PREFIX + "SECOND_MAX"


/**
 * Activity where user selects a stage on which he will play against a known character.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class StageSelectActivity : AppCompatActivity() {
    // Loads and retrieves stage ceiling entries from resource file
    private fun loadStagesDb(): Array<StageCeiling> {
        resources.openRawResource(R.raw.stages).use { // Stream auto-closure
            return JsonStagesProvider(it)()
        }
    }

    // For every given stage data, adds a stage view for it into the underlying LinearLayout, which
    // will contain given Booyah ranges data recalculated to fit with its top blastzone
    private fun displayStages(charBooyahData: BooyahEntry, stagesDb: Array<StageCeiling>) {
        // Obtains the LinearLayout displaying horizontally stacked each stage
        val stagesContainer = findViewById<LinearLayout>(R.id.stagesContainer)

        // And for each stage, creates the view and gives it the Booyah ranges data calculated for
        // it
        for (stage in stagesDb) {
            ViewStageBinding.inflate(layoutInflater, stagesContainer, true).apply {
                stageId = stage.id
                stageBooyahData = charBooyahData.withStage(stage.booyahPercentageMalus)
            }
        }

        // We must refresh the display after adding all these views
        stagesContainer.requestLayout()
    }

    /**
     * Loads stages ceiling from database json file, gets Booyah data for character selected by
     * the activity which started this and display each stage specific Booyah range.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage_select)
        setShowWhenLocked(true)

        // Gets Booyah ranges data for previously selected character to construct a new entry
        // Escape option will not be displayed so it isn't interesting for us now

        // First we read the mandatory 1st range
        val firstMin = intent.getIntExtra(FIRST_MIN, -1)
        val firstMax = intent.getIntExtra(FIRST_MAX, -1)
        // Then we tries to read the optional 2nd range
        val secondMin: Int = intent.getIntExtra(SECOND_MIN, -1)
        val secondMax: Int = intent.getIntExtra(SECOND_MAX, -1)

        // We use our intent extras to interpret if there is a second range and construct to
        // booyah ranges which will later be recalculated for each stage
        val charBooyahData: BooyahEntry
        val hasSecondRange = secondMin == -1 || secondMax == -1
        if (hasSecondRange)
            charBooyahData = BooyahEntry(firstMin, firstMax, secondMin, secondMax)
        else
            charBooyahData = BooyahEntry(firstMin, firstMax)

        // Gets stages database from the resource file and display booyah for each of them
        displayStages(charBooyahData, loadStagesDb())
    }
}