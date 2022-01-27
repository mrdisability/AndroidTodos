package com.lkdigital.androidtodos.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lkdigital.androidtodos.network.ApiClient
import com.lkdigital.androidtodos.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    private val TAG = "HomeRepository"

    private var apiInterface: ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun fetchAllTodos(): MutableLiveData<List<Todo>> {
        val data = MutableLiveData<List<Todo>>()

        apiInterface?.fetchAllTodos()?.enqueue(object : Callback<List<Todo>>{

            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<Todo>>,
                response: Response<List<Todo>>
            ) {

                val res = response.body()

                Log.d(TAG, res.toString())

                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }

            }
        })

        return data

    }

    fun createTodo(todo: Todo): MutableLiveData<Todo> {
        val data = MutableLiveData<Todo>()

        apiInterface?.createTodo(todo)?.enqueue(object : Callback<Todo>{
            override fun onFailure(call: Call<Todo>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                val res = response.body()
                if (response.code() == 201 && res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })

        return data

    }

    fun deleteTodo(id:Int):LiveData<Boolean>{
        val data = MutableLiveData<Boolean>()

        apiInterface?.deleteTodo(id)?.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value = false
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.code() == 200
            }
        })

        return data

    }
}