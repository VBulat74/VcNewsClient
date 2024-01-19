package ru.com.vbulat.vcnewsclient.navigaton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    newsFeedScreenContent : @Composable ()-> Unit,
    favoriteScreenContent : @Composable ()-> Unit,
    profileScreenContent : @Composable ()-> Unit,
    commentsScreenContent : @Composable (FeedPost)-> Unit,
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ){

        homeScreenNavGraph(
            newsFeedScreenContent = newsFeedScreenContent,
            commentsScreenContent = commentsScreenContent,
        )

        composable(
            route = Screen.Favorite.route,
        ){
            favoriteScreenContent()
        }
        composable(
            route = Screen.Profile.route,
        ){
            profileScreenContent()
        }
    }

}