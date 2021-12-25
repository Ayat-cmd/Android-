package com.example.lab_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_3.databinding.LayoutContentBinding
import kotlinx.android.synthetic.main.layout_content.*

class ContentActivity: AppCompatActivity() {

    private lateinit var binding: LayoutContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnDelete.hide()
        btnSave.show()
        btnBackNote.setOnClickListener { finish() }
        btnSave.setOnClickListener {
            if ((editTextTitle.text.isNotEmpty() || editTextContent.text.isNotEmpty()) ||
                (editTextTitle.text.equals("") && editTextContent.text.equals(""))) {
                intent.putExtra("addTitle", editTextTitle.text.toString())
                intent.putExtra("addContent", editTextContent.text.toString())
                setResult(RESULT_OK, intent)
                finish()

            }
        }
    }
}