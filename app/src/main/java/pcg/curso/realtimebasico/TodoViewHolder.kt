package pcg.curso.realtimebasico

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pcg.curso.realtimebasico.Actions.*
import pcg.curso.realtimebasico.databinding.ItemTodoBinding

class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTodoBinding.bind(view)

    fun bind(todoTask: Pair<String, Todo>, onItemSelected: (Actions, String) -> Unit) {
        binding.tvTitle.text = todoTask.second.title
        binding.tvDescription.text = todoTask.second.description
        binding.tvReference.text = todoTask.first
        binding.ivDelete.setOnClickListener { onItemSelected(DELETE, todoTask.first) }
        binding.ivDone.setOnClickListener { onItemSelected(DONE, todoTask.first) }
        val color = if (todoTask.second.done == true) {
            R.color.gold
        } else {
            R.color.purple
        }

        binding.cvItem.strokeColor = ContextCompat.getColor(binding.cvItem.context, color)
    }

}