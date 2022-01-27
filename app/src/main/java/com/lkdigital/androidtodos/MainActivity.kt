package com.lkdigital.androidtodos

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lkdigital.androidtodos.data.Todo
import com.lkdigital.androidtodos.ui.HomeAdapter
import com.lkdigital.androidtodos.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity(), HomeAdapter.HomeListener {
    private lateinit var vm: HomeViewModel
    private lateinit var adapter: HomeAdapter

    private var rv_home: RecyclerView? = null
    private var progress_home: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_home = findViewById<RecyclerView>(R.id.rv_home)
        progress_home = findViewById<ProgressBar>(R.id.progress_home)

        vm = ViewModelProvider(this)[HomeViewModel::class.java]

        initAdapter()

        vm.fetchAllTodos()

        vm.todoListLiveData?.observe(this, Observer {
            if (it!=null){
                rv_home?.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<Todo>)
            }else{
                showToast("Something went wrong")
            }
            progress_home?.visibility = View.GONE
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_create_todo -> showCreateTodoDialog()
        }
        return true
    }

    private fun showCreateTodoDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.create_todo_dialog, null)
        dialog.setContentView(view)

        var todo = ""

        val btn_submit = view.findViewById<Button>(R.id.btn_submit)
        val et_todo = view.findViewById<EditText>(R.id.et_todo)

        btn_submit.setOnClickListener {
            todo = et_todo.text.toString().trim()

            if (title.isNotEmpty()){
                val newTodo = Todo()
                newTodo.todo = todo
                newTodo.completed = false

                vm.createTodo(newTodo)

                vm.createTodoLiveData?.observe(this, Observer {
                    if (it!=null){
                        adapter.addData(newTodo)
                        rv_home?.smoothScrollToPosition(0)
                    }else{
                        showToast("Cannot create todo at the moment")
                    }
                    dialog.cancel()
                })

            }else{
                showToast("Please fill data carefully!")
            }

        }

        dialog.show()

        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

    }

    private fun initAdapter() {
        adapter = HomeAdapter(this)
        rv_home?.layoutManager = LinearLayoutManager(this)
        rv_home?.adapter = adapter
    }

    override fun onItemDeleted(todo: Todo, position: Int) {
        todo.id?.let { vm.deleteTodo(it) }
        vm.deleteTodoLiveData?.observe(this, Observer {
            if (it!=null){
                adapter.removeData(position)
            }else{
                showToast("Cannot delete todo at the moment!")
            }
        })

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}