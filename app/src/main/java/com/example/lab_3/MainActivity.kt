package com.example.lab_3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab_3.CartFragment.Companion.adapterCart
import com.example.lab_3.R.id
import com.example.lab_3.UpdateNote.Companion.flagCart
import com.example.lab_3.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_content.*
import kotlinx.android.synthetic.main.main_rc_content.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFrag(NotesFragment.newInstance(), id.place_holder)
        btnClear.visibility = View.INVISIBLE


        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (RESULT_OK == result.resultCode && !result.data?.getStringExtra("addTitle").equals(null)) {
                val title = result.data?.getStringExtra("addTitle")
                val con = result.data?.getStringExtra("addContent")
                val item = ItemList(title, con)
                listNotes.add(item)
                init(true)
            } else if (RESULT_OK == result.resultCode && !result.data?.getStringExtra("deleteTitle").equals(null)) {
                listCart.add(listNotes[ind!!])
                listNotes.removeAt(ind!!)
                init(true)
            } else if (RESULT_OK == result.resultCode && !result.data?.getStringExtra("updateTitle").equals(null)) {
                val titlee = result.data?.getStringExtra("updateTitle")
                val con = result.data?.getStringExtra("updateContent")
                val item = ItemList(titlee, con)
                listNotes[ind!!] = item
                init(true)
            }
        }
        btnClear.setOnClickListener {
            listCart.clear()
            init(false)
        }
        binding.apply {
            nv.setNavigationItemSelectedListener {
                when(it.itemId) {
                    id.id_notes -> {
                        flagCart = true
                        btnClear.visibility = View.INVISIBLE
                        openFrag(NotesFragment.newInstance(), id.place_holder)
                    }
                    id.id_cart -> {
                        btnClear.visibility = View.VISIBLE
                        flagCart = false
                        openFrag(CartFragment.newInstance(), id.place_holder)
                    }
                    id.id_translate -> {
                        translateLanguage()
                    }
                }
                drawer.closeDrawer(GravityCompat.START)
                true
            }
            btnMenu.setOnClickListener {
                drawer.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun translateLanguage() {
        var language = ""
        if(flagLanguage){
            language = "en"
            flagLanguage = false
        }else if(!flagLanguage) {
            language = "ru"
            flagLanguage = true
        }
        val nv = findViewById<NavigationView>(id.nv)
        val translate = nv.menu.findItem(id.id_translate)
        val notes = nv.menu.findItem(id.id_notes)
        val cart = nv.menu.findItem(id.id_cart)
        val btnClear = btnClear
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(configuration, null)
        notes.setTitle(R.string.nav_note)
        cart.setTitle(R.string.nav_cart)
        translate.setTitle(R.string.nav_translate)
        btnClear.setText(R.string.clear_cart)
    }

    private fun openFrag(f: Fragment, idHolder: Int) {
        supportFragmentManager.beginTransaction()
            .replace(idHolder, f)
            .commit()
    }
    private fun init(flag: Boolean) {
        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(this)
        if (flag){
            adapter = NotesAdapter(listNotes, this)
            rcView.adapter = adapter
        } else if (!flag) {
            adapterCart = CartAdapter(listCart, this)
            rcView.adapter = adapterCart
        }
    }

    companion object{
        var ind:Int ?= null
        var launcher: ActivityResultLauncher<Intent>? = null
        @SuppressLint("StaticFieldLeak")
        var adapter: NotesAdapter ?= null
        var listNotes = ArrayList<ItemList>()
        var listCart = ArrayList<ItemList>()
        var flagLanguage = true
    }
}