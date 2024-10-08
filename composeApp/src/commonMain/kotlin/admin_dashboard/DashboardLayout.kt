package admin_dashboard

import Strings.ACCOUNT
import Strings.ARROW_DOWN
import Strings.BACK
import Strings.CALENDAR
import Strings.DASHBOARD
import Strings.EDIT_PROMOKIT
import Strings.FAVORITE
import Strings.HELP
import Strings.INBOX
import Strings.MENU
import Strings.MESSAGES
import Strings.REVIEWS
import Strings.TOOLS
import Strings.USERNAME
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import navigation.dashboard.DashboardComponent
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardLayout(component: DashboardComponent) {
    BoxWithConstraints {
        val isSmallScreen = maxWidth < 600.dp

        if (isSmallScreen) {
            MobileDashboardLayout(component)
        } else {
            WebDashboardLayout(component)
        }
    }
}

@Composable
fun MobileDashboardLayout(component: DashboardComponent) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DashboardDrawerContent(scope, drawerState)
        },
        content = {
            MainContent(scope, drawerState)
        }
    )
}

@Composable
fun WebDashboardLayout(component: DashboardComponent) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        DashboardTopAppBarWeb(component)
        Spacer(modifier = Modifier.height(16.dp))
        DashboardTabs()
    }
}

@Composable
fun MainContent(scope: CoroutineScope, drawerState: DrawerState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        DashboardTopAppBar(scope, drawerState)
        Spacer(modifier = Modifier.height(16.dp))
        // Aquí puedes agregar más contenido principal del Dashboard
    }
}

@Composable
fun DashboardTopAppBar(scope: CoroutineScope, drawerState: DrawerState) {
    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    painter = painterResource(Res.drawable.compose_multiplatform), // Replace with menu icon resource
                    contentDescription = MENU,
                    modifier = Modifier.size(24.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform), // Replace with your logo resource
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = DASHBOARD,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconsAndUserMenu()
        }
    }
}

@Composable
fun DashboardTopAppBarWeb(component: DashboardComponent) {
    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { component.goBack() }) {
                Icon(
                    painter = painterResource(Res.drawable.compose_multiplatform), // Replace with back arrow icon resource
                    contentDescription = BACK,
                    modifier = Modifier.size(24.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform), // Replace with your logo resource
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text =DASHBOARD,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconsAndUserMenu()
        }
    }
}

@Composable
fun IconsAndUserMenu() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with favorite icon resource
            contentDescription = FAVORITE,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with messages icon resource
            contentDescription = MESSAGES,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = USERNAME,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with arrow down resource
            contentDescription = ARROW_DOWN,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun DashboardDrawerContent(scope: CoroutineScope, drawerState: DrawerState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = DASHBOARD,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text =INBOX,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = REVIEWS,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = EDIT_PROMOKIT,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = CALENDAR,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = TOOLS,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = ACCOUNT,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = HELP,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
    }
}

@Composable
fun DashboardTabs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TabItem(text = DASHBOARD, isSelected = true)
        TabItem(text = INBOX)
        TabItem(text = REVIEWS)
        TabItem(text = EDIT_PROMOKIT)
        TabItem(text = CALENDAR)
        TabItem(text = TOOLS)
        TabItem(text = ACCOUNT)
        TabItem(text = HELP)
    }
}

@Composable
fun TabItem(text: String, isSelected: Boolean = false) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) Color.Blue else Color.Gray
    )
}
