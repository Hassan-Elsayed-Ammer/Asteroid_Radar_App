package com.hassan.elsayed.ammer.asteroidradar.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hassan.elsayed.ammer.asteroidradar.R
import com.hassan.elsayed.ammer.asteroidradar.adapters.AsteroidAdapter
import com.hassan.elsayed.ammer.asteroidradar.databinding.FragmentMainBinding
import com.hassan.elsayed.ammer.asteroidradar.ui.MainViewModel


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.showItemDetails(it)
        })

        viewModel.navigateToSelectedItem.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.setNull()
            }
        }

        setHasOptionsMenu(true)


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.week_asteroid -> viewModel.viewWeekAsteroids()
            R.id.today_asteroid -> viewModel.viewTodayAsteroids()
            R.id.save_asteroid -> viewModel.viewSavedAsteroids()
        }
        return true
    }


}