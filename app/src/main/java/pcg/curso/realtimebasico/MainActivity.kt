package pcg.curso.realtimebasico

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import pcg.curso.realtimebasico.data.FirebaseInstance
import pcg.curso.realtimebasico.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var firebaseInstance: FirebaseInstance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)
        setUI()
        setUpListeners()
    }

    private fun setUpListeners() {

        val postListener = object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val data  = snapshot.getValue<String>()

                if(data != null){
                    binding.tvResult.text = data
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("onCancelled", error.details)
            }
        }

        firebaseInstance.setUpDatabaseListener(postListener)

    }

    private fun setUI() {
        binding.btnUpdate.setOnClickListener{
            firebaseInstance.writeOnFirebase()
        }
    }


}