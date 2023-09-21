package org.tbm.walletleaks.authentication.domain.useCases

class FooUseCase(private val carRepository: CarRepository) {
    operator fun invoke() = ""
}

interface CarRepository {

}