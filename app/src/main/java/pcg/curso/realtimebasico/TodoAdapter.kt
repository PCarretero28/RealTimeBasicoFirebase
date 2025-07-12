package pcg.curso.realtimebasico

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TodoAdapter(
    private var todoList: List<Pair<String, Todo>> = emptyList(),
    private val onItemSelected: (String) -> Unit
) :
    RecyclerView.Adapter<TodoViewHolder>() {

    fun setNewList(newList: List<Pair<String, Todo>>) {
        todoList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun getItemCount() = todoList.count()

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position], onItemSelected)
    }


}