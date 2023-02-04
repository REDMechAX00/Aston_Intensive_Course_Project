package com.redmechax00.astonintensivecourseproject.data.remote.models.details

data class AllCharactersDetailsResponseDto(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: OriginResponseDto,
    var location: LocationResponseDto,
    var image: String,
    var episode: List<String>,
    var url: String,
    var created: String
) {

    data class OriginResponseDto(
        var name: String,
        var url: String
    )

    data class LocationResponseDto(
        var name: String,
        var url: String
    )
}
