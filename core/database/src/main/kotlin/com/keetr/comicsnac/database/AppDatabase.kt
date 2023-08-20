package com.keetr.comicsnac.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keetr.comicsnac.database.character.CharacterDao

@Database(
    entities = [
        CharacterDao::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
}