package com.aodmrz.guessthenumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aodmrz.guessthenumber.databinding.FragmentMainPageBinding


class MainPageFragment : Fragment() {

    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainPageBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Play button click activity
        binding.playButton.setOnClickListener {

            //Navigate to the GuessFragment
            findNavController().navigate(MainPageFragmentDirections.mainToGuess())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}