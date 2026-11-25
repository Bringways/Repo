package com.example.matka

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class UiMarket(
    val id: String,
    val name: String,
    val result: String,
    val openTime: String,
    val closeTime: String,
    val isClosed: Boolean = false
)

@Composable
fun MainHomeShell(
    userPhone: String,
    onLogout: () -> Unit
) {
    val bg = Color(0xFF090E1A)
    val surface = Color(0xFF121A2E)
    val elevated = Color(0xFF1A2540)
    val border = Color(0xFF283659)
    val gold = Color(0xFFF5C451)
    val sapphire = Color(0xFF3B82F6)
    val sapphireDark = Color(0xFF1D4ED8)
    val emerald = Color(0xFF10B981)
    val crimson = Color(0xFFF43F5E)
    val textPrimary = Color(0xFFF8FAFC)
    val textSecondary = Color(0xFF94A3B8)

    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf("Home") }
    var walletBalance by remember { mutableIntStateOf(0) }

    val markets = remember {
        listOf(
            UiMarket("1", "SRIDEVI", "578-0*-***", "11:40 AM", "12:40 PM"),
            UiMarket("2", "TIME BAZAR", "***-**-***", "01:10 PM", "02:10 PM"),
            UiMarket("3", "MADHUR DAY", "***-**-***", "01:30 PM", "02:30 PM"),
            UiMarket("4", "MILAN DAY", "***-**-***", "03:10 PM", "05:10 PM"),
            UiMarket("5", "RAJDHANI DAY", "***-**-***", "03:10 PM", "05:10 PM"),
            UiMarket("6", "SUPREME DAY", "***-**-***", "03:35 PM", "05:35 PM"),
            UiMarket("7", "KALYAN", "***-**-***", "04:30 PM", "06:30 PM"),
            UiMarket("8", "SRIDEVI NIGHT", "***-**-***", "07:20 PM", "08:20 PM"),
            UiMarket("9", "MADHUR NIGHT", "***-**-***", "08:30 PM", "10:30 PM"),
            UiMarket("10", "SUPREME NIGHT", "***-**-***", "08:45 PM", "10:45 PM"),
            UiMarket("11", "MILAN NIGHT", "***-**-***", "09:10 PM", "11:10 PM"),
            UiMarket("12", "KALYAN NIGHT", "***-**-***", "09:30 PM", "11:30 PM"),
            UiMarket("13", "RAJDHANI NIGHT", "***-**-***", "09:30 PM", "11:40 PM"),
            UiMarket("14", "MAIN BAZAR", "***-**-***", "09:55 PM", "11:58 PM"),
            UiMarket("15", "TEST", "***-**-***", "08:50 PM", "09:00 PM")
        )
    }

    val drawerItems = remember {
        listOf(
            "Home", "My Bids", "Passbook", "Get Gift", "Chat", "Funds",
            "Notice Board / Rules", "Game Rates", "Charts", "Settings",
            "Invite And Earn", "Share Application", "Logout"
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = surface,
                modifier = Modifier.width(290.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.linearGradient(listOf(Color(0xFF141E38), bg)))
                            .statusBarsPadding()
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(gold.copy(alpha = 0.16f))
                                    .border(2.dp, gold, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("👤", fontSize = 24.sp)
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text("Player", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(userPhone.ifBlank { "Member" }, color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                                Text("VIP • ₹$walletBalance.0", color = gold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    drawerItems.forEach { item ->
                        val isLogout = item == "Logout"
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    if (isLogout) onLogout()
                                    else Toast.makeText(context, "$item clicked", Toast.LENGTH_SHORT).show()
                                }
                                .padding(horizontal = 20.dp, vertical = 13.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("•", color = if (isLogout) crimson else gold, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.width(14.dp))
                            Text(item, color = if (isLogout) crimson else textPrimary, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = bg,
            topBar = {
                Surface(color = Color(0xFF0D1426), shadowElevation = 4.dp) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .height(58.dp)
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Text("☰", color = textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                            MatkaBrandLogo(size = 32.dp)
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = "MATKA GAMING APP",
                                color = textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(elevated)
                                    .border(1.dp, gold.copy(alpha = 0.5f), RoundedCornerShape(50))
                                    .clickable { Toast.makeText(context, "Wallet: ₹$walletBalance.0", Toast.LENGTH_SHORT).show() }
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("₹", color = gold, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                                Spacer(Modifier.width(4.dp))
                                Text("$walletBalance.0", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            IconButton(onClick = { Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show() }) {
                                Text("↻", color = textSecondary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        HorizontalDivider(color = border)
                    }
                }
            },
            bottomBar = {
                Box(modifier = Modifier.fillMaxWidth().navigationBarsPadding()) {
                    Surface(
                        color = Color(0xFF0D1426),
                        shadowElevation = 10.dp,
                        modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).height(62.dp)
                    ) {
                        Column {
                            HorizontalDivider(color = border)
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                listOf("My Bids", "Passbook", "", "Funds", "Support").forEach { tab ->
                                    if (tab.isEmpty()) {
                                        Spacer(Modifier.width(64.dp))
                                    } else {
                                        Text(
                                            text = tab,
                                            color = if (selectedTab == tab) gold else textSecondary,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable {
                                                    selectedTab = tab
                                                    Toast.makeText(context, "$tab selected", Toast.LENGTH_SHORT).show()
                                                }
                                                .padding(vertical = 12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(y = (-14).dp)
                            .size(56.dp)
                            .shadow(10.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(sapphire, sapphireDark)))
                            .border(2.dp, gold, CircleShape)
                            .clickable { selectedTab = "Home" },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🏠", fontSize = 22.sp)
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).background(bg),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(surface)
                            .border(0.5.dp, border)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Welcome to MATKA GAMING APP. India's Best Matka App for online Gaming  ★  24×7 Instant Deposit & Withdrawal  ★",
                            color = gold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth().basicMarquee().padding(horizontal = 12.dp)
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Brush.linearGradient(listOf(Color(0xFF15254A), Color(0xFF0C152B))))
                            .border(1.dp, gold.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Text("24×7 VIP SUPPORT", color = gold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            Text("Instant Deposit & Fast Payouts", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("100% Trusted • Automated Result Settlement", color = Color.White.copy(alpha = 0.75f), fontSize = 12.sp)
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ActionPill("Deposit", "🏦", gold, Modifier.weight(1f), sapphire, sapphireDark) {
                                Toast.makeText(context, "Deposit clicked", Toast.LENGTH_SHORT).show()
                            }
                            ActionPill("Withdrawal", "💸", emerald, Modifier.weight(1f), sapphire, sapphireDark) {
                                Toast.makeText(context, "Withdrawal clicked", Toast.LENGTH_SHORT).show()
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ActionPill("WhatsApp", "💬", Color(0xFF22C55E), Modifier.weight(1f), sapphire, sapphireDark) {
                                Toast.makeText(context, "WhatsApp clicked", Toast.LENGTH_SHORT).show()
                            }
                            ActionPill("Telegram", "✈", Color(0xFF0EA5E9), Modifier.weight(1f), sapphire, sapphireDark) {
                                Toast.makeText(context, "Telegram clicked", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SpecialTile("King Starline", "12 Hourly Slots", gold, surface, textPrimary, textSecondary, Modifier.weight(1f)) {
                            Toast.makeText(context, "King Starline clicked", Toast.LENGTH_SHORT).show()
                        }
                        SpecialTile("Desawar / Gali", "Jantri & Haruf", emerald, surface, textPrimary, textSecondary, Modifier.weight(1f)) {
                            Toast.makeText(context, "Desawar clicked", Toast.LENGTH_SHORT).show()
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }

                items(markets, key = { it.id }) { m ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = surface),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                            .border(1.dp, border, RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(Brush.linearGradient(listOf(sapphire, sapphireDark)))
                                    .border(1.dp, gold.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("📊", fontSize = 18.sp)
                            }
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(m.name, color = textPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                                Text(m.result, color = gold, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                                Spacer(Modifier.height(4.dp))
                                Text("Open: ${m.openTime}  |  Close: ${m.closeTime}", color = textSecondary, fontSize = 11.sp)
                            }
                            Spacer(Modifier.width(8.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    Toast.makeText(context, "Play ${m.name}", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Text("Running now", color = emerald, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                Spacer(Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(Brush.linearGradient(listOf(sapphire, sapphireDark)))
                                        .border(1.5.dp, gold, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("▶", color = Color.White, fontSize = 16.sp)
                                }
                                Text("Play Game", color = textSecondary, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionPill(
    label: String,
    emoji: String,
    badgeColor: Color,
    modifier: Modifier,
    sapphire: Color,
    sapphireDark: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(46.dp)
            .clip(RoundedCornerShape(50))
            .background(Brush.horizontalGradient(listOf(sapphire, sapphireDark)))
            .border(1.dp, Color(0xFFF5C451).copy(alpha = 0.4f), RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(start = 5.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(34.dp).clip(CircleShape).background(badgeColor),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 15.sp)
        }
        Spacer(Modifier.width(8.dp))
        Text(label, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
    }
}

@Composable
private fun SpecialTile(
    title: String,
    subtitle: String,
    accent: Color,
    surface: Color,
    textPrimary: Color,
    textSecondary: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(surface)
            .border(1.dp, accent.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("★", color = accent, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Column {
            Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(subtitle, color = textSecondary, fontSize = 11.sp)
        }
    }
}