package com.redmechax00.astonintensivecourseproject.data.remote.models.tabs

data class CharactersResponseDto(
    var info: InfoResponseDto,
    var results: List<CharacterResponseDto>
) {

    data class InfoResponseDto(
        var count: Int,
        var pages: Int,
        var next: String?,
        var prev: String?
    )

    data class CharacterResponseDto(
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
}
