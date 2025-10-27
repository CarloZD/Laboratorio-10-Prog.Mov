package com.example.semana09_servicios.data.remote;

import com.example.semana09_servicios.data.model.User
import retrofit2.http.GET

public interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
