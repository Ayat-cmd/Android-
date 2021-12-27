package com.example.lab_3

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_3.MainActivity.Companion.colorArr
import com.example.lab_3.MainActivity.Companion.listCart
import com.example.lab_3.MainActivity.Companion.listNotes
import com.example.lab_3.databinding.FragmentCartBinding
import kotlinx.android.synthetic.main.main_rc_content.*


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var LinearOrGridLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        launcherCart = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
            if (RESULT_OK == result.resultCode){
                createAlertDialog()
            }
        }
        init()
    }

    private fun init() {
        if(MainActivity.LinearOrGridBoolean!!){
            LinearOrGridLayoutManager = GridLayoutManager(context,2)
            MainActivity.LinearOrGridBoolean = true
        }else{
            LinearOrGridLayoutManager = LinearLayoutManager(context)
            MainActivity.LinearOrGridBoolean = false
        }
        rcView.hasFixedSize()
        rcView.layoutManager = LinearOrGridLayoutManager
        adapterCart = CartAdapter(listCart, context, colorArr)
        rcView.adapter = adapterCart
    }

    fun createAlertDialog() {
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.dialog_delete_note))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.dialog_restore)){ _, _ ->
                listNotes.add(listCart[indCart!!])
                colorArr.add(listCart[indCart!!].color!!)
                listCart.removeAt(indCart!!)
                init()
            }
            .setPositiveButton(getString(R.string.dialog_yes)){ _, _ ->
                listCart.removeAt(indCart!!)
                init()
            }
            .setNeutralButton(getString(R.string.dialog_cancel)){ dialog, i -> dialog.dismiss() }
            .show()

    }

    companion object {
        var launcherCart: ActivityResultLauncher<Intent>? = null
        var indCart:Int ?= null
        @SuppressLint("StaticFieldLeak")
        var adapterCart: CartAdapter ?= null
        @JvmStatic
        fun newInstance() = CartFragment()
    }

}