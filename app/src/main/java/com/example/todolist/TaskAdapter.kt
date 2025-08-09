package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskDoneListener: (Int) -> Unit,
    private val onDeleteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: TextView = itemView.findViewById(R.id.taskText)
        val isDoneCheckbox: CheckBox = itemView.findViewById(R.id.isDoneCheckbox)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskText.text = task.title

        // Set the checkbox state and strikethrough text based on isDone status
        holder.isDoneCheckbox.isChecked = task.isDone
        if (task.isDone) {
            holder.taskText.paintFlags = holder.taskText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.taskText.paintFlags = holder.taskText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Handle Mark as Done checkbox click
        holder.isDoneCheckbox.setOnClickListener {
            onTaskDoneListener.invoke(position)
        }

        // Handle Delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClickListener.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}