package com.raphau.licho.repository

import android.content.Context
import android.provider.ContactsContract
import com.raphau.licho.data.Contact
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val context: Context) {

    private val PEOPLE_URI = ContactsContract.Data.CONTENT_URI

     fun getContact(id: Int): Contact? {
        if (id < 1) return null
        val cursor = context.contentResolver.query(PEOPLE_URI,
            null,
            "raw_contact_id like $id",
            null,
            null)
        val contact = if (cursor != null && cursor.moveToFirst()) {
            Contact(id,
                cursor.getString(cursor.getColumnIndex("display_name")),
                cursor.getString(cursor.getColumnIndex("photo_thumb_uri")))
        } else null
        cursor?.close()
        return contact
    }
}