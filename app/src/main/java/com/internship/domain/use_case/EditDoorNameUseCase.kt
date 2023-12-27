package com.internship.domain.use_case

import com.internship.domain.repository.InternRepository
import javax.inject.Inject

class EditDoorNameUseCase @Inject constructor(
    private val repository: InternRepository
) {

    suspend operator fun invoke(doorId: Int, newName: String) {
        repository.editDoorName(doorId = doorId, newName = newName)
    }

}