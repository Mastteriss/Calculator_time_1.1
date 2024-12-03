package com.example.timecalculator
import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var toolbar: Toolbar
    private lateinit var firstOperandET:EditText
    private lateinit var secondOperandET:EditText

    private lateinit var buttonSumBtn:Button
    private lateinit var buttonDifBtn:Button

    private lateinit var resultTv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbarMainMenu)
        setSupportActionBar(toolbar)

        title = "Калькулятор времени"
        toolbar.subtitle = "Версия 1.1"
        toolbar.setLogo(R.drawable.ic_time_calculate)

        firstOperandET = findViewById(R.id.firstOperandET)
        secondOperandET = findViewById(R.id.secondOperandET)
        buttonSumBtn = findViewById(R.id.buttonSumBTN)
        buttonDifBtn = findViewById(R.id.buttonDifBTN)

        resultTv = findViewById(R.id.resultTV)

        buttonSumBtn.setOnClickListener(this)
        buttonDifBtn.setOnClickListener(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.resetMenuMain ->{
                firstOperandET.text.clear()
                secondOperandET.text.clear()
                resultTv.text = "Результат"

                Toast.makeText(
                    applicationContext,
                    "Данные очищены",
                    Toast.LENGTH_LONG
                ).show()
            }
            R.id.exitMenuMain ->{
                Toast.makeText(
                    applicationContext,
                    "Приложение закрыто",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        val time1 = firstOperandET.text.toString()
        val time2 = secondOperandET.text.toString()

        if(firstOperandET.text.isEmpty()||secondOperandET.text.isEmpty())
        {
            resultTv.text = "Введите оба времени"
            return
        }
       val totalSeconds1 = convertToSeconds(time1)
        val totalSeconds2 = convertToSeconds(time2)
        val resultInt = if (v.id ==R.id.buttonSumBTN){
            totalSeconds1 + totalSeconds2
        }else{
            totalSeconds1 - totalSeconds2
        }

        resultTv.text = "Результат ${convertToFormattedString(resultInt)}"
        resultTv.setTextColor(Color.parseColor("#8B0000"))


    }
    private fun convertToSeconds(time:String):Int {

        var totalSeconds = 0
        val regex = "(\\d+)(h|m|s)".toRegex()

        val matches = regex.findAll(time)
        for (match in matches) {
            val (value, unit) = match.destructured
            when (unit) {
                "h" -> totalSeconds += value.toInt() * 3600
                "m" -> totalSeconds += value.toInt() * 60
                "s" -> totalSeconds += value.toInt()
            }
        }
        return  totalSeconds
    }

    private fun convertToFormattedString(totalSeconds: Int): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return if (hours > 0) {
            "${hours}h ${minutes}m ${seconds}s"
        } else {
            "${minutes}m ${seconds}s"
        }
    }

}
