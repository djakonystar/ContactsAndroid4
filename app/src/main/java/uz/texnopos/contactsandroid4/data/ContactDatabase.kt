package uz.texnopos.contactsandroid4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    companion object {
        private var instance: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase {
            instance?.let {
                return it
            }

            val db = Room.databaseBuilder(context, ContactDatabase::class.java, "contacts.db")
                .allowMainThreadQueries()
                .build()

            instance = db
            return db
        }
    }

    abstract fun getContactDao(): ContactDao
}
