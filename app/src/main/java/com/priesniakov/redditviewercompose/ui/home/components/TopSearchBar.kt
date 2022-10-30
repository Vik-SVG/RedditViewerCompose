package com.priesniakov.redditviewercompose.ui.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.priesniakov.redditviewercompose.ui.theme.TextAppBar
import com.priesniakov.redditviewercompose.ui.theme.Vermilion

@Composable
fun TopSearchBar(appBarTitle: String) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = appBarTitle, style = TextAppBar)
        Spacer(modifier = Modifier.size(width = 36.dp, height = 0.dp))

        var isSearchActive by rememberSaveable { mutableStateOf(false) }

        val onSearchClick = {
            isSearchActive = !isSearchActive
        }
        AnimatedVisibility(visible = isSearchActive) {
            SearchBar(onSearchIconClick = onSearchClick)
        }
        if (!isSearchActive) SearchIcon(onClick = onSearchClick)
    }
}

@Composable
private fun SearchBar(onSearchIconClick: () -> Unit) {
    var textState by rememberSaveable() {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = textState,
        onValueChange = { textState = it },
        modifier = Modifier
            .padding(0.dp)
            .defaultMinSize(2.dp),
        label = {
            Text(text = "Search")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        leadingIcon = {
            SearchIcon(onSearchIconClick)
        },
        keyboardActions = KeyboardActions(onSearch = {

        }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Vermilion,
            leadingIconColor = Vermilion
        ),
        shape = TextFieldDefaults.OutlinedTextFieldShape, singleLine = true,
    )
}

@Composable
private fun SearchIcon(onClick: () -> Unit = {}) {
    Icon(Icons.Filled.Search, "Search", Modifier.clickable { onClick() })
}