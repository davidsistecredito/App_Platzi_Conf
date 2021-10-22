package com.sistecredito.conf.view.adapter
import com.sistecredito.conf.model.Speaker

interface SpeakerListener {
    fun onSpeakerClicked(speaker: Speaker, position: Int)
}