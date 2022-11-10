package de.matthiaseymers.pixabaycompose.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.matthiaseymers.pixabaycompose.R
import de.matthiaseymers.pixabaycompose.ui.theme.PixabayComposeTheme
import de.matthiaseymers.pixabaycompose.core.Constants.EXTRA_ARGUMENT_HIT
import de.matthiaseymers.pixabaycompose.core.Constants.INITIAL_SEARCH_QUERY
import de.matthiaseymers.pixabaycompose.core.isOnline
import de.matthiaseymers.pixabaycompose.core.showGenericErrorToast
import de.matthiaseymers.pixabaycompose.domain.model.Hit
import de.matthiaseymers.pixabaycompose.domain.model.SearchHitEvent
import de.matthiaseymers.pixabaycompose.navigation.Destination
import de.matthiaseymers.pixabaycompose.ui.composable.HitListItem
import de.matthiaseymers.pixabaycompose.ui.composable.SearchBar

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val hitEvent by viewModel.searchHitEvent.collectAsState(initial = null)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.searchHits(INITIAL_SEARCH_QUERY, context.isOnline())
    }

    SearchContent(
        navHostController = navHostController,
        searchHitEvent = hitEvent,
        onKeyboardDoneClicked = { viewModel.searchHits(it, context.isOnline()) }
    )
}

private fun navigateToDetailScreen(
    navHostController: NavHostController,
    hit: Hit
) {
    navHostController.currentBackStackEntry?.savedStateHandle?.set(EXTRA_ARGUMENT_HIT, hit)
    navHostController.navigate(Destination.Detail.route)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    navHostController: NavHostController? = null,
    searchHitEvent: SearchHitEvent? = null,
    onKeyboardDoneClicked: (String) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            SearchBar(
                placeholderText = stringResource(id = R.string.search_hint),
                onKeyboardDoneClick = { onKeyboardDoneClicked(it) }
            )

            when (searchHitEvent) {
                is SearchHitEvent.Error -> LocalContext.current.showGenericErrorToast()
                is SearchHitEvent.Loading -> CircularProgressIndicator()
                is SearchHitEvent.Success -> HitsListLazyColumn(
                    hits = searchHitEvent.hits,
                    navHostController = navHostController!!
                )
                null -> Unit
            }
        }
    }
}

@Composable
fun HitsListLazyColumn(
    navHostController: NavHostController,
    hits: List<Hit>
) {
    PixabayComposeTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            itemsIndexed(
                items = hits,
                key = { _, newsEntry -> newsEntry.id },
                itemContent = { _, element ->
                    HitListItem(hit = element) {
                        navigateToDetailScreen(
                            navHostController = navHostController,
                            hit = element
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightPreview() {
    PixabayComposeTheme(useDarkTheme = false) {
        SearchContent()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    PixabayComposeTheme(useDarkTheme = true) {
        SearchContent()
    }
}