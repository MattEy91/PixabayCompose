package de.matthiaseymers.pixabaycompose.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.matthiaseymers.pixabaycompose.domain.model.SearchHitEvent
import de.matthiaseymers.pixabaycompose.domain.usecase.GetLocalHitsUC
import de.matthiaseymers.pixabaycompose.domain.usecase.SearchHitsUC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHitUC: SearchHitsUC,
    private val getLocalHitsUC: GetLocalHitsUC
) : ViewModel() {

    private val _searchHitEvent = MutableStateFlow<SearchHitEvent>(SearchHitEvent.Loading)
    val searchHitEvent = _searchHitEvent.asStateFlow()

    fun searchHits(query: String, isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isOnline) {
                searchHitUC.invoke(query)
            } else {
                getLocalHitsUC.invoke()
            }.collectLatest {
                _searchHitEvent.emit(it)
            }
        }
    }
}