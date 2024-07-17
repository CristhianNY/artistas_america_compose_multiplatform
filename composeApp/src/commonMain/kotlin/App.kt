// commonMain/src/commonMain/kotlin/com/example/common/SharedComposables.kt

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        var showLoginDialog by remember { mutableStateOf(false) }

        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            TopNavigationBar { showLoginDialog = true }
            PlatformSpecificMainContent()
            Spacer(modifier = Modifier.height(40.dp))
            CategoriesSection()
            Spacer(modifier = Modifier.height(10.dp))
            HowWorksSection()
            CenteredButton()
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (showLoginDialog) {
            LoginDialog(onDismiss = { showLoginDialog = false })
        }
    }
}

@Composable
fun TopNavigationBar(onLoginClick: () -> Unit) {
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
                TextButton(onClick = onLoginClick) {
                    Text(
                        text = "Log In",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { /* TODO Handle List Your Service */ }) {
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
            modifier = Modifier.padding(end = 8.dp)
        )
        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
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
                        fontSize = 12.sp
                    )
                }
                innerTextField()
            }
        )
        Button(
            onClick = { /* TODO: handle search Click */ },
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
            .width(350.dp)
            .height(400.dp)
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

@Composable
fun LoginDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .widthIn(min = 200.dp, max = 280.dp)
                .shadow(16.dp, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colors.surface,
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hello again.",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var forgotPasswordEmail by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }
                var showForgotPasswordField by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* Handle login */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text("Log In", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { showForgotPasswordField = !showForgotPasswordField }) {
                    Text("Forgot your password?", color = MaterialTheme.colors.primary)
                }

                if (showForgotPasswordField) {
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = forgotPasswordEmail,
                        onValueChange = { forgotPasswordEmail = it },
                        label = { Text("Type your email address below to reset your password") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.primary,
                            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                        )
                    )

                    Button(
                        onClick = { /* Handle password reset */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Text("Reset Password", color = Color.White)
                    }
                }

                TextButton(onClick = { /* Handle trouble logging in */ }) {
                    Text("Trouble logging in?", color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}

data class Category(val title: String, val description: String, val imageUrl: String)
