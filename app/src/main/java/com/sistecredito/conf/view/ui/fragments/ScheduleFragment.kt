package com.sistecredito.conf.view.ui.fragments

import android.os.Bundle
import android.telecom.Conference
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sistecredito.conf.R
import com.sistecredito.conf.view.adapter.ScheduleAdapter
import com.sistecredito.conf.view.adapter.ScheduleListener
import com.sistecredito.conf.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_shedule.*

/**
 * A simple [Fragment] subclass.
 */
class ScheduleFragment : Fragment(), ScheduleListener{
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shedule, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        viewModel.refresh()

        scheduleAdapter = ScheduleAdapter(this)

        rvSchedule.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = scheduleAdapter
        }
        observeViewModel()
    }
    fun observeViewModel() {
        viewModel.listSchedule.observe(viewLifecycleOwner, { schedule ->
            scheduleAdapter.updateData(schedule)
        })

        viewModel.isLoading.observe(this, Observer<Boolean> {
            if(it != null)
                rlBaseSchedule.visibility = View.INVISIBLE
        })
    }

    override fun onConferenceClicked(
        conference: com.sistecredito.conf.model.Conference,
        position: Int
    ) {
        val bundle = bundleOf("conference" to conference)
        findNavController().navigate(R.id.scheduleDetailFragmentDialog, bundle)
    }

}