package com.redmechax00.astonintensivecourseproject.domain.models.tab

data class LocationDomainModel(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<String>,
    var url: String,
    var created: String
)