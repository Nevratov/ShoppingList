package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var ll_shop_list: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll_shop_list = findViewById(R.id.ll_shop_list)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            setShopList(it)
        }

    }




    private fun setShopList(list: List<ShopItem>) {
        ll_shop_list.removeAllViews()
        for (i in list) {
            val layout = if (i.enabled) R.layout.item_shop_enambled else R.layout.item_shop_disabled
            val view = LayoutInflater.from(this).inflate(layout, ll_shop_list, false)

            val name = view.findViewById<TextView>(R.id.tv_name)
            name.text = i.name
            val count = view.findViewById<TextView>(R.id.tv_count)
            count.text = i.count.toString()

            view.setOnLongClickListener {
                viewModel.changeEnableState(i)
                true
            }

            ll_shop_list.addView(view)

        }
    }
}
