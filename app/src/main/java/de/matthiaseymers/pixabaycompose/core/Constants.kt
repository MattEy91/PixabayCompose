package de.matthiaseymers.pixabaycompose.core

import de.matthiaseymers.pixabaycompose.domain.model.Hit

object Constants {
    const val DESTINATION_SEARCH = "search"
    const val DESTINATION_DETAIL = "detail"

    const val EXTRA_ARGUMENT_HIT = "hit"

    const val BASE_URL = "https://pixabay.com/"
    const val INITIAL_SEARCH_QUERY = "fruits"

    val mockHit = Hit(
        id = 0,
        previewURL = "https://www.ipcc.ch/site/assets/uploads/sites/3/2019/10/img-placeholder.png",
        tags = "test, test test",
        user = "TestUser",
        largeImageURL = "https://www.ipcc.ch/site/assets/uploads/sites/3/2019/10/img-placeholder.png",
        likes = 100,
        comments = 100,
        downloads = 100000
    )
}