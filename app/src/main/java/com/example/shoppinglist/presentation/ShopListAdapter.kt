package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(field, value)
            val diff = DiffUtil.calculateDiff(callback)
            diff.dispatchUpdatesTo(this)
            field = value
        }


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutId = if (viewType == STATE_ENABLE)
            R.layout.item_shop_enambled else R.layout.item_shop_disabled
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ShopItemViewHolder(view)
    }

    var count = 0
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder ${++count}")
        val shopItem = shopList[position]
        holder.tv_name.text = shopItem.name
        holder.tv_count.text = shopItem.count.toString()

        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            Toast.makeText(holder.itemView.context, "position: $position", Toast.LENGTH_SHORT).show()
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enabled) STATE_ENABLE else STATE_DISABLE
    }

    companion object {
        const val STATE_ENABLE = 1
        const val STATE_DISABLE = 0

        const val MAX_PULL_SIZE = 10
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_count = view.findViewById<TextView>(R.id.tv_count)
    }
}