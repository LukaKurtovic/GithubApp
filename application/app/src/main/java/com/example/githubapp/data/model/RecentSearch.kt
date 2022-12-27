package com.example.githubapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentSearch(
    @PrimaryKey(autoGenerate = false) val searchQuery: String
) {
    override fun toString(): String {
        return searchQuery
    }
}