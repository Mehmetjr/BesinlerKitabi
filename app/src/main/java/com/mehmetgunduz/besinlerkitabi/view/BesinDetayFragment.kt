package com.mehmetgunduz.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mehmetgunduz.besinlerkitabi.R
import com.mehmetgunduz.besinlerkitabi.viewModel.BesinDetayViewModel
import kotlinx.android.synthetic.main.fragment_besin_detay.*


class BesinDetayFragment : Fragment() {

    private lateinit var viewModel: BesinDetayViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_detay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BesinDetayViewModel::class.java)
        viewModel.roomVerisiniAl()
        observeLiveData()
    }

    fun observeLiveData(){

        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let {
                besinIsim.text = it.besinIsim
                besinKalori.text=it.besinKalori
                besinYag.text = it.besinYag
                besinKarbonhidrat.text = it.besinKarbonhidrat
                besinProtein.text = it.besinProtein
            }
        })
    }


}