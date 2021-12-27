package com.example.lab_3

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lab_3.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomFragment : BottomSheetDialogFragment() {

    private val dataModel: DataModel by activityViewModels()

    lateinit var binding: BottomSheetLayoutBinding

    // Переопределим тему, чтобы использовать нашу с закруглёнными углами
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetLayoutBinding.bind(inflater.inflate(R.layout.bottom_sheet_layout, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            green.setOnClickListener {
                dataModel.colorBackground.value = Color.GREEN
            }
            blue.setOnClickListener {
                dataModel.colorBackground.value = Color.BLUE
            }
            white.setOnClickListener {
                dataModel.colorBackground.value = Color.WHITE
            }
            red.setOnClickListener {
                dataModel.colorBackground.value = Color.RED
            }
            pink.setOnClickListener {
                dataModel.colorBackground.value = Color.rgb(255,105,180)
            }
            yellow.setOnClickListener {
                dataModel.colorBackground.value = Color.YELLOW
            }
            orange.setOnClickListener {
                dataModel.colorBackground.value = Color.rgb(255,140,0)
            }
            brown.setOnClickListener {
                dataModel.colorBackground.value = Color.rgb(210,105,30)
            }
            violet.setOnClickListener {
                dataModel.colorBackground.value = Color.rgb(138,43,226)
            }
        }
    }

}