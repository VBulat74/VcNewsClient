package ru.com.vbulat.vcnewsclient.ui.theme

import android.os.Handler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SideEffectTest(number : MyNMumber){
    Column {
        LazyColumn(){
            repeat(5){
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }

        Handler().postDelayed({
            number.a = 5
        },3000)

        LazyColumn(){
            repeat(5){
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
    }
}

data class MyNMumber(var a : Int)