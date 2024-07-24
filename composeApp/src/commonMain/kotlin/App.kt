import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import auth.presentation.AuthViewModel
import auth.presentation.LoginDialog
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.coroutines.delay
import navigation.Child
import navigation.DashboardComponent
import navigation.HomeComponent
import navigation.RootComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        val childStack by rootComponent.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->

            when (val instance = child.instance) {
                is Child.DashBoardScreen -> {
                    /**HomeScreen(instance.component)**/
                }

                is Child.HomeScreen -> HomeScreen(instance.component)
            }
        }
    }
}

@Composable
fun HomeScreen(component: HomeComponent) {
    val authViewModel: AuthViewModel = koinInject<AuthViewModel>()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    var showLoginDialog by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        TopNavigationBar(isLoggedIn, { showLoginDialog = true }, { authViewModel.logout() })
        PlatformSpecificMainContent()
        Spacer(modifier = Modifier.height(40.dp))
        CategoriesSection()
        Spacer(modifier = Modifier.height(10.dp))
        HowWorksSection()
        CenteredButton()
        Spacer(modifier = Modifier.height(10.dp))
    }

    if (showLoginDialog) {
        LoginDialog(onDismiss = { showLoginDialog = false }, authViewModel)
    }
}

@Composable
fun TopNavigationBar(isLoggedIn: Boolean, onLoginClick: () -> Unit, onLogoutClick: () -> Unit) {
    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        BoxWithConstraints {
            val isSmallScreen = maxWidth < 600.dp

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
                    if (!isLoggedIn) {
                        TextButton(onClick = onLoginClick) {
                            Text(
                                text = "Log In",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        TextButton(onClick = onLogoutClick) {
                            Text(
                                text = "Log Out",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF)),
                        onClick = { /* TODO Handle List Your Service */ }) {
                        Text(
                            text = if (isSmallScreen) "Add" else "List your Services",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(modifier: Modifier = Modifier) {
    val pageCount = 5
    val pagerState = rememberPagerState(initialPage = 0) { pageCount }
    val coroutineScope = rememberCoroutineScope()

    // LaunchedEffect para cambiar la página automáticamente
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000) // Tiempo de espera en milisegundos (3 segundos)
            val nextPage = (pagerState.currentPage + 1) % pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

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
            AsyncImage(
                model = "https://picsum.photos/200/300.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        },
    )
}

@Composable
fun MainTextContent(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val fontSize = if (maxWidth < 600.dp) 24.sp else 36.sp
        Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Text(
                text = "Book something awesome",
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007BFF)
            )

            Text(
                text = "for your next event",
                fontSize = fontSize,
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
}

@Composable
fun SearchTextField() {
    BoxWithConstraints {
        val isSmallScreen = maxWidth < 600.dp
        val padding = if (isSmallScreen) 8.dp else 16.dp
        var searchQuery by remember { mutableStateOf("") }

        if (!isSmallScreen) {
            // Layout for mobile devices
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = padding, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start
                        ),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "What kind of talent or service can we help you find?",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            innerTextField()
                        }
                    )
                }
                Button(
                    onClick = { /* TODO: handle search click */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Search",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            // Layout for web
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = padding, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    ),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "What kind of talent or service can we help you find?",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                )
                Button(
                    onClick = { /* TODO: handle search click */ },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Search",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesSection() {
    BoxWithConstraints {
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF6F9FA))) {
            Text(
                text = "Whatever you're planning, make it extraordinary.",
                fontSize = if (this@BoxWithConstraints.maxWidth < 600.dp) 18.sp else 24.sp,
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
            Spacer(modifier = Modifier.height(35.dp))
            MusicalActs()
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Entertainers",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(35.dp))
            EntertainersCategories()

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Event Services",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(35.dp))
            EventsServices()
            Spacer(modifier = Modifier.height(50.dp))
            // Add more LazyRow or grids as needed for the Entertainers section
        }
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
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Ensembles",
                        "Chamber Orchestras, Classical Ensembles, String Trios...",
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Singers",
                        "Country Singers, Singing Guitarists, Rappers...",
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Solo Musicians",
                        "Bagpipers, Guitarists, Pianists, Violinists...",
                        "https://picsum.photos/200/300.jpg"
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
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Ensembles",
                        "Chamber Orchestras, Classical Ensembles, String Trios...",
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Singers",
                        "Country Singers, Singing Guitarists, Rappers...",
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Solo Musicians",
                        "Bagpipers, Guitarists, Pianists, Violinists...",
                        "https://picsum.photos/200/300.jpg"
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
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Ensembles",
                        "Chamber Orchestras, Classical Ensembles, String Trios...",
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Singers",
                        "Country Singers, Singing Guitarists, Rappers...",
                        "https://picsum.photos/200/300.jpg"
                    ),
                    Category(
                        "Solo Musicians",
                        "Bagpipers, Guitarists, Pianists, Violinists...",
                        "https://picsum.photos/200/300.jpg"
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
    var flipped by remember { mutableStateOf(false) }
    val rotation = animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .widthIn(max = 350.dp)
            .heightIn(max = 400.dp)
            .padding(8.dp)
            .clickable { flipped = !flipped }
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            }
    ) {
        if (rotation.value <= 90f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(8.dp)
                    .graphicsLayer { rotationY = 0f }
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x80000000)) // Fondo semi-transparente para el texto
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = description,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(8.dp)
                    .graphicsLayer { rotationY = 180f },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Additional Info",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CenteredButton() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { /* TODO: Handle button click */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
        ) {
            Text(
                text = "Get Started",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}


data class Category(val title: String, val description: String, val imageUrl: String)
