package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String,
    val sender: String, // "bot" or "user"
    val text: String,
    val timestamp: String,
    val structuredData: List<Pair<String, String>>? = null,
    val actionText: String? = null,
    val actionRoute: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIAdvisorScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToVoiceMode: () -> Unit = {},
    onNavigateToReport: (String) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf<ChatMessage>()
    }
    val primaryPurple = Color(0xFF5B32E9)

    val webhookUrl = "https://agents-backend.dronahq.com/webhook/b0fc1da7-7fd9-4f22-a810-cd8893dddae1"
    val apiKey = "sk_Adr7hCyX3B0IU6mI19lnV8PMLaw6Y644"
    val nvidiaApiKey = "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"
    val nvidiaModel = "nvidia/llama-3.1-nemotron-70b-instruct"

    fun handleSend(query: String) {
        if (query.isBlank()) return
        val userMsg = ChatMessage(
            id = System.currentTimeMillis().toString(),
            sender = "user",
            text = query,
            timestamp = "Just now"
        )
        messages.add(userMsg)

        // Show a typing indicator/loading state
        val loadingMsg = ChatMessage(
            id = "loading",
            sender = "bot",
            text = "Thinking...",
            timestamp = "Just now"
        )
        messages.add(loadingMsg)
        
        coroutineScope.launch {
            listState.animateScrollToItem(messages.size - 1)
        }

        coroutineScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            try {
                // Step 1: Fetch context from DronaHQ
                val url = java.net.URL(webhookUrl)
                val connection = url.openConnection() as java.net.HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", "Bearer $apiKey")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val dronaPayload = org.json.JSONObject().apply {
                    put("request", query)
                }.toString()

                connection.outputStream.use { os ->
                    val input = dronaPayload.toByteArray(Charsets.UTF_8)
                    os.write(input, 0, input.size)
                }

                var dronaContext = ""
                if (connection.responseCode == java.net.HttpURLConnection.HTTP_OK) {
                    dronaContext = connection.inputStream.bufferedReader().use { it.readText() }
                }

                // Step 2: Query NVIDIA LLM with the context
                val nvidiaUrl = java.net.URL("https://integrate.api.nvidia.com/v1/chat/completions")
                val nvConnection = nvidiaUrl.openConnection() as java.net.HttpURLConnection
                nvConnection.requestMethod = "POST"
                nvConnection.setRequestProperty("Authorization", "Bearer $nvidiaApiKey")
                nvConnection.setRequestProperty("Content-Type", "application/json")
                nvConnection.doOutput = true

                val messagesArray = org.json.JSONArray().apply {
                    put(org.json.JSONObject().apply {
                        put("role", "system")
                        put("content", "You are Vyapaar AI, a helpful business assistant. Answer the user's question based strictly on the provided Context from the database. Make it conversational, concise, and easy to read. Context: $dronaContext")
                    })
                    put(org.json.JSONObject().apply {
                        put("role", "user")
                        put("content", query)
                    })
                }

                val nvPayload = org.json.JSONObject().apply {
                    put("model", nvidiaModel)
                    put("messages", messagesArray)
                    put("max_tokens", 512)
                    put("temperature", 0.7)
                }.toString()

                nvConnection.outputStream.use { os ->
                    val input = nvPayload.toByteArray(Charsets.UTF_8)
                    os.write(input, 0, input.size)
                }

                if (nvConnection.responseCode == java.net.HttpURLConnection.HTTP_OK) {
                    val nvResponseStr = nvConnection.inputStream.bufferedReader().use { it.readText() }
                    val nvJson = org.json.JSONObject(nvResponseStr)
                    val choices = nvJson.optJSONArray("choices")
                    val finalMessage = if (choices != null && choices.length() > 0) {
                        choices.getJSONObject(0).optJSONObject("message")?.optString("content") ?: "I couldn't process that properly."
                    } else {
                        "No response generated."
                    }

                    kotlinx.coroutines.Dispatchers.Main.dispatch(kotlin.coroutines.EmptyCoroutineContext, Runnable {
                        messages.remove(loadingMsg)
                        messages.add(
                            ChatMessage(
                                id = System.currentTimeMillis().toString(),
                                sender = "bot",
                                text = finalMessage,
                                timestamp = "Just now"
                            )
                        )
                        coroutineScope.launch {
                            listState.animateScrollToItem(messages.size - 1)
                        }
                    })
                } else {
                    val errorResp = try { nvConnection.errorStream?.bufferedReader()?.use { it.readText() } } catch (e: Exception) { "" }
                    kotlinx.coroutines.Dispatchers.Main.dispatch(kotlin.coroutines.EmptyCoroutineContext, Runnable {
                        messages.remove(loadingMsg)
                        messages.add(
                            ChatMessage(
                                id = System.currentTimeMillis().toString(),
                                sender = "bot",
                                text = "NVIDIA AI error (${nvConnection.responseCode}): $errorResp",
                                timestamp = "Just now"
                            )
                        )
                    })
                }
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.Main.dispatch(kotlin.coroutines.EmptyCoroutineContext, Runnable {
                    messages.remove(loadingMsg)
                    messages.add(
                        ChatMessage(
                            id = System.currentTimeMillis().toString(),
                            sender = "bot",
                            text = "Connection error: ${e.message}",
                            timestamp = "Just now"
                        )
                    )
                })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1E293B)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "AI Business Advisor",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Online",
                            fontSize = 12.sp,
                            color = Color(0xFF10B981),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Voice Mode Toggle Button (as seen in mockup top right)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, primaryPurple.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .background(Color(0xFFF3EFFF))
                    .clickable { onNavigateToVoiceMode() }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Mode",
                    tint = primaryPurple,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Voice",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryPurple
                )
            }
        }

        Divider(color = Color(0xFFE2E8F0))

        // Message List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(messages) { message ->
                if (message.sender == "user") {
                    // User bubble
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = 16.dp,
                                        bottomEnd = 4.dp
                                    )
                                )
                                .background(primaryPurple)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = message.text,
                                color = Color.White,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = message.timestamp,
                            fontSize = 10.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                } else {
                    // Bot response card
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.92f)
                                .clip(RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = message.text,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E293B),
                                    lineHeight = 20.sp
                                )

                                if (message.structuredData != null) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    message.structuredData.forEach { (item, value) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(item, fontSize = 13.sp, color = Color(0xFF475569))
                                            Text(
                                                value,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF10B981)
                                            )
                                        }
                                    }
                                }

                                if (message.actionText != null) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(
                                        onClick = {
                                            if (message.actionRoute != null) {
                                                onNavigateToReport(message.actionRoute)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3EFFF)),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = message.actionText,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = primaryPurple
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = message.timestamp,
                            fontSize = 10.sp,
                            color = Color(0xFF94A3B8),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            // Suggestion Chips (under first bot message) - Removed
        }

        // Chat Input Bar
        Divider(color = Color(0xFFE2E8F0))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("Ask anything...", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryPurple,
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color(0xFFF8FAFC),
                    unfocusedContainerColor = Color(0xFFF8FAFC)
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    handleSend(inputText)
                    inputText = ""
                })
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Voice Mode / Mic Button
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(primaryPurple)
                    .clickable {
                        if (inputText.isNotBlank()) {
                            handleSend(inputText)
                            inputText = ""
                        } else {
                            onNavigateToVoiceMode()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (inputText.isNotBlank()) Icons.Default.Send else Icons.Default.Mic,
                    contentDescription = "Voice / Send",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
fun SuggestionChipItem(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF5B32E9).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .background(Color(0xFFF3EFFF))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5B32E9)
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color(0xFF5B32E9),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
