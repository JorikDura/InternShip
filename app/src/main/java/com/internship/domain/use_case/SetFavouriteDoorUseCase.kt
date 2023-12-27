package com.internship.domain.use_case

import com.internship.domain.repository.InternRepository
import javax.inject.Inject

class SetFavouriteDoorUseCase @Inject constructor(
    private val repository: InternRepository
) {

    suspend operator fun invoke(doorId: Int, favourite: Boolean) {
        repository.setFavouriteDoor(doorId = doorId, favourite = favourite)
    }

}