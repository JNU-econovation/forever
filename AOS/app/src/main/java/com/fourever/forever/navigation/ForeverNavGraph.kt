package com.fourever.forever.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fourever.forever.presentation.home.HomeScreen
import com.fourever.forever.presentation.home.HomeViewModel

@Composable
fun ForeverNavGraph(
    navController: NavHostController = rememberNavController(),
    navActions: ForeverNavActions = remember(navController) {
        ForeverNavActions(navController)
    },
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val homeUiState by homeViewModel.homeUiState.collectAsState()

            HomeScreen(
                homeUiState = homeUiState,
                navigateToUploadFile = { navActions.navigateToUploadFile() },
                navigateToGetSummary = { documentId -> navActions.navigateToGetSummary(documentId) },
                loadMoreFile = { page -> (homeViewModel::getFileList)(page) }
            )
        }
        detailGraph(navActions = navActions)
        generationGraph()
    }
}