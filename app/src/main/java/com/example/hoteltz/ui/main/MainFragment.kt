package com.example.hoteltz.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hoteltz.R

class MainFragment : Fragment() {

    private lateinit var progressSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerViewBody: RecyclerView
    private lateinit var noDataTextView: TextView

    private lateinit var viewModel: MainViewModel
    private lateinit var viewMainFragment: View

    private var initViewFlag: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("DataRepository", "onCreate MainFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("DataRepository", "onCreateView MainFragment")

        setHasOptionsMenu(true);

        if (initViewFlag) {
            initViewFlag = false

            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

            viewMainFragment = inflater.inflate(R.layout.main_fragment, container, false)

            recyclerViewBody = viewMainFragment.findViewById(R.id.recyclerview_body)
            noDataTextView = viewMainFragment.findViewById(R.id.recyclerview_body_text_no_data)
            progressSwipeRefreshLayout =
                viewMainFragment.findViewById(R.id.progressSwipeRefreshLayout)

            progressSwipeRefreshLayout.isEnabled = true
            progressSwipeRefreshLayout.isRefreshing = true

            viewModel.adapterRV.itemClickListener = { it ->
                viewModel.itemClickRV(it)

                val bundle = bundleOf(
                    "id" to it.id,
                    "address" to it.address,
                    "distance" to it.distance,
                    "name" to it.name,
                    "stars" to it.stars,
                    "suitesAvailability" to it.suites_availability
                )

                Navigation.findNavController(viewMainFragment)
                    .navigate(R.id.action_mainFragment_to_itemFragment, bundle)
            }
            recyclerViewBody.adapter = viewModel.adapterRV
            recyclerViewBody.layoutManager = LinearLayoutManager(context)

            if (viewModel.adapterRV.items.isEmpty()) viewModel.fetchAllHotels() //Данные будут получены только один раз при запуске приложения

        } else {
            Log.d("DataRepository", "initViewFlag == false")
        }
        return viewMainFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.allHotelLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.adapterRV.items = it
            } else {
                noDataTextView.visibility = View.VISIBLE
            }

            progressSwipeRefreshLayout.isRefreshing = false
            progressSwipeRefreshLayout.isEnabled = false //обновление только при запуске приложения
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort_by_len -> {
                Toast.makeText(activity, "action_sort_by_len", Toast.LENGTH_SHORT).show()
                Log.d("DataRepository", "action_sort_by_len")
                viewModel.sortByLen()
                return true
            }
            R.id.action_sort_by_count -> {
                Toast.makeText(activity, "action_sort_by_count", Toast.LENGTH_SHORT).show()
                Log.d("DataRepository", "action_sort_by_count")
                viewModel.sortByCount()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}