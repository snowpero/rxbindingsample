package com.ninis.rxbindingrv

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import com.ninis.rxbindingrv.adapter.MainAdapter
import com.ninis.rxbindingrv.adapter.rx.*
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_component_items.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private lateinit var mainAdapter: MainAdapter
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_component_items)

//        initLayout()

        subscribeChangeEvent()
    }

    private fun genRandom(): Int {
        val min = 0
        val max = 999999

        val random = Random().nextInt(max - min) + min

        Log.d("NINIS", "genRandom] " + random)

        return random
    }

    private fun initLayout() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_main_list.layoutManager = layoutManager

        mainAdapter = MainAdapter(disposable)
        rv_main_list.adapter = mainAdapter
        rv_main_list.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(this)
                .color(Color.BLUE)
                .sizeResId(R.dimen.divider_height)
                .build()
        )
        getAdapterDataChanges().subscribe(object: Observer<MainAdapter?> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: MainAdapter) {
            }

            override fun onError(e: Throwable) {
            }
        })
    }

    private fun getAdapterDataChanges() : Observable<MainAdapter> {
        return RecyclerAdapterDataChangeObservable<MainAdapter>(mainAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun subscribeChangeEvent() {
        // EditText
        val editTextChange = EditTextChangeObservable(et_test)

        // RadioButton
        val radioBtnChange = RadioButtonChangeObservable(radiogroup_test)

        // Switch
        val switchChange = SwitchChangeObservable(switch_test)

        // CheckBox
        val checkBoxChange = SwitchChangeObservable(checkbox_test)

        // Toggle
        val toggleBtnChange = SwitchChangeObservable(togglebtn_test)

        // SeekBar
        val seekbarChange = SeekBarChangeObservable(seekbar_test)

        // RatingBar
        val ratingBarChange = RatingBarRatingChangeObservable(ratingbar_test)

        val switchChanges = Observable.merge(switchChange, checkBoxChange, toggleBtnChange)

        val numberChanges = Observable.merge(seekbarChange, ratingBarChange)

        val eventChange = Observable.merge(editTextChange, radioBtnChange, switchChanges, numberChanges)
            .subscribe {
                val isValidInputTxt = !TextUtils.isEmpty(et_test.text)
                val isValidRadioSelect = radiogroup_test.checkedRadioButtonId > 0

                val isValidSwitch = switch_test.isChecked
                val isValidCheckBox = checkbox_test.isChecked
                val isValidToggle = togglebtn_test.isChecked

                val isValidSeekbar = seekbar_test.progress > 0
                val isValidRating = ratingbar_test.rating > 0f

                if( isValidInputTxt && isValidRadioSelect &&
                        isValidSwitch && isValidCheckBox && isValidToggle &&
                            isValidSeekbar && isValidRating
                        ) {
                    btn_complete.setBackgroundColor(Color.BLUE)
                } else {
                    btn_complete.setBackgroundColor(Color.DKGRAY)
                }

                randomTests()
            }
        disposable.add(eventChange)
    }

    private fun randomTests() {
        val num = genRandom()

        Test2(num)
        Test3(num)
    }

    private fun Test1(num: Int = 0) {
        val arrInput = LinkedList<Int>()
        if( num > 0 ) {
            val strInput = num.toString()
        } else {
            arrInput.addAll(arrayOf(1, 2, 3))
        }
        val arrRotate = ArrayList<Int>()
        if( num > 0 ) {

        } else {
            arrRotate.addAll(arrayOf(1, 2, 3, 4))
        }
        val indices = ArrayList<Int>()

        val maxValue = Collections.max(arrInput)

        for( count in 0 until arrRotate.size ) {
            val result = arrInput

            for( rotate in 0 until arrRotate.size ) {
                val popItem = result.pop()
                result.add(popItem)
            }

            indices.add(result.indexOf(maxValue))
        }

        Log.d("NINIS", "Test1] result\n" + indices.toString())
    }

    private fun Test2(num: Int = 0) {
        var input = ArrayList<Int>()
        if( num > 0 ) {
            val strInput = num.toString()

            for( char in strInput ) {
                input.add(Character.getNumericValue(char))
            }
        } else {
            input = arrayListOf(1, 4, 4, 8)
        }

        val map = HashMap<Int, Int>()

        var count = 1
        for( index in 0 until input.size ) {
            val item = input[index]

            if( !map.containsKey(item) ) {
                input[index] = count
                map.put(item, count++)
            } else {
                map[item]?.let {
                    input[index] = it
                }
            }
        }

        Log.d("NINIS", "Test2] result\n" + input.toList().toString())
    }

    private fun Test3(num: Int = 0) {
        var inputNum = "11891"
        if( num > 0 ) {
            inputNum = num.toString()
        }
        var convertMax = ""
        var convertMin = ""

        var minNum = 0
        var maxNum = 0
        val arrInput = ArrayList<Int>()
        for( char in inputNum ) {
            arrInput.add(Character.getNumericValue(char))
        }

        Log.d("NINIS", "Test3] arrInput\n" + arrInput.toString())


        // min
        if( arrInput[0] == 9 ) {
            minNum = arrInput[1]
        } else {
            minNum = arrInput[0]
        }

        // max
        if( arrInput[0] == 1 ) {
            maxNum = arrInput[1]
        } else {
            maxNum = arrInput[0]
        }

        Log.d("NINIS", String.format("min : %d, max %d", minNum, maxNum))

        convertMax = inputNum.replace(minNum.toString(), "9")

        var temp = inputNum.replace(maxNum.toString(), "0")
        if( temp.startsWith("0") ) {
            temp = temp.replaceFirst("0", "1")
        }
        convertMin = temp

        Log.d("NINIS", "Test3] result\n" + String.format("convert min : %s, max : %s", convertMin, convertMax))
    }
}
