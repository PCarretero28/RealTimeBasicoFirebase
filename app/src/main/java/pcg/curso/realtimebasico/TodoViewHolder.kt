package pcg.curso.realtimebasico

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pcg.curso.realtimebasico.databinding.ItemTodoBinding

class TodoViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemTodoBinding.bind(view)

    fun bind(todoTask: Pair<String, Todo>) {
        binding.tvTitle.text = todoTask.second.title
        binding.tvDescription.text = todoTask.second.description
    }

}