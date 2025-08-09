package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var taskList: MutableList<Task>
    private lateinit var adapter: TaskAdapter
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskList = loadTasks()

        adapter = TaskAdapter(
            taskList,
            onTaskDoneListener = { position ->
                taskList[position].isDone = !taskList[position].isDone
                saveTasks()
                adapter.notifyItemChanged(position)
            },
            onDeleteClickListener = { position ->
                taskList.removeAt(position)
                saveTasks()
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show()
            }
        )

        val recyclerView = findViewById<RecyclerView>(R.id.taskRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fab = findViewById(R.id.fabAddTask)
        fab.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val taskInput = dialogView.findViewById<EditText>(R.id.dialogTaskInput)
        val dialogAddButton = dialogView.findViewById<Button>(R.id.dialogAddButton)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        dialogAddButton.setOnClickListener {
            val taskTitle = taskInput.text.toString().trim()
            if (taskTitle.isNotEmpty()) {
                val task = Task(taskTitle)
                taskList.add(task)
                saveTasks()
                adapter.notifyItemInserted(taskList.size - 1)
                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Enter a task", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    private fun saveTasks() {
        val prefs = getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(taskList)
        editor.putString("tasks", json)
        editor.apply()
    }

    private fun loadTasks(): MutableList<Task> {
        val prefs = getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("tasks", null)
        val type = object : TypeToken<MutableList<Task>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }
}