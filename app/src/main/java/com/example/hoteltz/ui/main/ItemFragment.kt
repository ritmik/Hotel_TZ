package com.example.hoteltz.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.hoteltz.R
import com.example.hoteltz.data.*

/**
 * @author Mustafin Rufat (@mustafin)
 */
class ItemFragment : Fragment() {

    private lateinit var viewModel: ItemViewModel
    private lateinit var viewFragment: View

    private lateinit var idView: TextView
    private lateinit var nameView: TextView
    private lateinit var addressView: TextView
    private lateinit var starsView: TextView
    private lateinit var distanceView: TextView
    private lateinit var imageTextView: TextView
    private lateinit var suitesAvailabilityView: TextView
    private lateinit var latView: TextView
    private lateinit var lonView: TextView
    private lateinit var noDataTextView: TextView
    private lateinit var progressSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewGroup: View
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewFragment = inflater.inflate(R.layout.fragment_foto_item, container, false)

        idView = viewFragment.findViewById(R.id.foto_item_id)
        nameView = viewFragment.findViewById(R.id.foto_item_name)
        addressView = viewFragment.findViewById(R.id.foto_item_address)
        starsView = viewFragment.findViewById(R.id.foto_item_stars)
        distanceView = viewFragment.findViewById(R.id.foto_item_distance)
        imageTextView = viewFragment.findViewById(R.id.foto_item_image)
        suitesAvailabilityView = viewFragment.findViewById(R.id.foto_item_suites_availability)
        latView = viewFragment.findViewById(R.id.foto_item_lat)
        lonView = viewFragment.findViewById(R.id.foto_item_lon)
        noDataTextView = viewFragment.findViewById(R.id.foto_item_text_no_data)
        progressSwipeRefreshLayout =
            viewFragment.findViewById(R.id.progressSwipeRefreshLayout_detail)
        viewGroup = viewFragment.findViewById(R.id.foto_item_group)
        imageView = viewFragment.findViewById(R.id.detail_imageView)

        progressSwipeRefreshLayout.isEnabled = true
        progressSwipeRefreshLayout.isRefreshing = true

        return viewFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val item = HotelData(
            arguments?.getInt("id") ?: 0,
            arguments?.getString("name") ?: "empty name",
            arguments?.getString("address") ?: "empty address",
            arguments?.getFloat("stars") ?: 0F,
            arguments?.getFloat("distance") ?: 0F,
            arguments?.getString("suitesAvailability") ?: "empty suites Availability",
        )

        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        viewModel.fetchHotelById(item.id)

        viewModel.datailHotelById.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewGroup.visibility = View.VISIBLE
                noDataTextView.visibility = View.GONE
                //val v = Util.convertDetail(it)
                val v = convertDetail(it)
                idView.text = v.id
                nameView.text = v.name
                addressView.text = v.address
                starsView.text = v.stars
                distanceView.text = v.distance
                imageTextView.text = v.image
                suitesAvailabilityView.text = v.suites_availability
                latView.text = v.lat
                lonView.text = v.lon

                Glide
                    .with(this)
                    .load("https://github.com/iMofas/ios-android-test/raw/master/" + it.image)
                    .into(imageView)

            } else {
                viewGroup.visibility = View.GONE
                noDataTextView.visibility = View.VISIBLE
            }

            progressSwipeRefreshLayout.isRefreshing = false
            progressSwipeRefreshLayout.isEnabled = false //обновление только при запуске приложения
        })
    }
}