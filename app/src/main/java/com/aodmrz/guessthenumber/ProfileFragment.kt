package com.aodmrz.guessthenumber

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.aodmrz.guessthenumber.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = this.activity?.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        //Getting values from SharedPrefs
        var nameEdit: String = preferences?.getString(EDITED_NAME,getString(R.string.player)).toString()
        val highScore: Int = preferences?.getInt(HIGH_SCORE,10)!!.toInt()



        binding.apply {

            nameTextView.text = nameEdit
            highScoreTextView.text = String.format(getString(R.string.highScore), highScore)

            //Change name button click activity
            changeNameButton.setOnClickListener {

                //Making change name views visible
                nameEditText.visibility = View.VISIBLE
                saveNameButton.visibility = View.VISIBLE
            }

            //Save name button click activity
            saveNameButton.setOnClickListener {

                //Change name activity
                nameEdit = nameEditText.text.toString()
                nameTextView.text = nameEdit

                //Setting the name value to SharedPrefs
                editor?.putString(EDITED_NAME, nameEdit)
                editor?.apply()

                //Hide the keyboard
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(nameEditText.windowToken, 0)


                //Making change name views invisible
                nameEditText.visibility = View.GONE
                saveNameButton.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
