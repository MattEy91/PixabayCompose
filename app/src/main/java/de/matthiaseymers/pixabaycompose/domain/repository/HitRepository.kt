package de.matthiaseymers.pixabaycompose.domain.repository

import de.matthiaseymers.pixabaycompose.domain.model.SearchHitEvent
import kotlinx.coroutines.flow.Flow

interface HitRepository {
    fun searchHitsRemote(query: String): Flow<SearchHitEvent>
    fun getAllHitsLocal(): Flow<SearchHitEvent>
}