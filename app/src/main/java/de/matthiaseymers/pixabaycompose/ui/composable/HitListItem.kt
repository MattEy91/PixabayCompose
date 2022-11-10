package de.matthiaseymers.pixabaycompose.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.matthiaseymers.pixabaycompose.core.Constants.mockHit
import de.matthiaseymers.pixabaycompose.ui.theme.PixabayComposeTheme
import de.matthiaseymers.pixabaycompose.domain.model.Hit

@Composable
fun HitListItem(
    hit: Hit? = null,
    onItemClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(
                onClick = onItemClicked,
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .height(75.dp)
                .width(75.dp),
            model = hit?.previewURL,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
            hit?.user?.let { Text(text = it, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            hit?.tags?.let { Text(text = it, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightPreview() {
    PixabayComposeTheme(useDarkTheme = false) {
        HitListItem(hit = mockHit)
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    PixabayComposeTheme(useDarkTheme = true) {
        HitListItem(hit = mockHit)
    }
}