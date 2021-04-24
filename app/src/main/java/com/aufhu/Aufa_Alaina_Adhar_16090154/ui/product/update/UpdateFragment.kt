package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aufhu.Aufa_Alaina_Adhar_16090154.R
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.AuthManager
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.showAlert
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.request.CreateProductRequest
import com.aufhu.Aufa_Alaina_Adhar_16090154.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment(R.layout.fragment_update) {
    private var binding: FragmentUpdateBinding? = null
    private lateinit var vm : UpdateFragmentViewModel

    @ExperimentalUnsignedTypes
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateBinding.bind(view)
        setupViewModel()
        observe()
        fetchProduct()
        updateProduct()
        deleteProduct()
    }

    private fun setupViewModel(){
        vm = ViewModelProvider(this).get(UpdateFragmentViewModel::class.java)
    }

    private fun observe(){
        vm.getState().observe(viewLifecycleOwner, { handleState(it) })
    }

    private fun fetchProduct(){
        val token = AuthManager.getToken(requireActivity())
        token?.let {
            val id = arguments?.getInt("productId") ?: 0
            if (id != 0){
                vm.getProductById(it, id.toString())
            }
        }
    }

    private fun handleState(state: UpdateFragmentState){
        when(state){
            is UpdateFragmentState.IsLoading -> {
                binding?.simpanButton?.isEnabled = !state.isLoading
                binding?.hapusButton?.isEnabled = !state.isLoading
            }
            is UpdateFragmentState.Success -> {
                findNavController().navigate(R.id.action_update_to_home)
            }
            is UpdateFragmentState.ShowAlert -> requireActivity().showAlert(state.message)
            is UpdateFragmentState.OnGetProduct -> {
                handleProduct(state.product)
            }
        }
    }

    private fun deleteProduct(){
        binding?.hapusButton?.setOnClickListener {
            val id = arguments?.getInt("productId") ?: 0
            val token = AuthManager.getToken(requireActivity())

            token?.let { t ->
                vm.deleteProduct(token, id.toString())
            }
        }
    }

    @ExperimentalUnsignedTypes
    private fun updateProduct(){
        binding?.simpanButton?.setOnClickListener {
            val name = binding?.productNameEditText?.text.toString().trim()
            val price = binding?.productPriceEditText?.text.toString().trim()
            if(validate(name, price)){
                val token = AuthManager.getToken(requireContext())
                token?.let {
                    val id = arguments?.getInt("productId") ?: 0
                    val priceInt = price.toUInt()
                    val productRequest = CreateProductRequest(name, priceInt)
                    vm.updateProduct(it, id.toString(), productRequest)
                }
            }
        }
    }

    private fun handleProduct(p: Product){
        binding?.productNameEditText?.setText(p.name)
        binding?.productPriceEditText?.setText(p.price.toString())
    }

    @ExperimentalUnsignedTypes
    private fun validate(name: String, price: String) : Boolean {
        setErrorName(null)
        setErrorPrice(null)

        if(name.isEmpty()){
            setErrorName("Nama produk wajib diisi")
            return false
        }

        if(price.isEmpty()){
            setErrorPrice("Harga wajib diisi")
            return false
        }

        if(price.toUIntOrNull() == null){
            setErrorPrice("Masukkan harga yang benar")
            return false
        }

        return true
    }

    private fun setErrorName(err: String?) {
        binding?.productNameInput?.error = err
    }

    private fun setErrorPrice(err: String?) {
        binding?.productPriceInput?.error = err
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}