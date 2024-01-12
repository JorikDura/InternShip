package com.internship.data.remote

import com.internship.BuildConfig

object HttpRoutes {
    const val CAMERAS = "${BuildConfig.BASE_URL}/cameras/"
    const val DOORS = "${BuildConfig.BASE_URL}/doors/"
}