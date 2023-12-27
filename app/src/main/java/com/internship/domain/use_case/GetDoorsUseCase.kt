package com.internship.domain.use_case

import com.internship.domain.model.Door
import com.internship.domain.repository.InternRepository
import javax.inject.Inject

class GetDoorsUseCase @Inject constructor(
    private val repository: InternRepository
) {

    suspend operator fun invoke(): List<Door> {
        return repository.getDoors()
    }

}