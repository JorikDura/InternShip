package com.internship.domain.use_case

import com.internship.domain.model.Camera
import com.internship.domain.repository.InternRepository
import javax.inject.Inject

class GetCamsUseCase @Inject constructor(
    private val repository: InternRepository
) {

    suspend operator fun invoke(fetchFromRemote: Boolean = false): List<Camera> {
        return repository.getCams(fetchFromRemote)
    }

}