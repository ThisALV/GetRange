package com.thisalv.getrange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Activity where the user selects a stage on which he will play agaist a known character.
 *
 * @author ThisALV, https://github.com/ThisALV/
 */
class StageSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage_select)
    }
}