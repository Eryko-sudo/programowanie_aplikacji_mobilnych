package com.example.lego_collector

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<LegoSet>
)
class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 columns

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    performSearch(query)
                }
                // Hide keyboard
                val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Automatically search for "Star Wars" when the app opens
        searchView.setQuery("Starfighter", true)

        return view
    }

    private fun performSearch(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = sendApiRequest(query)
            val legoSets = parseResponse(response)
            recyclerView.adapter = LegoSetAdapter(legoSets)
        }
    }

    private suspend fun sendApiRequest(query: String): String? = withContext(Dispatchers.IO) {
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

    private fun parseResponse(response: String?): List<LegoSet> {
        val apiResponse = Json { ignoreUnknownKeys = true }.decodeFromString<ApiResponse>(response!!)
        return apiResponse.results
    }
}
