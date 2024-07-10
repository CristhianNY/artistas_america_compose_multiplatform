// commonMain/src/commonMain/kotlin/com/example/common/SharedComposables.kt
package com.example.common

import PlatformSpecificMainContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            TopNavigationBar()
            PlatformSpecificMainContent()
            Spacer(modifier = Modifier.height(40.dp))
            CategoriesSection()
        }
    }
}

@Composable
fun TopNavigationBar() {
    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painterResource(Res.drawable.compose_multiplatform), null,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Menu",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Arrow Down",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Favorite",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                TextButton(onClick = { /**TODO handle log in Click**/ }) {
                    Text(
                        text = "Log In",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { /**TODO Handle List Your Service*/ }) {
                    Text(
                        text = "List your Services",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(modifier: Modifier = Modifier) {
    val pageCount = 5
    val pagerState = rememberPagerState { pageCount }

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        pageSize = PageSize.Fill,
        beyondBoundsPageCount = PagerDefaults.BeyondBoundsPageCount,
        pageSpacing = 0.dp,
        verticalAlignment = Alignment.CenterVertically,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
        userScrollEnabled = true,
        reverseLayout = false,
        key = null,
        pageNestedScrollConnection = remember(pagerState) {
            PagerDefaults.pageNestedScrollConnection(
                pagerState,
                Orientation.Horizontal
            )
        },
        pageContent = { page ->
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        },
    )
}

@Composable
fun MainTextContent(modifier: Modifier = Modifier, fontSize: Int = 36) {
    Column(modifier = modifier.horizontalScroll(rememberScrollState())) {
        Text(
            text = "Book something awesome",
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF007BFF)
        )

        Text(
            text = "for your next event",
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "From birthday parties to weddings, we'll help you book the best talent for any occasion",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
fun SearchTextField() {
    var searchQuery by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            modifier = Modifier.padding(end = 8.dp) // Margen entre el icono y el campo de texto
        )
        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.weight(1f),
            decorationBox = { innerTextField ->
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "What kind of talent or service can we help you find?",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                innerTextField()
            }
        )
        Button(
            onClick = { /** TODO: handle search Click */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
        ) {
            Text(
                "Search",
                color = Color.White
            )
        }
    }
}

@Composable
fun CategoriesSection() {
    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF6F9FA))) {
        Text(
            text = "Whatever you're planning, make it extraordinary.",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Musical Acts",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(40.dp))
        MusicalActs()
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Entertainers",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(40.dp))
        EntertainersCategories()

        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Event Services",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(40.dp))
        EventsServices()
        Spacer(modifier = Modifier.height(60.dp))
        // Add more LazyRow or grids as needed for the Entertainers section
    }
}

@Composable
fun EntertainersCategories() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            items(
                listOf(
                    Category(
                        "Bands & Groups",
                        "Blues Bands, Mariachis, Wedding Bands...",
                        "https://example.com/bands.jpg"
                    ),
                    Category(
                        "Ensembles",
                        "Chamber Orchestras, Classical Ensembles, String Trios...",
                        "https://example.com/ensembles.jpg"
                    ),
                    Category(
                        "Singers",
                        "Country Singers, Singing Guitarists, Rappers...",
                        "https://example.com/singers.jpg"
                    ),
                    Category(
                        "Solo Musicians",
                        "Bagpipers, Guitarists, Pianists, Violinists...",
                        "https://example.com/solo.jpg"
                    )
                )
            ) { category ->
                CategoryCard(
                    title = category.title,
                    description = category.description,
                    imageUrl = category.imageUrl
                )
            }
        }
    }
}

@Composable
fun MusicalActs() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            items(
                listOf(
                    Category(
                        "Bands & Groups",
                        "Blues Bands, Mariachis, Wedding Bands...",
                        "https://example.com/bands.jpg"
                    ),
                    Category(
                        "Ensembles",
                        "Chamber Orchestras, Classical Ensembles, String Trios...",
                        "https://example.com/ensembles.jpg"
                    ),
                    Category(
                        "Singers",
                        "Country Singers, Singing Guitarists, Rappers...",
                        "https://example.com/singers.jpg"
                    ),
                    Category(
                        "Solo Musicians",
                        "Bagpipers, Guitarists, Pianists, Violinists...",
                        "https://example.com/solo.jpg"
                    )
                )
            ) { category ->
                CategoryCard(
                    title = category.title,
                    description = category.description,
                    imageUrl = category.imageUrl
                )
            }
        }
    }
}

@Composable
fun EventsServices() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            items(
                listOf(
                    Category(
                        "Bands & Groups",
                        "Blues Bands, Mariachis, Wedding Bands...",
                        "https://example.com/bands.jpg"
                    ),
                    Category(
                        "Ensembles",
                        "Chamber Orchestras, Classical Ensembles, String Trios...",
                        "https://example.com/ensembles.jpg"
                    ),
                    Category(
                        "Singers",
                        "Country Singers, Singing Guitarists, Rappers...",
                        "https://example.com/singers.jpg"
                    ),
                    Category(
                        "Solo Musicians",
                        "Bagpipers, Guitarists, Pianists, Violinists...",
                        "https://example.com/solo.jpg"
                    )
                )
            ) { category ->
                CategoryCard(
                    title = category.title,
                    description = category.description,
                    imageUrl = category.imageUrl
                )
            }
        }
    }
}

@Composable
fun CategoryCard(title: String, description: String, imageUrl: String) {
    Column(
        modifier = Modifier
            .width(350.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(8.dp)
            .shadow(1.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

data class Category(val title: String, val description: String, val imageUrl: String)
