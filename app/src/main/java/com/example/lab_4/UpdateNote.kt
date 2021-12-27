package com.example.lab_3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_3.MainActivity.Companion.colorArr
import com.example.lab_3.MainActivity.Companion.ind
import kotlinx.android.synthetic.main.layout_content.*

class UpdateNote: AppCompatActivity()  {
    private val dataModel: DataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_content)
        dataModel.colorBackground.observe(this, {
            colorArr[ind!!] = it
            editTextContent.setBackgroundColor(it)
        })
        editTextContent.setBackgroundColor(colorArr[ind!!])
        editTextTitle.setText(intent.getStringExtra("title"))
        editTextContent.setText(intent.getStringExtra("content"))
        btnDelete.show()

        btnDelete.setOnClickListener {
            intent.putExtra("deleteTitle", editTextTitle.text.toString())
            intent.putExtra("deleteContent", editTextContent.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        btnBackNote.setOnClickListener { finish() }

        btnColor.setOnClickListener {
            BottomFragment().show(supportFragmentManager, "tag")
        }

        btnSave.setOnClickListener {
            val content = editTextContent.text.toString().replace(" ", "").replace("\n","")
            val title = editTextTitle.text.toString().replace(" ", "")
            if (content.isNotEmpty() || title.isNotEmpty()) {
                intent.putExtra("updateTitle", editTextTitle.text.toString())
                intent.putExtra("updateContent", editTextContent.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!flagCart) {
            btnSave.hide()
        }
    }

    companion object{
        var flagCart = true
    }
}