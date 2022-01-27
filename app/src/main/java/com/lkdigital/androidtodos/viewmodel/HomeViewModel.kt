package com.lkdigital.androidtodos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lkdigital.androidtodos.data.HomeRepository
import com.lkdigital.androidtodos.data.Todo

class HomeViewModel(application: Application): AndroidViewModel(application){

    private var homeRepository: HomeRepository?=null
    var todoListLiveData: LiveData<List<Todo>>?=null
    var createTodoLiveData: LiveData<Todo>?=null
    var deleteTodoLiveData: LiveData<Boolean>?=null

    init {
        homeRepository = HomeRepository()
        todoListLiveData = MutableLiveData()
        createTodoLiveData = MutableLiveData()
        deleteTodoLiveData = MutableLiveData()
    }

    fun fetchAllTodos(){
        todoListLiveData = homeRepository?.fetchAllTodos()
    }

    fun createTodo(todo: Todo){
        createTodoLiveData = homeRepository?.createTodo(todo)
    }

    fun deleteTodo(id:Int){
        deleteTodoLiveData = homeRepository?.deleteTodo(id)
    }

}