package com.example.weatherapp2.ui.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.weatherapp2.MainViewModel

@Composable
fun BottomNavBar(viewModel: MainViewModel, items: List<BottomNavItem>) {
    NavigationBar(contentColor = Color.Black) {
        items.forEach { item ->
            val isSelected = viewModel.page == item.route
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title, fontSize = 12.sp) },
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = {
                    viewModel.page = item.route
                }
            )
        }
    }
}
