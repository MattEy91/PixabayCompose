package de.matthiaseymers.pixabaycompose.data.remote.dto

import de.matthiaseymers.pixabaycompose.domain.model.SearchHit

data class SearchHitDto(
    val hits: List<HitDto>,
    val total: Int,
    val totalHits: Int
) {
    fun toSearchHit(): SearchHit {
        return SearchHit(hits = hits.map { it.toHit() })
    }
}