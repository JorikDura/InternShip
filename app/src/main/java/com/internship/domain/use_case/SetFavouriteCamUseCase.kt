package com.internship.domain.use_case

import com.internship.domain.repository.InternRepository
import javax.inject.Inject

class SetFavouriteCamUseCase @Inject constructor(
    private val repository: InternRepository
) {

    suspend operator fun invoke(camId: Int, favourite: Boolean) {
        repository.setFavouriteCam(camId = camId, favourite = favourite)
    }

}