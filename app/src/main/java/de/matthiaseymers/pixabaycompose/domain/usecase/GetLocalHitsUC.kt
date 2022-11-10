package de.matthiaseymers.pixabaycompose.domain.usecase

import de.matthiaseymers.pixabaycompose.domain.repository.HitRepository

class GetLocalHitsUC(
    private val repo: HitRepository
) {
    operator fun invoke() = repo.getAllHitsLocal()
}