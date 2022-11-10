package de.matthiaseymers.pixabaycompose.domain.model

sealed class SearchHitEvent {
    object Loading: SearchHitEvent()
    data class Success(val hits: List<Hit>): SearchHitEvent()
    data class Error(val cause: Throwable): SearchHitEvent()
}
