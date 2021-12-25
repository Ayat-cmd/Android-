package com.example.lab_3

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab_3.MainActivity.Companion.listCart
import com.example.lab_3.MainActivity.Companion.listNotes
import com.example.lab_3.databinding.FragmentCartBinding
import kotlinx.android.synthetic.main.main_rc_content.*


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

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
        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(context)
        adapterCart = CartAdapter(listCart, context)
        rcView.adapter = adapterCart
    }

    fun createAlertDialog() {
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.dialog_delete_note))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.dialog_restore)){ dialog, i ->
                listNotes.add(listCart[indCart!!])
                listCart.removeAt(indCart!!)
                init()
            }
            .setPositiveButton(getString(R.string.dialog_yes)){ dialog, i ->
                listCart.removeAt(indCart!!)
                init()
            }
            .setNeutralButton(getString(R.string.dialog_cancel)){ dialog, i -> dialog.dismiss() }
            .show()

    }

    companion object {
        var launcherCart: ActivityResultLauncher<Intent>? = null
        var indCart:Int ?= null
        var adapterCart: CartAdapter ?= null
        @JvmStatic
        fun newInstance() = CartFragment()
    }

}