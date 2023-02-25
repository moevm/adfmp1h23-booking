package com.etu.booking.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProgressIndicator(isLoading: MutableState<Boolean>, load: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
        load(isLoading, load)
    }
}

fun load(isLoading: MutableState<Boolean>, load: () -> Unit) = effect {
    load()
    isLoading.value = false
}

private fun effect(block: suspend () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
        block()
    }
}