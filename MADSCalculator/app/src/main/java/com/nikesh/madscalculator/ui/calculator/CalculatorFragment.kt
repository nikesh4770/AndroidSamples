package com.nikesh.madscalculator.ui.calculator

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikesh.madscalculator.R
import kotlinx.android.synthetic.main.fragment_calculator.*
import java.lang.reflect.Type

class CalculatorFragment : Fragment(), View.OnClickListener, TextView.OnEditorActionListener {

    private lateinit var mContext: Context
    private var previousAns: Int = 0
    private var historyList: ArrayList<String> = ArrayList(10)

    // without type annotation in lambda expression
    val addition: (Int, Int) -> Int = { a, b -> a + b }
    val subtraction: (Int, Int) -> Int = { a, b -> a - b }
    val multiplication: (Int, Int) -> Int = { a, b -> a * b }
    val division: (Int, Int) -> Int = { a, b -> a / b }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calc_ans.setOnClickListener(this)
        calc_history.setOnClickListener(this)
        calc_clear.setOnClickListener(this)
        calc_multiply.setOnClickListener(this)
        calc_add.setOnClickListener(this)
        calc_divide.setOnClickListener(this)
        calc_subtract.setOnClickListener(this)

        edit_query.setOnEditorActionListener(this)
    }


    private fun performOperation(query: String): Int {
        //Default ans
        var ans = 0
        //Replace spaces if any
        val str = query.replace("\\s".toRegex(), "")

        // Init array
        val dividedArray: ArrayList<String> = ArrayList(0)
        dividedArray.addAll(str.split("(?<=[-+*/])|(?=[-+*/])".toRegex()))

        //Safety check
        if (dividedArray.isNullOrEmpty()) {
            Log.e(TAG, "performOperation: empty array")
            return ans
        }

        while (dividedArray.size > 1) {
            var index = -1

            // Check all operations as per MADS priority
            // If multiply present
            if (dividedArray.contains("*") && dividedArray.size >= 3) {
                index = dividedArray.indexOf("*")
                if (index > 0) {
                    ans = multiplication(
                        dividedArray[index - 1].toInt(),
                        dividedArray[index + 1].toInt()
                    )
                    dividedArray[index - 1] = ans.toString()
                    dividedArray.removeAt(index + 1)
                    dividedArray.removeAt(index)
                }
                // For Addition
            } else if (dividedArray.contains("+") && dividedArray.size >= 3) {
                index = dividedArray.indexOf("+")
                if (index > 0) {
                    ans = addition(dividedArray[index - 1].toInt(), dividedArray[index + 1].toInt())
                    dividedArray[index - 1] = ans.toString()
                    dividedArray.removeAt(index + 1)
                    dividedArray.removeAt(index)
                }
                // For Division
            } else if (dividedArray.contains("/") && dividedArray.size >= 3) {
                index = dividedArray.indexOf("/")
                if (index > 0) {

                    // Very Important Note in calculator
                    // Handling of divide by 0
                    if (dividedArray[index - 1] == "0" || dividedArray[index + 1] == "0") {
                        return 0
                    }

                    ans = division(dividedArray[index - 1].toInt(), dividedArray[index + 1].toInt())
                    dividedArray[index - 1] = ans.toString()
                    dividedArray.removeAt(index + 1)
                    dividedArray.removeAt(index)
                }
                // For subtraction
            } else if (dividedArray.contains("-") && dividedArray.size >= 3) {
                index = dividedArray.indexOf("-")
                if (index > 0) {
                    ans = subtraction(
                        dividedArray[index - 1].toInt(),
                        dividedArray[index + 1].toInt()
                    )
                    dividedArray[index - 1] = ans.toString()
                    dividedArray.removeAt(index + 1)
                    dividedArray.removeAt(index)
                }
            }
        }

        previousAns = ans
        return ans
    }


    override fun onClick(view: View?) {

        view?.let {
            when (view.id) {

                R.id.calc_ans -> {
                    onAnsClick(view)
                }
                R.id.calc_clear -> {
                    edit_query.setText("")
                }
                R.id.calc_history -> {

                    if (historyList.isNullOrEmpty()) {
                        val prefs = mContext.getSharedPreferences(mContext.packageName + ".user", MODE_PRIVATE)
                        val str = prefs.getString("HISTORY_LIST", null)
                        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
                        str?.let {
                            historyList = Gson().fromJson(str, type)
                        }
                    }
                    calc_history_text.text = ""
                    historyList.forEach {
                        calc_history_text.text = "${calc_history_text.text}\n$it"
                    }

                }
                R.id.calc_multiply -> {
                    edit_query.append(calc_multiply.text)
                }
                R.id.calc_add -> {
                    edit_query.append(calc_add.text)
                }
                R.id.calc_divide -> {
                    edit_query.append(calc_divide.text)
                }
                R.id.calc_subtract -> {
                    edit_query.append(calc_subtract.text)
                }
                else -> {
                }
            }
        }
    }

    private fun onAnsClick(view: View) {

        val expression = edit_query.text.toString()
        previousAns = performOperation(expression)
        edit_query.setText(previousAns.toString())
        edit_query.text?.lastIndex?.let { it1 -> edit_query.setSelection(it1 + 1) }

        historyList.add(0, "$expression = $previousAns")
        if (historyList.size > 10) {
            historyList.removeAt(10)
        }

        // For temporary purpose and list is small
        storeInSharedPreference()
    }


    private fun storeInSharedPreference() {
        val prefs = mContext.getSharedPreferences(mContext.packageName+ ".user", MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(historyList)
        val prefEditor = prefs.edit()
        prefEditor?.putString("HISTORY_LIST", json)
        prefEditor?.apply()
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        view?.let {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm =
                    view.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                onAnsClick(view)
                return true
            }
        }

        return false
    }

    companion object {
        private const val TAG = "CalculatorFragment"
    }
}