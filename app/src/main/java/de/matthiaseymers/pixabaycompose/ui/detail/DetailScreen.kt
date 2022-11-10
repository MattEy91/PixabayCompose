package de.matthiaseymers.pixabaycompose.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.matthiaseymers.pixabaycompose.R
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import de.matthiaseymers.pixabaycompose.core.Constants.mockHit
import de.matthiaseymers.pixabaycompose.domain.model.Hit
import de.matthiaseymers.pixabaycompose.ui.theme.PixabayComposeTheme

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    hit: Hit
) {
    DetailContent(
        hit = hit,
        navHostController = navHostController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    navHostController: NavHostController? = null,
    hit: Hit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                onClick = { navHostController?.navigateUp() }
            ) {
                Text(text = stringResource(id = R.string.detail_button_back))
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp * 0.3).dp),
                model = hit.largeImageURL,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Text(
                text = hit.user,
                modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = hit.tags,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(id = R.string.detail_likes, hit.likes),
                modifier = Modifier.padding(top = 2.dp, start = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(id = R.string.detail_downloads, hit.downloads),
                modifier = Modifier.padding(top = 2.dp, start = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(id = R.string.detail_comments, hit.comments),
                modifier = Modifier.padding(top = 2.dp, start = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightPreview() {
    PixabayComposeTheme(useDarkTheme = false) {
        DetailContent(
            hit = mockHit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    PixabayComposeTheme(useDarkTheme = true) {
        DetailContent(
            hit = mockHit
        )
    }
}