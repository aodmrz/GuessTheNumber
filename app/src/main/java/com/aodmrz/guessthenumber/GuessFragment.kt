package com.aodmrz.guessthenumber

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.aodmrz.guessthenumber.databinding.FragmentGuessBinding

class GuessFragment : Fragment() {

    private var _binding:FragmentGuessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGuessBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Definition of the player's life
        var life = 10
        //Selection of target number
        val number = (1..100).random()


        binding.apply {
            //decrease5Button click activity
            decrease1Button.setOnClickListener {

                //Subtraction of 1 from the number
                updateNumber(guessEditText, -1)
                guessEditText.hideKeyboard()
            }

            //increase5Button click activity
            increase1Button.setOnClickListener {

                //Addition of 1 to the number
                updateNumber(guessEditText, 1)
                guessEditText.hideKeyboard()
            }

            //decrease5Button click activity
            decrease5Button.setOnClickListener {

                //Subtraction of 5 from the number
                updateNumber(guessEditText, -5)
                guessEditText.hideKeyboard()
            }

            //increase5Button click activity
            increase5Button.setOnClickListener {

                //Addition of 5 to the number
                updateNumber(guessEditText, 5)
                guessEditText.hideKeyboard()
            }

        }

        //Send button click activity
        binding.sendButton.setOnClickListener {

            binding.guessEditText.hideKeyboard()
            //Getting guessed number in EditText
            val guessStr = binding.guessEditText.text.toString()

            //Empty EditText check
            if (guessStr != "" && (guessStr.toInt() > 100 || guessStr.toInt() < 0)) {

                //Show number out of range warning
                Toast.makeText(context, getString(R.string.numberWarning), Toast.LENGTH_SHORT).show()

            }  else if(guessStr == "") {

                //Show take a guess warning
                Toast.makeText(context, getString(R.string.guessWarning), Toast.LENGTH_SHORT).show()
            } else {

                //Reduction of player's life
                life -= 1
                binding.lifeTextview.text = life.toString()

                //Conversion of guessed number to integer
                val guess = guessStr.toInt()

                //Comparison of guessed number and target number
                if(life == 0 && guess != number) {

                    //If the player runs out of life
                    //Navigate to the ResultFragment with result = false
                    val send = GuessFragmentDirections.guessToResult(false,number)
                    findNavController().navigate(send)

                } else if (guess > number){
                    binding.apply {

                        //Making decreaseViews visible and increaseViews invisible
                        changeVisibility(decreaseImageView,decreaseTextView)
                        changeVisibility(increaseImageView,increaseTextView,false)
                    }

                } else if (guess < number){
                    binding.apply {

                        //Making increaseViews visible and decreaseViews invisible
                        changeVisibility(increaseImageView,increaseTextView)
                        changeVisibility(decreaseImageView,decreaseTextView,false)
                    }

                } else if (guess == number){

                    binding.apply {

                        //Making decreaseViews invisible and increaseViews invisible
                        changeVisibility(decreaseImageView,decreaseTextView,false)
                        changeVisibility(increaseImageView,increaseTextView,false)
                    }

                    //If the player wins the game
                    //Setting the high score to SharedPrefs
                    val preferences = this.activity?.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
                    val editor = preferences?.edit()
                    val highScore: Int = preferences?.getInt(HIGH_SCORE,10)!!.toInt()
                    if ((10-life) < highScore) {
                        editor?.putInt(HIGH_SCORE, (10 - life))
                        editor?.apply()
                    }

                    //Navigate to the ResultFragment with result = true
                    val send = GuessFragmentDirections.guessToResult(true,number)
                    findNavController().navigate(send)
                }
            }
        }

    }

    private fun updateNumber(number: EditText, delta: Int){
        /**
         * This function takes a number as an EditText and adds the delta number to that number.
         * Also keeps the number stay between 0 and 100 even though increasing and decreasing of the number.
         *
         * @param number The number you want to change
         * @param delta The number you want to add
         *
         * */

        val numberStr: String = number.text.toString()
        if (numberStr != ""){

            var numberInt = numberStr.toInt()

            if (numberInt in (-1*delta)..(100-delta)){
                numberInt += delta
            } else if (numberInt < -1*delta){
                numberInt = 0
            } else if (numberInt > 100-delta){
                numberInt = 100
            }

            number.setText(numberInt.toString())
        } else {
            if (delta <= -1) {
                number.setText("0")
            } else {
                number.setText(R.string.hundred)
            }
        }
    }

    private fun changeVisibility(image: ImageView, text: TextView, visible: Boolean = true){
        /**
         * This function takes an ImageView and a TextView. Then it toggles their visibility.
         *
         * @param image The image whose visibility you want to change
         * @param text The text whose visibility you want to change
         * @param visible desired visibility state
         *
         * */

        if (visible){
            //Making decreaseViews visible
            image.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
        } else {
            //Making increaseViews invisible
            if (image.isVisible) {
                image.visibility = View.INVISIBLE
                text.visibility = View.INVISIBLE
            }
        }

    }

    private fun View.hideKeyboard(){
        /**
         * This method hides the keyboard
         * */
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
