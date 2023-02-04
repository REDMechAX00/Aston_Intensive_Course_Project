package com.redmechax00.astonintensivecourseproject.data.remote.models.details

data class CharacterDetailsResponseDto(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: OriginCharacterDetailsDto,
    var location: LocationCharacterDetailsDto,
    var image: String,
    var episode: List<String>,
    var url: String,
    var created: String
) {
    data class OriginCharacterDetailsDto(
        var name: String,
        var url: String
    )

    data class LocationCharacterDetailsDto(
        var name: String,
        var url: String
    )
}
