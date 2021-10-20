package com.thisalv.getrange

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * Uses custom attribute `app:charactedId` to search for the corresponding image resource to display.
 *
 * @param img View on which `app:characterId` is applied
 * @param charId ID of character to describe with resource loaded in this adapter
 */
@BindingAdapter("app:characterId")
fun setCharacterId(img: ImageView, charId: String) {
    img.setImageResource(
        img.resources.getIdentifier(charId, "drawable", img.context.packageName)
    )
}
