package com.redmechax00.astonintensivecourseproject.domain.models.details

data class LocationDetailsDomainModel(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<String>,
    var url: String,
    var created: String
)