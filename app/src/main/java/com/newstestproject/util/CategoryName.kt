package com.newstestproject.util

import com.newstestproject.R
import com.newstestproject.core.util.UiText

enum class CategoryName(val localizedName: UiText) {
    general(UiText.StringResource(R.string.general_category)),
    business(UiText.StringResource(R.string.business_category)),
    entertainment(UiText.StringResource(R.string.entertainment_category)),
    health(UiText.StringResource(R.string.health_category)),
    science(UiText.StringResource(R.string.science_category)),
    sports(UiText.StringResource(R.string.sports_category)),
    technology(UiText.StringResource(R.string.technology_category)),
}