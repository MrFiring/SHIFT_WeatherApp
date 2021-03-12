package ru.mrfiring.shiftweatherapp.domain

class GetCountriesLiveDataUseCase(private val countriesRepository: CountriesRepository) {
    operator fun invoke() = countriesRepository.getCountriesLiveData()
}