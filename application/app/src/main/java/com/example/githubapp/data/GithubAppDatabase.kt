package com.example.githubapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubapp.data.model.RecentSearch

@Database(entities = [RecentSearch::class], version = 4)
abstract class GithubAppDatabase : RoomDatabase() {
    abstract fun getEntryDao(): RecentSearchDao
}