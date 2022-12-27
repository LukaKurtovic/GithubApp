package com.example.githubapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubapp.data.model.RecentSearch
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentSearch: RecentSearch)

    @Query("DELETE FROM RecentSearch")
    suspend fun deleteSearchHistory()

    @Query("SELECT * FROM RecentSearch WHERE searchQuery LIKE '%' || :query || '%'")
    fun getSuggestions(query: String): Flow<List<RecentSearch>>
}