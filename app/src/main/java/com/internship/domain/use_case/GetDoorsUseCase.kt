package com.internship.domain.use_case

import com.internship.domain.model.Door
import com.internship.domain.repository.InternRepository
import com.internship.utils.Resource
import javax.inject.Inject

class GetDoorsUseCase @Inject constructor(
    private val repository: InternRepository
) {

    suspend operator fun invoke(fetchFromRemote: Boolean = false): Resource<List<Door>> {
        return repository.getDoors(fetchFromRemote)
    }

}