package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class quiz : AppCompatActivity(), View.OnClickListener {
    lateinit var totalQuestionTextView: TextView
    lateinit var questionTextView: TextView
    lateinit var ansA: TextView
    lateinit var ansB: TextView
    lateinit var ansC: TextView
    lateinit var submit: Button
    var score = 0
    var totalQuestion: Int = QuestionAnswer.question.size
    var currentQuestionIndex = 0
    var selectedAnswer = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        totalQuestionTextView = findViewById(R.id.total_question)
        questionTextView = findViewById(R.id.question)

        ansA = findViewById(R.id.ans_a)
        ansB = findViewById(R.id.ans_b)
        ansC = findViewById(R.id.ans_c)
        submit = findViewById(R.id.submit)
        ansA.setOnClickListener(this)
        ansB.setOnClickListener(this)
        ansC.setOnClickListener(this)
        submit.setOnClickListener(this)
        loadNewQuestion()
    }

    private fun loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz()
            return
        }
        questionTextView.text = QuestionAnswer.question[currentQuestionIndex]
        ansA.text = QuestionAnswer.choices[currentQuestionIndex][0]
        ansB.text = QuestionAnswer.choices[currentQuestionIndex][1]
        ansC.text = QuestionAnswer.choices[currentQuestionIndex][2]
    }

    private fun finishQuiz() {
        var pass = ""
        if (score >= 1) {
            pass = "critical"
        } else {
            pass = "normal"
        }
        val check=intent.getIntExtra("value",0)
        val position = intent.getIntExtra("key1", 0)
        val intent = Intent(this, Request::class.java)
        intent.putExtra("key1", position)
        intent.putExtra("key", pass)
        intent.putExtra("value",check)
        startActivity(intent)
    }

    override fun onClick(view: View?) {
        val clickedButton = view as Button
        if (clickedButton.id == R.id.submit) {
            if (selectedAnswer == QuestionAnswer.correctans[currentQuestionIndex]) {
                score++
            }
            currentQuestionIndex++
            //totalQuestionTextView.text = "Score: $score / $totalQuestion"
            loadNewQuestion()
        } else {
            selectedAnswer = clickedButton.text.toString()
        }
    }
}