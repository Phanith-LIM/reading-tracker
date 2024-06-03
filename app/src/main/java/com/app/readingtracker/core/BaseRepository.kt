package com.app.readingtracker.core

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

open class BaseRepository {
    private val baseUrl = "https://reading-tracking-api-99b58363a3cb.herokuapp.com/"
    private val header: HashMap<String, String> = hashMapOf()

    suspend fun get(path: String): String {
        return suspendCancellableCoroutine { continuation ->
            Fuel.get(baseUrl + path).header(header).responseJson { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.d("com.app.readingtracker.core.BaseRepository", "Error fetching data: ${result.getException()}")
                        continuation.resume("")
                    }
                    is Result.Success -> {
                        val data = result.get().content
                        continuation.resume(data)
                    }
                }
            }
        }
    }

    suspend fun post(path: String, body: Any): String {
        Log.d("BaseRepository", body.toString())
        return suspendCancellableCoroutine { continuation ->
            Fuel.post(baseUrl + path)
                .header(header)
                .jsonBody(body.toString())
                .responseJson { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            Log.d("BaseRepository", "Error posting data: ${result.getException()}")
                            continuation.resume("")
                        }
                        is Result.Success -> {
                            val data = result.get().content
                            continuation.resume(data)
                        }
                    }
                }
        }
    }
}
