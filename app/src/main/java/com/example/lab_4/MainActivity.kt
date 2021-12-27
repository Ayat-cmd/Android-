package com.example.lab_3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_3.CartFragment.Companion.adapterCart
import com.example.lab_3.ContentActivity.Companion.colorChoice
import com.example.lab_3.R.id
import com.example.lab_3.UpdateNote.Companion.flagCart
import com.example.lab_3.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_rc_content.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var LinearOrGridLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFrag(NotesFragment.newInstance(), id.place_holder)
        btnClear.visibility = View.INVISIBLE
        LinearGrid()

        switchGridOrLinear.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                LinearOrGridLayoutManager = GridLayoutManager(this,2)
                init(true)
                LinearOrGridBoolean = true
            }else{
                LinearOrGridLayoutManager = LinearLayoutManager(this)
                init(true)
                LinearOrGridBoolean = false
            }
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (RESULT_OK == result.resultCode && !result.data?.getStringExtra("addTitle").equals(null)) {
                val title = result.data?.getStringExtra("addTitle")
                val con = result.data?.getStringExtra("addContent")
                if (colorChoice != null)
                    colorArr.add(colorChoice!!)
                colorChoice = Color.rgb(190,190,190)
                val item = ItemList(title, con)
                listNotes.add(item)
                ind=-1
                LinearGrid()
                init(true)
            } else if (RESULT_OK == result.resultCode && !result.data?.getStringExtra("deleteTitle").equals(null)) {
                Log.i("info", "${listNotes.size}")
                listCart.add(listNotes[ind!!])
                listNotes.removeAt(ind!!)
                colorArr.removeAt(ind!!)
                ind=-1
                LinearGrid()
                init(true)
            } else if (RESULT_OK == result.resultCode && !result.data?.getStringExtra("updateTitle").equals(null)) {
                val titlee = result.data?.getStringExtra("updateTitle")
                val con = result.data?.getStringExtra("updateContent")
                val item = ItemList(titlee, con)
                listNotes[ind!!] = item
                LinearGrid()
                init(true)
            }
        }
        btnClear.setOnClickListener {
            if (listCart.isNotEmpty()) {
                listCart.clear()
                LinearGrid()
                init(false)
            }
        }
        binding.apply {
            nv.setNavigationItemSelectedListener {
                when(it.itemId) {
                    id.id_notes -> {
                        flagCart = true
                        btnClear.visibility = View.INVISIBLE
                        switchGridOrLinear.visibility = View.VISIBLE
                        openFrag(NotesFragment.newInstance(), id.place_holder)
                    }
                    id.id_cart -> {
                        btnClear.visibility = View.VISIBLE
                        switchGridOrLinear.visibility = View.INVISIBLE
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

    private fun LinearGrid(){
        when {
            LinearOrGridBoolean == null -> {
                LinearOrGridBoolean = false
                LinearOrGridLayoutManager = LinearLayoutManager(this)
            }
            LinearOrGridBoolean!! -> {
                LinearOrGridLayoutManager = GridLayoutManager(this, 2)
            }
            else -> {
                LinearOrGridLayoutManager = LinearLayoutManager(this)
            }
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
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
        val editNotes = switchGridOrLinear
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
        editNotes.setText(R.string.edit_notes)
    }

    private fun openFrag(f: Fragment, idHolder: Int) {
        supportFragmentManager.beginTransaction()
            .replace(idHolder, f)
            .commit()
    }
    private fun init(flag: Boolean) {
        rcView.hasFixedSize()
        rcView.layoutManager = LinearOrGridLayoutManager
        if (flag){
            adapter = NotesAdapter(listNotes, this, colorArr)
            rcView.adapter = adapter
        } else if (!flag) {
            adapterCart = CartAdapter(listCart, this, colorArr)
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
        var colorArr = ArrayList<Int>()
        var LinearOrGridBoolean:Boolean ?= null

    }
}