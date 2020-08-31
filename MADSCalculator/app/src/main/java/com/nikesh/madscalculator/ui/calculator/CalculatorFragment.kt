package com.nikesh.madscalculator.ui.calculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nikesh.madscalculator.R
import kotlinx.android.synthetic.main.fragment_calculator.*

class CalculatorFragment : Fragment(), View.OnClickListener, TextView.OnEditorActionListener {

    private lateinit var calcViewModel: CalcViewModel
    private lateinit var mContext: Context

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

        calcViewModel = ViewModelProvider(this, CalcViewModelFactory())
            .get(CalcViewModel::class.java)

        calc_ans.setOnClickListener(this)
        calc_history.setOnClickListener(this)
        calc_clear.setOnClickListener(this)
        calc_multiply.setOnClickListener(this)
        calc_add.setOnClickListener(this)
        calc_divide.setOnClickListener(this)
        calc_subtract.setOnClickListener(this)

        edit_query.setOnEditorActionListener(this)

        calcViewModel.prevResult.observe(viewLifecycleOwner, Observer {
            edit_query.setText(it.toString())
            edit_query.text?.lastIndex?.let { it1 -> edit_query.setSelection(it1 + 1) }
        })

        calcViewModel.historyList.observe(viewLifecycleOwner, Observer {
            if(it.isNullOrEmpty()){
                return@Observer
            }
            showHistoryList(it)
        })
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
                    getHistoryListFromFB()
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

    private fun getHistoryListFromFB() {
        calcViewModel.fetchFromFireStore()
    }

    private fun showHistoryList(result: ArrayList<String>) {
        calc_history_text.text = ""
        result.asReversed().forEach {
            calc_history_text.text = "${calc_history_text.text}\n$it"
        }
    }

    private fun onAnsClick(view: View) {
        calcViewModel.calculate(edit_query.text.toString())
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