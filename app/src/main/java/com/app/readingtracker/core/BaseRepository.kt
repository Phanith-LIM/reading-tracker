package com.app.readingtracker.core

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

open class BaseRepository {
    private val baseUrl = "https://reading-tracker-api-om3dbxgmuq-as.a.run.app/"
    private var _header: Map<String, String> = hashMapOf()

    fun setHeader(token: String) {
        _header = mapOf("Authorization" to "Bearer $token")
    }

    suspend fun get(path: String): String {
        return suspendCancellableCoroutine { continuation ->
            Fuel.get(baseUrl + path).header(_header).responseJson { _, _, result ->
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
        return suspendCancellableCoroutine { continuation ->
            Fuel.post(baseUrl + path)
                .header(_header)
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
