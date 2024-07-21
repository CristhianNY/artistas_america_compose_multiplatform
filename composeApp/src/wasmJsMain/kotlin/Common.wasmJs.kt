import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
actual fun PlatformSpecificMainContent() {
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
    SearchTextFieldWeb()
}

@Composable
fun SearchTextFieldWeb() {
    BoxWithConstraints {
        val isSmallScreen = maxWidth < 800.dp
        val padding = if (isSmallScreen) 8.dp else 16.dp
        var searchQuery by remember { mutableStateOf("") }

        if (isSmallScreen) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 280.dp)
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
                /** TODO: handle search Click */
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
    }
}