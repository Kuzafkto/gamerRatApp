package com.example.ratagamerapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratagamerapp.data.api.GiveawayApiRepository
import com.example.ratagamerapp.data.repository.Giveaway
import com.example.ratagamerapp.data.repository.GiveawayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ViewModelGiveAwayList @Inject constructor(private val repository: GiveawayRepository): ViewModel() {

    private val _uiState = MutableStateFlow(GiveawayListUIState(listOf()))
    val uiState: StateFlow<GiveawayListUIState>
        get() = _uiState.asStateFlow()

    init {

        viewModelScope.launch {
            try {
                repository.refreshList()
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message!!)
            }
        }

        viewModelScope.launch {
            repository.giveaway.collect {
                _uiState.value = GiveawayListUIState(it)
            }
        }

    }
suspend fun updateValueChecker(give: Giveaway){
    repository.updateValueChecker(give)
}

}
