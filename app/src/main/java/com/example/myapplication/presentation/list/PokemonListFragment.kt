package com.example.myapplication.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.Singletons
import com.example.myapplication.presentation.api.PokeApi
import com.example.myapplication.presentation.api.PokemonListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListFragment : Fragment() {


    private lateinit var recyclerview: RecyclerView

    private val adapter = PokemonAdapter(listOf(), ::onClickedPokemon)

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




        Singletons.pokeApi.getPokemonList().enqueue(object: Callback<PokemonListResponse>{
            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                //TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<PokemonListResponse>, response: Response<PokemonListResponse>) {
                if(response.isSuccessful && response.body() != null) {
                    val pokemonResponse : PokemonListResponse = response.body()!!
                    adapter.updateList(pokemonResponse.results)
                }
            }
         })
    }
    private fun onClickedPokemon(pokemon : Pokemon) {
       findNavController().navigate(R.id.navigateToPokemonDetailFragment)
    }
}