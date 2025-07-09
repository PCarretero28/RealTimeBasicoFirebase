package pcg.curso.realtimebasico

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    }

    private fun setUI() {
        binding.btnUpdate.setOnClickListener{
            firebaseInstance.writeOnFirebase()
        }
    }


}