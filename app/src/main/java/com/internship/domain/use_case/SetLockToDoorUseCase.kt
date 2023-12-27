package com.internship.domain.use_case

import com.internship.domain.repository.InternRepository
import javax.inject.Inject

class SetLockToDoorUseCase @Inject constructor(
    private val repository: InternRepository
) {

    suspend operator fun invoke(doorId: Int, lock: Boolean) {
        repository.setLockToDoor(doorId = doorId, lock = lock)
    }

}