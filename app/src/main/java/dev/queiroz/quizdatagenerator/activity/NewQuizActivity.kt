package dev.queiroz.quizdatagenerator.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.queiroz.quizdatagenerator.MyApplication
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.databinding.ActivityNewQuizBinding
import kotlinx.android.synthetic.main.activity_new_quiz.*
import javax.inject.Inject
@AndroidEntryPoint
class NewQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewQuizBinding
    private lateinit var navController: NavController
    private var categoryCount: Int = 0

    private val newQuizViewModel by viewModels<NewQuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_new_quiz_step1,
                R.id.navigation_new_quiz_step2
            )
        )
        binding = ActivityNewQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.newQuizToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)

        navController = findNavController(R.id.nav_host_new_quiz)

        setupActionBarWithNavController(navController, appBarConfiguration)
        setBackAndNextButtonObserversAndListeners()
        setDataListeners()
    }

    private fun setBackAndNextButtonObserversAndListeners() {
        newQuizViewModel.stepper.observe(this) {
            when (it) {
                0 -> {
                    binding.run {
                        fabNextQuizStep.run {
                            text = getString(R.string.next)
                            icon = AppCompatResources.getDrawable(
                                applicationContext,
                                R.drawable.ic_arrow_right
                            )
                            setOnClickListener {
                                if (canItGoNextStep()) {
                                    newQuizViewModel.nextStep()
                                    navController.navigate(R.id.action_navigation_from_quiz_step_1_to_2)
                                }
                            }
                        }

                        fabBackQuizStep.run {
                            text = getString(R.string.cancel)
                            icon = null
                            setOnClickListener {
                                finish()
                            }
                        }
                    }

                    quiz_stepper_progress.progress = 0

                }
                1 -> {

                    binding.run {
                        fabNextQuizStep.run {
                            text = getString(R.string.save_quiz)
                            icon = null
                            setOnClickListener {
                                Toast.makeText(applicationContext, "TODO SAVE", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        fabBackQuizStep.run {
                            text = getString(R.string.back)
                            icon = AppCompatResources.getDrawable(
                                applicationContext,
                                R.drawable.ic_arrow_left
                            )
                            setOnClickListener {
                                navController.popBackStack()
                                newQuizViewModel.backStep()
                            }
                        }
                    }

                    quiz_stepper_progress.progress = 50

                }
            }
        }
    }

    private fun setDataListeners() {
        newQuizViewModel.categories.observe(this){
            categoryCount = it.size
        }
    }

    private fun canItGoNextStep(): Boolean {
        val canGo = categoryCount > 0
        if (!canGo) {
            Toast.makeText(applicationContext, "You must to add some category.", Toast.LENGTH_SHORT)
                .show()
        }
        return canGo
    }
}