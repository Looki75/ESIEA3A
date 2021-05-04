package com.example.myapplication.presentation.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.Singletons
import com.example.myapplication.presentation.api.PokemonListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListFragment : Fragment() {


    private lateinit var recyclerview: RecyclerView

    private val adapter = PokemonAdapter(listOf(), ::onClickedPokemon)

    private val sharedPref = activity?.getSharedPreferences("app", Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = view.findViewById(R.id.pokemon_recyclerview)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PokemonListFragment.adapter
        }

        val list = getListFromCache()
        if (list.isEmpty()) {
            callApi()
        } else {
            showList(list)
        }
    }

    private fun getListFromCache(): List<Pokemon> {
    //sharedPref
        TODO("Not yet implemented")

    }

    private fun saveListIntoCache() {
        TODO("Not yet implemented")
    }


    private fun callApi() {
        Singletons.pokeApi.getPokemonList().enqueue(object : Callback<PokemonListResponse> {
            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                //TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<PokemonListResponse>, response: Response<PokemonListResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val pokemonResponse: PokemonListResponse = response.body()!!
                    adapter.updateList(pokemonResponse.results)
                    saveListIntoCache()
                    showList(pokemonResponse.results)
                }
            }
        })
    }


    private fun showList(pokeList: List<Pokemon>) {
        adapter.updateList(pokeList)

    }

    private fun onClickedPokemon(id : Int) {
       findNavController().navigate(R.id.navigateToPokemonDetailFragment, bundleOf(
               "pokemonId" to (id + 1)
       ))
    }
}