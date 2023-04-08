package com.mehmetgunduz.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehmetgunduz.besinlerkitabi.R
import com.mehmetgunduz.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.mehmetgunduz.besinlerkitabi.viewModel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {

    private lateinit var viewModel: BesinListesiViewModel
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        besiListesiRecyclerView.layoutManager = LinearLayoutManager(context)
        besiListesiRecyclerView.adapter = recyclerBesinAdapter

        //Yenileme listeyi
        swipeRefreshLayout.setOnRefreshListener {
            besinYükleniyor.visibility = View.VISIBLE
            besinHataMesajı.visibility = View.GONE
            besiListesiRecyclerView.visibility = View.GONE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData(){
    viewModel.besinler.observe(viewLifecycleOwner, Observer {
        it.let {
            besiListesiRecyclerView.visibility = View.VISIBLE
            recyclerBesinAdapter.besinListUpdate(it)
        }
    })

        viewModel.besinHataMesaj.observe(viewLifecycleOwner, Observer { hata->
            hata?.let {
                if (it){
                    besiListesiRecyclerView.visibility = View.GONE
                    besinHataMesajı.visibility = View.VISIBLE
                }else{
                    besinHataMesajı.visibility = View.GONE
                }
            }
        })

        viewModel.besinYükleniyor.observe(viewLifecycleOwner, Observer { yükleniyor ->
            yükleniyor?.let {
                if (it){
                    besiListesiRecyclerView.visibility = View.GONE
                    besinHataMesajı.visibility = View.GONE
                    besinYükleniyor.visibility = View.VISIBLE
                }else{
                    besinYükleniyor.visibility = View.GONE
                }
            }
        })
    }


}