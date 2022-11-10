package de.matthiaseymers.pixabaycompose.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import de.matthiaseymers.pixabaycompose.core.Constants.INITIAL_SEARCH_QUERY

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    placeholderText: String = "",
    onKeyboardDoneClick: (String) -> Unit = {}
) {
    var showClearButton by remember { mutableStateOf(false) }
    var textInput by remember { mutableStateOf(INITIAL_SEARCH_QUERY) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    TopAppBar(title = { Text("") },
        actions = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .onFocusChanged { focusState ->
                        showClearButton = (focusState.isFocused)
                    }
                    .focusRequester(focusRequester),
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = {
                    Text(text = placeholderText)
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = showClearButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = { textInput = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = ""
                            )
                        }
                    }
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onKeyboardDoneClick(textInput)
                    keyboardController?.hide()
                }),
            )
        })
}