package com.lkdigital.androidtodos.network

import com.lkdigital.androidtodos.data.Todo
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("todos")
    fun fetchAllTodos(): Call<List<Todo>>

    @POST("todos")
    fun createTodo(@Body todo: Todo):Call<Todo>

    @DELETE("todos/{id}")
    fun deleteTodo(@Path("id") id:Int):Call<String>
}