package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aufhu.Aufa_Alaina_Adhar_16090154.R
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.AuthManager
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.showAlert
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.request.CreateProductRequest
import com.aufhu.Aufa_Alaina_Adhar_16090154.databinding.FragmentCreateBinding

class CreateFragment : Fragment(R.layout.fragment_create) {
    private var binding : FragmentCreateBinding? = null
    private lateinit var vm : CreateFragmentViewModel

    @ExperimentalUnsignedTypes
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateBinding.bind(view)
        setupViewModel()
        observe()
        onSaveClick()
    }

    private fun setupViewModel(){
        vm = ViewModelProvider(this).get(CreateFragmentViewModel::class.java)
    }

    private fun observe(){
        vm.getState().observe(viewLifecycleOwner, { handleState(it) })
    }

    private fun handleState(state: CreateFragmentState){
        when(state){
            is CreateFragmentState.IsLoading -> binding?.simpanButton?.isEnabled = !state.isLoading
            is CreateFragmentState.ShowAlert -> requireActivity().showAlert(state.msg)
            is CreateFragmentState.OnSuccessCreate -> findNavController().navigate(R.id.action_popup_to_home)
        }
    }

    @ExperimentalUnsignedTypes
    private fun onSaveClick(){
        binding?.simpanButton?.setOnClickListener {
            val name = binding?.productNameEditText?.text.toString().trim()
            val price = binding?.productPriceEditText?.text.toString().trim()
            if(validate(name, price)){
                val token = AuthManager.getToken(requireContext())
                token?.let {
                    val priceInt = price.toUInt()
                    val productRequest = CreateProductRequest(name, priceInt)
                    vm.createProduct(it, productRequest)
                }

            }

        }
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