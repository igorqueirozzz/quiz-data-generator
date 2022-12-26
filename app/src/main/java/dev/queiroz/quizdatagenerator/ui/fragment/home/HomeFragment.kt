package dev.queiroz.quizdatagenerator.ui.fragment.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.BuildConfig
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.viewmodel.QuizViewModel
import dev.queiroz.quizdatagenerator.databinding.FragmentHomeBinding
import dev.queiroz.quizdatagenerator.ui.adapter.QuizRecyclerViewAdapter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val quizViewModel: QuizViewModel by activityViewModels()
    private lateinit var quizRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        quizRecyclerView = binding.root.findViewById(R.id.recycler_quiz)

        setupRecyclerView()
        setupListeners()
        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = QuizRecyclerViewAdapter(object: QuizRecyclerViewAdapter.OptionMenuClickListener{
            override fun onOptionMenuClick(position: Int) {
                performOptionsMenuClick(position)
            }
        } )
        quizRecyclerView.layoutManager = LinearLayoutManager(context)
        quizRecyclerView.adapter = adapter
        quizRecyclerView.setHasFixedSize(true)
        quizViewModel.quizList.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                binding.tvHomeFragment.visibility = View.GONE
            }
           adapter.setData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners(){
        quizViewModel.run {
            jsonObject.observe(viewLifecycleOwner){ exportJson(it) }
        }
    }

    private fun exportJson(json: String){
        val file = File.createTempFile("generated", "quiz_data.json")
        val fileStream = FileOutputStream(file)

        fileStream.write(json.toByteArray())
        fileStream.flush()
        fileStream.close()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/json"
        val uri = FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", file)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        requireContext().startActivity(Intent.createChooser(intent, "Share quiz data"))
    }
    private fun performOptionsMenuClick(position: Int){
        val popUpMenu = PopupMenu(context, binding.recyclerQuiz[position].findViewById(R.id.tv_options))
        popUpMenu.inflate(R.menu.export_menu)

        popUpMenu.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.export_as_json -> {
                    Toast.makeText(context, "Exporting as Json...", Toast.LENGTH_SHORT).show()
                    quizViewModel.exportQuizAsJsonFromList(position)
                }
            }
            true
        }
        popUpMenu.show()
    }
}