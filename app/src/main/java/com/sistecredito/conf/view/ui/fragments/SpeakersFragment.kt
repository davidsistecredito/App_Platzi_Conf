package com.sistecredito.conf.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sistecredito.conf.R
import com.sistecredito.conf.model.Speaker
import com.sistecredito.conf.view.adapter.SpeakerAdapter
import com.sistecredito.conf.view.adapter.SpeakerListener
import com.sistecredito.conf.viewmodel.SpeakerViewModel
import kotlinx.android.synthetic.main.fragment_speakers.*

class SpeakersFragment : Fragment(), SpeakerListener {
    private lateinit var speakerAdapter: SpeakerAdapter
    private val viewModel: SpeakerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View? {
        return inflater.inflate(R.layout.fragment_speakers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh()

        speakerAdapter = SpeakerAdapter(this)

        rvSpeakers.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter  =speakerAdapter
        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.listSpeaker.observe(viewLifecycleOwner, { speakers ->
            speakers.let {
                speakerAdapter.updateData(speakers)
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it != null)
                rlBase.visibility = View.INVISIBLE
        })
    }

    override fun onSpeakerClicked(speaker: Speaker, position: Int) {
        val bundle = bundleOf("speaker" to speaker)
        findNavController().navigate(R.id.speakerDetailFragmentDialog, bundle)
    }

}