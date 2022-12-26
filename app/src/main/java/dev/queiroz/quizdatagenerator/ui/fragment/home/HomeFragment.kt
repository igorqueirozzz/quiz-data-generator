package dev.queiroz.quizdatagenerator.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.viewmodel.QuizViewModel
import dev.queiroz.quizdatagenerator.databinding.FragmentHomeBinding
import dev.queiroz.quizdatagenerator.ui.adapter.QuizRecyclerViewAdapter

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
        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = QuizRecyclerViewAdapter()
        quizRecyclerView.layoutManager = LinearLayoutManager(context)
        quizRecyclerView.adapter = adapter
        quizRecyclerView.setHasFixedSize(true)
        quizViewModel.quizList.observe(viewLifecycleOwner) {
           adapter.setData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}