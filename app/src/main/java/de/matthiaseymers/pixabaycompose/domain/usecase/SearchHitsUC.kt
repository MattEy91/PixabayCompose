package de.matthiaseymers.pixabaycompose.domain.usecase

import de.matthiaseymers.pixabaycompose.domain.repository.HitRepository

class SearchHitsUC(
    private val repo: HitRepository
) {
    operator fun invoke(query: String) = repo.searchHitsRemote(query)
}