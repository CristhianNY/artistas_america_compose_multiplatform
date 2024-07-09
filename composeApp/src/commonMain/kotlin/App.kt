import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        Column(Modifier.fillMaxWidth()) {
            TopNavigationBar()
            MainContent()
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
                Button(onClick = { /**TODO Handle List Your Service*/ })
                {
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
fun MainContent() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageCarousel(
            modifier = Modifier.weight(1f).height(400.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        MainTextContent(
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    SearchTextField()
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
fun MainTextContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Book something",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "awesome",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF007BFF)
        )
        Text(
            text = "for your next event",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "From birthday parties to weddings, we'll help you book the best talent for any occasion",
            fontSize = 16.sp,
            color = Color.Gray
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
        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.weight(1f),
            decorationBox = { innerTextField ->
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "What kind of talent or service can we help you find?",
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )
        Button(
            onClick = { /** TODO: handle search Click */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
        ) {
            Text("Search")
        }
    }
}