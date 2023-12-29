package com.internship.domain.use_case

import com.internship.domain.repository.InternRepository
import javax.inject.Inject

class GetRoomsUseCase @Inject constructor(
    private val repository: InternRepository
) {

    suspend operator fun invoke(fetchFromRemote: Boolean = false): List<String> {
        return repository.getRooms(fetchFromRemote)
    }

}