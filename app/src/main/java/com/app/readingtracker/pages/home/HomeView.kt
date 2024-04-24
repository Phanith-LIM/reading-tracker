package com.app.readingtracker.pages.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeView(homeViewModel: HomeViewModel?) {
    homeViewModel?.counter?.observeAsState()?.value.let {
        Log.i("HomeView", "Counter: $it")
    }
    return Scaffold (
        content = {
            Box(
                modifier = Modifier.padding(it),
                contentAlignment = Alignment.Center
            ) {
               Row(
                   modifier = Modifier.padding(it),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   IconButton(
                       onClick = {
                            homeViewModel?.onIncrease()
                       }
                   ){
                          Text(text = "-")
                   }
                   Text(text = "${homeViewModel?.counter?.value}")
                   IconButton(
                       onClick = {
                           homeViewModel?.onDecrease()
                       }
                   ){
                       Text(text = "+")
                   }

               }
            }
        }
    )
}