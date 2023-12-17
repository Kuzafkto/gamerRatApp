package com.example.ratagamerapp.ui.list

import com.example.ratagamerapp.data.repository.Giveaway

data class GiveawayListUIState(
    val giveaway: List<Giveaway>,
    val errorMessage: String? = null,
)
