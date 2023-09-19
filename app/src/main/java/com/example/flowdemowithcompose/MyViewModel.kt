package com.example.flowdemowithcompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    init {
        backPressureDemo()
    }

    private fun backPressureDemo() {
        val myFlow1 = flow<Int>{
            for (i in 1..10){
                Log.i("LinLi", "Produced $i ");
                emit(i)
                delay(1000L)
            }
        }
        viewModelScope.launch {
//            myFlow1.buffer().collect{
//                delay(2000L)
//                Log.i("LinLi", "Consumed $it ");
//            }
            myFlow1.collectLatest {
                delay(2000L)
                Log.i("LinLi", "Consumed $it ");
            }
            //producer只有在consumer可以消費它時才送資料
            //但有些狀況是我們需要在不等待生產者時得到流
            //例如我們從某個網路伺服器得到live data
            //為了避免等待消費者，我們可以用buffer operator
            //myFlow1.buffer().collect
            //這會創建一個協程給消費者，也就是說生產者與消費者會在不同的協程運行
            //有些情況下我們只需要最新的值，例如顯示某場比賽的當前得分，此時就可以用collectLatest()
        }
    }


}