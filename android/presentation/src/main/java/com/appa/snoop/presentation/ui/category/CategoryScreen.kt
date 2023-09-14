package com.appa.snoop.presentation.ui.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.appa.snoop.presentation.ui.category.component.CategoryItem

const val SIZE = 2
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(SIZE)
        ) {
            items(count = 10) {
                CategoryItem(
                    modifier = Modifier,
                    onItemClicked = { /*TODO*/ },
                    onLikeClicked = { /*TODO*/ }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategoryScreen() {
    CategoryScreen()
}