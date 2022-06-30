package com.aodmrz.guessthenumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aodmrz.guessthenumber.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Getting the result argument
        val result = arguments?.getBoolean("result").toString()

        binding.apply {

            //Getting the number argument
            numberTextView.text = arguments?.getInt("number").toString()

            //Result check
            if (result == "true"){

                //If the player wins
                resultTextView.text = getString(R.string.win)
                resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.true_icon, 0, 0)
            } else {

                //If the player loses
                resultTextView.text = getString(R.string.lose)
                resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.false_icon,0,0)
            }

            //Replay button activity
            replayButton.setOnClickListener {

                //Navigate to the GuessFragment
                findNavController().navigate(ResultFragmentDirections.resultToGuess())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}