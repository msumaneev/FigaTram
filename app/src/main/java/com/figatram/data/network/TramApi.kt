package com.figatram.data.network

import com.figatram.data.network.dto.TramResponse
import retrofit2.http.GET

interface TramApi {
    @GET("vehicles") // Adjust endpoint as needed
    suspend fun getTrams(): TramResponse
}
