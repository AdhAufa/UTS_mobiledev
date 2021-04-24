package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aufhu.Aufa_Alaina_Adhar_16090154.R
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.AuthManager
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.hide
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.showToast
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.visible
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product
import com.aufhu.Aufa_Alaina_Adhar_16090154.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home), ProductAdapterInterface {
    private var binding : FragmentHomeBinding? = null
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setupViewModel()
        observe()
        setupRecyclerView()
        fabClick()
    }

    private fun fabClick(){
        binding?.fab?.setOnClickListener {
            findNavController().navigate(R.id.action_to_create_page)
        }
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }

    private fun observe(){
        viewModel.getState().observe(viewLifecycleOwner, { handleState(it) })
        viewModel.getProducts().observe(viewLifecycleOwner, { handleProducts(it) })
    }

    private fun handleState(state: HomeFragmentState){
        when(state){
            is HomeFragmentState.ShowToast -> requireActivity().showToast(state.message)
            is HomeFragmentState.IsLoading -> {
                if(state.isLoading){
                    binding?.productLoading?.visible()
                }else{
                    binding?.productLoading?.hide()
                }
            }
        }
    }

    private fun handleProducts(products : List<Product>){
        binding?.productRecycler?.adapter?.let {
            if(it is ProductAdapter){
                it.updateList(products)
            }
        }
    }

    private fun setupRecyclerView(){
        binding?.productRecycler?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = ProductAdapter(mutableListOf(), this@HomeFragment)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onTap(p: Product) {
        val b = bundleOf("productId" to p.id)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, b)
    }

    override fun onLongTap(p: Product) {
//        requireActivity().showToast(p.name)
    }

    override fun onResume() {
        super.onResume()
        val token = AuthManager.getToken(requireActivity())
        token?.let { t ->
            viewModel.fetchAllProducts(t)
        }
    }
}