package pcg.curso.realtimebasico

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager.LayoutParams
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import pcg.curso.realtimebasico.data.FirebaseInstance
import pcg.curso.realtimebasico.databinding.ActivityMainBinding
import pcg.curso.realtimebasico.databinding.DialogAddTaskBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseInstance: FirebaseInstance
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)
        setUI()
        setUpListeners()
    }

    private fun setUI() {
        /*
        binding.btnUpdate.setOnClickListener{
            firebaseInstance.writeOnFirebase()
        }
        */

        todoAdapter = TodoAdapter { action, reference ->
            when (action) {
                Actions.DELETE -> firebaseInstance.removeFromDatabase(reference)
                Actions.DONE -> firebaseInstance.updateFromDatabase(reference)
            }

        }
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnAddTask -> {
                //firebaseInstance.writeOnFirebase()
                showDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        val binding = DialogAddTaskBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(binding.root)

        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        binding.btnAddTask.setOnClickListener {

            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Rellena los campos.", Toast.LENGTH_SHORT).show()
            } else {
                firebaseInstance.writeOnFirebase(title, description)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun setUpListeners() {

        val postListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                /*val data  = snapshot.getValue<String>()
                if(data != null){
                    binding.tvResult.text = data
                }*/

                val data = getCleanSnapshot(snapshot)
                todoAdapter.setNewList(data)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("onCancelled", error.details)
            }
        }

        firebaseInstance.setUpDatabaseListener(postListener)

    }

    private fun getCleanSnapshot(snapshot: DataSnapshot): List<Pair<String, Todo>> {
        val list = snapshot.children.map { item ->
            Pair(item.key!!, item.getValue(Todo::class.java)!!)
        }
        return list
    }


}