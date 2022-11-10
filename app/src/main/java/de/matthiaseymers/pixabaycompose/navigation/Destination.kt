package de.matthiaseymers.pixabaycompose.navigation

import de.matthiaseymers.pixabaycompose.core.Constants.DESTINATION_DETAIL
import de.matthiaseymers.pixabaycompose.core.Constants.DESTINATION_SEARCH
import de.matthiaseymers.pixabaycompose.domain.model.Hit

sealed class Destination(val route: String) {
    object Search: Destination(DESTINATION_SEARCH)
    object Detail: Destination(DESTINATION_DETAIL)
}
