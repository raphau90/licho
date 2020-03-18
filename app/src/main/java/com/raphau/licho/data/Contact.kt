package com.raphau.licho.data

import android.net.Uri

data class Contact(val id: Int,
                   val displayName: String,
                   private val thumbnailUriString: String?) {
    val thumbnailUri by lazy {
        if (thumbnailUriString != null) {
            Uri.parse(thumbnailUriString)
        } else null
    }

}