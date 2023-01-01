package dev.queiroz.quizdatagenerator.activity.main

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.viewmodel.QuizViewModel
import dev.queiroz.quizdatagenerator.data.entity.Category
import dev.queiroz.quizdatagenerator.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private val REQUEST_CAMERA_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)


        val navView: BottomNavigationView = binding.navView

        navView.background = null

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        setupActionBarWithNavController(navController)
        navView.setupWithNavController(navController)

        setClickListeners()
        setObservers()
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_CODE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp() || super.onSupportNavigateUp()
    }

    private fun setClickListeners() {
        binding.fabAddQuiz.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            when (quizViewModel.currentFragmentName.value) {
                "Home" -> {
                    val input = EditText(this)
                    alert.setView(input)
                    alert.setTitle(getString(R.string.new_quiz))
                    alert.setMessage(getString(R.string.type_new_quiz_name))
                    alert.setPositiveButton("Ok") { _, _ ->
                        if (validateInput(input)) {
                            quizViewModel.addQuiz(input.text.toString())
                        }
                    }
                    alert.show()
                }
                quizViewModel.quiz.name -> {
                    val layout = LinearLayout(applicationContext)
                    val input = EditText(this)
                    input.hint = "Category Name"
                    val input2 = EditText(this)
                    input2.hint = "Icon Name"
                    layout.orientation = LinearLayout.VERTICAL
                    layout.addView(input)
                    layout.addView(input2)
                    alert.setTitle("New Category")
                    alert.setView(layout)

                    alert.setPositiveButton("Ok") { _, _ ->
                        if (validateInput(input)) {
                            quizViewModel.addCategory(
                                Category(
                                    name = input.text.toString(),
                                    icon = input2.text.toString(),
                                    quizId = quizViewModel.quiz.id
                                )
                            )
                        }
                    }
                    alert.show()
                }
                quizViewModel.category.name -> {
                    findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_categoryFragment_to_questionFragment)
                    hideBottomBar()
                }
            }

        }
    }


    private fun validateInput(input: EditText): Boolean {
        return !input.text.isNullOrBlank()
    }

    private fun setObservers() {
        quizViewModel.currentFragmentName.observe(this) {
            supportActionBar?.title = it
        }
    }
     fun hideBottomBar(){
        binding.bottomAppBar.visibility = View.GONE
        binding.fabAddQuiz.visibility = View.GONE
    }

     fun showBottomBar(){
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.fabAddQuiz.visibility = View.VISIBLE
    }

}