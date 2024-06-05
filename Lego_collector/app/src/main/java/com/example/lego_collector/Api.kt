package com.example.lego_collector

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun sendApiRequest(query: String): String? = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://rebrickable.com/api/v3/lego/sets/?search=$query")
        .addHeader("Authorization", "key f36cba6ba65a184bbe8a4a5ba704c213")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        return@withContext response.body?.string()
    }
}