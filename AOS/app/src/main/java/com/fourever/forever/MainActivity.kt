package com.fourever.forever

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fourever.forever.navigation.ForeverNavGraph
import com.fourever.forever.ui.theme.ForeverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForeverTheme {
                ForeverNavGraph()
            }
        }
    }
}