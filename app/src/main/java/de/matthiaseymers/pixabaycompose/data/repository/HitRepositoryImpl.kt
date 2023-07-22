package de.matthiaseymers.pixabaycompose.data.repository

import de.matthiaseymers.pixabaycompose.data.local.HitDao
import de.matthiaseymers.pixabaycompose.data.remote.PixabayApi
import de.matthiaseymers.pixabaycompose.domain.model.SearchHitEvent
import de.matthiaseymers.pixabaycompose.domain.repository.HitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HitRepositoryImpl(
    private val api: PixabayApi,
    private val dao: HitDao
) : HitRepository {

    override fun searchHitsRemote(query: String): Flow<SearchHitEvent> = flow {
        emit(SearchHitEvent.Loading)

        val remoteSearchHit = api.searchHitsAsync(query = query).toSearchHit()
        val hits = remoteSearchHit.hits

        dao.deleteHits(dao.getHits())
        dao.insertHits(hits.map { it.toHitEntity() })

        emit(SearchHitEvent.Success(hits = hits))
    }.catch { e ->
        emit(SearchHitEvent.Error(cause = e))
    }.flowOn(Dispatchers.IO)

    override fun getAllHitsLocal(): Flow<SearchHitEvent> = flow {
        emit(SearchHitEvent.Loading)

        val localHits = dao.getHits().map { it.toHit() }
        emit(SearchHitEvent.Success(hits = localHits))
    }.catch { e ->
        emit(SearchHitEvent.Error(cause = e))
    }.flowOn(Dispatchers.IO)
}