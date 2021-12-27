package com.example.lab_3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_3.MainActivity.Companion.LinearOrGridBoolean
import com.example.lab_3.MainActivity.Companion.colorArr
import com.example.lab_3.MainActivity.Companion.launcher
import com.example.lab_3.MainActivity.Companion.listNotes
import com.example.lab_3.databinding.FragmentNotesBinding
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.main_rc_content.*


class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var LinearOrGridLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (listNotes.isNotEmpty()) {
            MainActivity.ind =-1
            init()
        }
        btnAdd.setOnClickListener {
            val intent = Intent(context, ContentActivity::class.java)
            launcher?.launch(intent)
        }
    }


    private fun init() {
        if(LinearOrGridBoolean!!){
            LinearOrGridLayoutManager = GridLayoutManager(context,2)
            LinearOrGridBoolean = true
        }else{
            LinearOrGridLayoutManager = LinearLayoutManager(context)
            LinearOrGridBoolean = false
        }
        rcView.hasFixedSize()
        rcView.layoutManager = LinearOrGridLayoutManager
        MainActivity.adapter = NotesAdapter(listNotes, context, colorArr)
        rcView.adapter = MainActivity.adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()
    }
}