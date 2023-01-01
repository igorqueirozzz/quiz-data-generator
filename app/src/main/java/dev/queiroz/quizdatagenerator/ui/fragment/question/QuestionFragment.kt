package dev.queiroz.quizdatagenerator.ui.fragment.question

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dev.queiroz.quizdatagenerator.activity.main.MainActivity
import dev.queiroz.quizdatagenerator.activity.viewmodel.QuizViewModel
import dev.queiroz.quizdatagenerator.data.entity.Answer
import dev.queiroz.quizdatagenerator.data.entity.AnswerList
import dev.queiroz.quizdatagenerator.databinding.FragmentQuestionBinding
import dev.queiroz.quizdatagenerator.ui.adapter.AnswerRecyclerViewAdapter
import dev.queiroz.quizdatagenerator.ui.adapter.OnItemUpdate
import dev.queiroz.quizdatagenerator.util.helper.SwipeToDeleteCallback

class QuestionFragment : Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var answerRecyclerView: RecyclerView
    private lateinit var answerEditText: EditText
    private lateinit var questionEditText: EditText
    private lateinit var sourceEditText: EditText
    private val adapter = AnswerRecyclerViewAdapter()
    private val quizViewModel: QuizViewModel by activityViewModels()
    private val args by navArgs<QuestionFragmentArgs>()
    private lateinit var takePictureQuestionTextLauncher: ActivityResultLauncher<CropImageContractOptions>
    private lateinit var takePictureSourceTextLauncher: ActivityResultLauncher<CropImageContractOptions>
    private lateinit var takePictureAnswerTextLauncher: ActivityResultLauncher<CropImageContractOptions>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).hideBottomBar()
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        binding.tieCategory.setText(quizViewModel.category.name)
        quizViewModel.setCurrentFragmentName(quizViewModel.category.name)
        answerRecyclerView = binding.answersRecycler
        answerEditText = binding.tieAnswer
        questionEditText = binding.tieQuestion
        sourceEditText = binding.tieSource

        takePictureQuestionTextLauncher = cropImageLauncherBuilder(questionEditText)
        takePictureSourceTextLauncher = cropImageLauncherBuilder(sourceEditText)
        takePictureAnswerTextLauncher = cropImageLauncherBuilder(answerEditText)

        checkArgs()
        setupRecyclerView()
        setupObservers()
        setupListeners()
        return binding.root
    }


    override fun onPause() {
        if (activity is MainActivity) {
            (activity as MainActivity).showBottomBar()
        }
        quizViewModel.clearAnswerList()
        super.onPause()
    }

    override fun onResume() {
        if (activity is MainActivity) {
            (activity as MainActivity).hideBottomBar()
        }
        super.onResume()
    }

    private fun checkArgs() {
        if (args.questionOnEdition != null) {
            with(args.questionOnEdition) {
                questionEditText.setText(this!!.description)
                sourceEditText.setText(this.source)
                this.answerList.answers.forEach { quizViewModel.addAnswer(it) }
                this.answerList.answers = mutableListOf()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter.setOnItemUpdateListener(object : OnItemUpdate {
            override fun onUpdated(answer: Answer) {
                quizViewModel.updateAnswerCorrect(answer)
            }

        })
        answerRecyclerView.setHasFixedSize(true)
        answerRecyclerView.layoutManager = LinearLayoutManager(context)
        answerRecyclerView.adapter = adapter

        val swipeToDelete = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val answer = adapter.getItem(position)
                quizViewModel.removeAnswerFromAnswersCreateList(answer)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(answerRecyclerView)
    }


    private fun setupObservers() {
        quizViewModel.answers.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun setupListeners() {
        binding.run {
            btnAddAnswer.setOnClickListener { addAnswer() }
            fabSaveQuestion.setOnClickListener { if (args.questionOnEdition == null) addQuestion() else updateQuestion() }
            tilQuestion.setEndIconOnClickListener {
                startCropImageLauncher(takePictureQuestionTextLauncher)
            }
            tilSource.setEndIconOnClickListener {
                startCropImageLauncher(takePictureSourceTextLauncher)
            }
            tilAnswer.setEndIconOnClickListener {
                startCropImageLauncher(takePictureAnswerTextLauncher)
            }
        }
    }

    private fun addQuestion() {
        if (validateAddQuestion()) {
            quizViewModel.addQuestion(
                questionText = questionEditText.text.toString(),
                source = sourceEditText.text.toString()
            )
            findNavController().navigateUp()
        }
    }

    private fun updateQuestion() {
        if (validateAddQuestion()) {
            val question = args.questionOnEdition!!
            question.description = questionEditText.text.toString()
            question.source = sourceEditText.text.toString()
            question.answerList = AnswerList(quizViewModel.answers.value!!.toList())
            quizViewModel.updateQuestion(question)
            findNavController().navigateUp()
        }
    }

    private fun addAnswer() {
        if (validateAddAnswer()) {
            quizViewModel.addAnswer(answer = answerEditText.text.toString())
            answerEditText.text = null

        }
    }

    private fun cropImageLauncherBuilder(editText: EditText) = registerForActivityResult(CropImageContract()){ result ->
        if(result.isSuccessful){
            val uri = result.uriContent
            val imageBimap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            getTextFromBitmapSetOnField(editText = editText, bitmap = imageBimap)
        }
    }

    private fun startCropImageLauncher(cropImageLauncher: ActivityResultLauncher<CropImageContractOptions>) {
        val options = CropImageContractOptions(uri = null, cropImageOptions = CropImageOptions())
        options.cropImageOptions.imageSourceIncludeCamera = true
        options.cropImageOptions.imageSourceIncludeGallery = true
        options.cropImageOptions.guidelines = CropImageView.Guidelines.ON
        cropImageLauncher.launch(options)
    }


    private fun getTextFromBitmapSetOnField(editText: EditText, bitmap: Bitmap?) {
        val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = bitmap?.let { InputImage.fromBitmap(it, 0) }
        val stringBuilder = StringBuilder()
        if (image != null) {
            textRecognizer.process(image).addOnSuccessListener {
                for (block in it.textBlocks) {
                    stringBuilder.append(block.text)
                }
                editText.setText(stringBuilder.toString())
            }.addOnFailureListener {
                Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Image not recognized, try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateAddQuestion(): Boolean {
        if (questionEditText.text.toString().isBlank()) {
            Toast.makeText(context, "Question can't be empty.", Toast.LENGTH_SHORT).show()
            return false
        } else if (quizViewModel.answers.value?.isEmpty() == true) {
            Toast.makeText(context, "Answers list can't be empty.", Toast.LENGTH_SHORT).show()
            return false
        } else if (quizViewModel.answers.value!!.count { it.isCorrect } != 1) {
            Toast.makeText(
                context,
                "You must to provide just one correct answer.",
                Toast.LENGTH_SHORT
            )
                .show()
            return false
        }
        return true
    }

    private fun validateAddAnswer(): Boolean {
        if (answerEditText.text.toString().isBlank()) {
            Toast.makeText(context, "Answer can't be empty.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
