package com.app.readingtracker.core

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.Method
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

    private suspend fun request(method: Method, path: String, body: Any? = null): String {
        return suspendCancellableCoroutine { continuation ->
            val request = when (method) {
                Method.GET -> Fuel.get(baseUrl + path)
                Method.POST -> Fuel.post(baseUrl + path).jsonBody(body.toString())
                Method.PUT -> Fuel.put(baseUrl + path).jsonBody(body.toString())
                else -> throw IllegalArgumentException("Unsupported method")
            }
            request.header(_header).responseJson { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.d("BaseRepository", "Error ${method.name} data: ${result.getException()}")
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

    suspend fun get(path: String): String {
        return request(Method.GET, path)
    }

    suspend fun post(path: String, body: Any): String {
        return request(Method.POST, path, body)
    }

    suspend fun put(path: String, body: Any): String {
        return request(Method.PUT, path, body)
    }

    suspend fun postFormData(path: String, body:  List<Pair<String, Any?>>?, file: FileDataPart? = null): String {
        return suspendCancellableCoroutine { continuation ->
            val request = Fuel.upload(path=baseUrl + path, method = Method.PUT, parameters = body)
            if(file != null) {
                request.add(file)
            }
            request.header(_header).responseJson { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.d("BaseRepository", "Error FormData: ${result.getException()}")
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

