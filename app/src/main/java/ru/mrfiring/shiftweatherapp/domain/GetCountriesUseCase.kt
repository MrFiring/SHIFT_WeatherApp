package ru.mrfiring.shiftweatherapp.domain

class GetCountriesUseCase(private val countriesRepository: CountriesRepository) {
    suspend operator fun invoke() = countriesRepository.getCountries()
}