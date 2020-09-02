package com.nikesh.madscalculator.ui.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalcViewModel(private val calcRepository: CalcRepository) : ViewModel() {

    private val _result = MutableLiveData<Double>()
    val prevResult: LiveData<Double> = _result

    private val _historyList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val historyList: LiveData<ArrayList<String>> = _historyList

    // without type annotation in lambda expression
    val addition: (Double, Double) -> Double = { a, b -> a + b }
    val subtraction: (Double, Double) -> Double = { a, b -> a - b }
    val multiplication: (Double, Double) -> Double = { a, b -> a * b }
    val division: (Double, Double) -> Double = { a, b -> a / b }


    private fun performOperation(query: String) {
        //Default ans
        var ans = 0.0
        //Replace spaces if any
        val str = query.replace("\\s".toRegex(), "")

        // Init array
        val dividedArray: ArrayList<String> = ArrayList(0)
        dividedArray.addAll(str.split("(?<=[-+*/])|(?=[-+*/])".toRegex()))

        //Safety check
        if (dividedArray.isNullOrEmpty()) {
            Log.e("TAG", "performOperation: empty array")
            return
        }

        while (dividedArray.size > 1) {
            var index = -1

            // Check all operations as per MADS priority
            // If multiply present
            if (dividedArray.contains("*") && dividedArray.size >= 3) {
                index = dividedArray.indexOf("*")
                if (index > 0) {
                    ans = multiplication(
                        dividedArray[index - 1].toDouble(),
                        dividedArray[index + 1].toDouble()
                    )
                    dividedArray[index - 1] = ans.toString()
                    dividedArray.removeAt(index + 1)
                    dividedArray.removeAt(index)
                }
                // For Addition
            } else if (dividedArray.contains("+") && dividedArray.size >= 3) {
                index = dividedArray.indexOf("+")
                if (index > 0) {
                    ans = addition(dividedArray[index - 1].toDouble(), dividedArray[index + 1].toDouble())
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
                        Log.e(Companion.TAG, "performOperation: Divide by 0 error")
                        return
                    }

                    ans = division(dividedArray[index - 1].toDouble(), dividedArray[index + 1].toDouble())
                    dividedArray[index - 1] = ans.toString()
                    dividedArray.removeAt(index + 1)
                    dividedArray.removeAt(index)
                }
                // For subtraction
            } else if (dividedArray.contains("-") && dividedArray.size >= 3) {
                index = dividedArray.indexOf("-")
                if (index > 0) {
                    ans = subtraction(
                        dividedArray[index - 1].toDouble(),
                        dividedArray[index + 1].toDouble()
                    )
                    dividedArray[index - 1] = ans.toString()
                    dividedArray.removeAt(index + 1)
                    dividedArray.removeAt(index)
                }
            }
        }

        _result.value = ans
    }

    fun calculate(expression: String) {

        performOperation(expression)

        // Store as soon as it performs operation.
        calcRepository.storeInFirebaseFireStore("$expression = ${prevResult.value}")
    }

    fun fetchFromFireStore() {
        CoroutineScope(Dispatchers.Main).launch {
            _historyList.value = calcRepository.getHistoryListFromFireStore()
        }
    }

    companion object {
        private const val TAG = "CalcViewModel"
    }
}
