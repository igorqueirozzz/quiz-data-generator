package dev.queiroz.quizdatagenerator.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.databinding.ActivityMainBinding
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)


        val navView: BottomNavigationView = binding.navView

        navView.background = null

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)
        setClickListeners()
    }

    private fun setClickListeners(){
        binding.fabAddQuiz.setOnClickListener{
            startActivity(Intent(this, NewQuizActivity::class.java))
        }
    }
}