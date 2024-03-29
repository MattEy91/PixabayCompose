package de.matthiaseymers.pixabaycompose.data.local

import androidx.room.*
import de.matthiaseymers.pixabaycompose.data.local.entity.HitEntity

@Dao
interface HitDao {

    @Query("SELECT * FROM hitentity")
    fun getHits(): List<HitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHits(hits: List<HitEntity>)

    @Delete
    fun deleteHits(hits: List<HitEntity>)
}