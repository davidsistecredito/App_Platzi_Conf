package com.sistecredito.conf.view.adapter

import com.sistecredito.conf.model.Conference

interface ScheduleListener {
    fun onConferenceClicked(conference: Conference, position: Int)
}