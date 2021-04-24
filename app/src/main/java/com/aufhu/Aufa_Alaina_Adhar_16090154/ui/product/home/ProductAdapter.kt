package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product
import com.aufhu.Aufa_Alaina_Adhar_16090154.databinding.ItemProductBinding

class ProductAdapter (private val products : MutableList<Product>, private val productAdapterInterface : ProductAdapterInterface) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    inner class ViewHolder(private val itemBinding : ItemProductBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(p: Product){
            itemBinding.productNameTextView.text = p.name
            itemBinding.root.setOnClickListener {
                productAdapterInterface.onTap(p)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])

    override fun getItemCount(): Int = products.size

    fun updateList(updated: List<Product>){
        products.clear()
        products.addAll(updated)
        notifyDataSetChanged()
    }
}