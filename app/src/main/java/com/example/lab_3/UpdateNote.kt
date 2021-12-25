package com.example.lab_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_content.*

class UpdateNote: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_content)
        editTextTitle.setText(intent.getStringExtra("title"))
        editTextContent.setText(intent.getStringExtra("content"))
        btnDelete.show()

        btnDelete.setOnClickListener {
            intent.putExtra("deleteTitle", editTextTitle.text.toString())
            intent.putExtra("deleteContent", editTextContent.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        btnSave.setOnClickListener {
            intent.putExtra("updateTitle", editTextTitle.text.toString())
            intent.putExtra("updateContent", editTextContent.text.toString())
            setResult(RESULT_OK, intent)
            finish()
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