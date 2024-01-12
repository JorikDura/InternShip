package com.internship.data.remote

import com.internship.BuildConfig

object HttpRoutes {
    const val CAMERAS = "${BuildConfig.BASE_URL}${BuildConfig.API_CAMERAS}"
    const val DOORS = "${BuildConfig.BASE_URL}${BuildConfig.API_DOORS}"
}