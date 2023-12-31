package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
           shopListAdapter.submitList(it)
        }

    }

    fun setupRecyclerView() {
        val shopListRecyclerView: RecyclerView = findViewById(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        with(shopListRecyclerView) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.STATE_ENABLE, ShopListAdapter.MAX_PULL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.STATE_DISABLE, ShopListAdapter.MAX_PULL_SIZE)
        }

        setupLongClickListener()

        setupClickListener()

        setupSwipeListener(shopListRecyclerView)
    }

    private fun setupSwipeListener(shopListRecyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            LEFT or RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedShopItem = shopListAdapter.currentList[position]
                viewModel.deleteShopItem(deletedShopItem)
            }
        })

        itemTouchHelper.attachToRecyclerView(shopListRecyclerView)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            Log.d("listenerClick", it.toString())
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}

