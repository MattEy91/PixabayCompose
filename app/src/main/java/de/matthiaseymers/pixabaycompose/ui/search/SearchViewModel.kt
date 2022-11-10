package de.matthiaseymers.pixabaycompose.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.matthiaseymers.pixabaycompose.core.Constants.INITIAL_SEARCH_QUERY
import de.matthiaseymers.pixabaycompose.domain.model.SearchHitEvent
import de.matthiaseymers.pixabaycompose.domain.usecase.GetLocalHitsUC
import de.matthiaseymers.pixabaycompose.domain.usecase.SearchHitsUC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHitUc: SearchHitsUC,
    private val getLocalHitsUC: GetLocalHitsUC
) : ViewModel() {

    private val _searchHitEvent = MutableSharedFlow<SearchHitEvent>()
    val searchHitEvent = _searchHitEvent.shareIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed()
    )

    fun searchHits(query: String, isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isOnline) {
                searchHitUc.invoke(query)
            } else {
                getLocalHitsUC.invoke()
            }.collectLatest {
                _searchHitEvent.emit(it)
            }
        }
    }
}