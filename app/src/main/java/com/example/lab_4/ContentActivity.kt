package com.example.lab_3

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_3.databinding.LayoutContentBinding
import kotlinx.android.synthetic.main.layout_content.*

class ContentActivity: AppCompatActivity() {

    private lateinit var binding: LayoutContentBinding
    private val dataModel: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataModel.colorBackground.observe(this, {
            colorChoice = it
            editTextContent.setBackgroundColor(it)
        })
        colorChoice = Color.rgb(190,190,190)
        btnDelete.hide()
        btnSave.show()
        btnBackNote.setOnClickListener { finish() }
        btnColor.setOnClickListener {
            BottomFragment().show(supportFragmentManager, "tag")
        }
        btnSave.setOnClickListener {
            val content = editTextContent.text.toString().replace(" ", "").replace("\n","")
            val title = editTextTitle.text.toString().replace(" ", "")
            if (content.isNotEmpty() || title.isNotEmpty()) {
                intent.putExtra("addTitle", editTextTitle.text.toString())
                intent.putExtra("addContent", editTextContent.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
    companion object {
        var colorChoice:Int?=null
    }
}