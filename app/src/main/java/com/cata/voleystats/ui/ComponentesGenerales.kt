package com.cata.voleystats.ui

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun darkTextFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedContainerColor = Color.DarkGray,
    unfocusedContainerColor = Color.DarkGray,
    focusedLabelColor = Color.LightGray,
    unfocusedLabelColor = Color.LightGray,
    cursorColor = Color.White
)
