package pcg.curso.realtimebasico.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pcg.curso.realtimebasico.Todo
import kotlin.random.Random

class FirebaseInstance(context: Context) {

    private val database = Firebase.database
    private val myRef = database.reference

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase(title:String, description:String) {
        //val randomValue = Random.nextInt(1, 100).toString()
        //myRef.setValue("Mi primera escritura: $randomValue")
        val newItem = myRef.push()
        newItem.setValue(Todo(title, description))
    }

    fun setUpDatabaseListener(postListener: ValueEventListener) {
        database.reference.addValueEventListener(postListener)
    }

    /*private fun getGenericTodoTaskItem(randomValue: String) =
        Todo(
            title = "Tarea $randomValue",
            description = "Prueba de descripción"
        )*/

    fun removeFromDatabase(reference: String) {
        myRef.child(reference).removeValue()
    }

    fun updateFromDatabase(reference: String) {
        myRef.child(reference).child("done").setValue(true)
    }

}