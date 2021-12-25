package com.example.lab_3

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab_3.MainActivity.Companion.launcher
import com.example.lab_3.MainActivity.Companion.listNotes
import com.example.lab_3.databinding.FragmentNotesBinding
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.main_rc_content.*


class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (listNotes.isNotEmpty()) {
            init()
        }

        btnAdd.setOnClickListener {
            val intent = Intent(context, ContentActivity::class.java)
            launcher?.launch(intent)
        }

    }

    private fun init() {
        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(context)
        MainActivity.adapter = NotesAdapter(listNotes, context)
        rcView.adapter = MainActivity.adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()
    }
}