package com.example.kisileruygulamasi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kisileruygulamasi.R
import com.example.kisileruygulamasi.data.entity.Kisiler
import com.example.kisileruygulamasi.databinding.FragmentAnasayfaBinding
import com.example.kisileruygulamasi.ui.adapter.KisilerAdapter
import com.example.kisileruygulamasi.ui.viewmodel.AnasayfaViewModel
import com.example.kisileruygulamasi.ui.viewmodel.KisiKayitViewModel
import com.example.kisileruygulamasi.utils.gecis

class AnasayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnasayfaBinding
    private lateinit var viewModel: AnasayfaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnasayfaBinding.inflate(inflater, container, false)

      viewModel.kisilerListesi.observe(viewLifecycleOwner){
          val kisilerAdapter = KisilerAdapter(requireContext(),it,viewModel)
          binding.kisilerRv.adapter = kisilerAdapter
      }


        binding.kisilerRv.layoutManager = LinearLayoutManager(requireContext())
       // binding.kisilerRv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
       // binding.kisilerRv.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL) instagram hikayeler gibi




        binding.fab.setOnClickListener {
           // Navigation.findNavController(it).navigate(R.id.kisiKayitGecis)
            Navigation.gecis(it,R.id.kisiKayitGecis)// Extension ile navigation fonk genişleterek kısalttık
        }


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {//Harf girdikce ve sildikce çalışır
                viewModel.ara(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {//Klavyedeki ara butonuyla çalışır
                viewModel.ara(query)
                return true
            }
        })

        return binding.root
    }
    fun ara(aramaKelimesi:String){
        Log.e("Kişi Ara",aramaKelimesi)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AnasayfaViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.kisileriYukle()
    }
}