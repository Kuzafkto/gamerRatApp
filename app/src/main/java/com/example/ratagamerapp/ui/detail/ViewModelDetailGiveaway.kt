package com.example.ratagamerapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratagamerapp.data.db.CommentEntity
import com.example.ratagamerapp.data.repository.Giveaway
import com.example.ratagamerapp.data.repository.GiveawayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelDetailGiveaway @Inject constructor(private val repository: GiveawayRepository): ViewModel() {
//injectas el constructor del repositorio que vas a usar para traerte el detail

    //usamos uiState para establecerlo como un observable de un Giveaway
    //y commentState para controlar los comentarios
    private val _commentsUiState = MutableStateFlow<List<CommentEntity>?>(null)
    val commentsUiState: StateFlow<List<CommentEntity>?>
        get() = _commentsUiState.asStateFlow()

    //usamos uiState para establecerlo como un observable de un Giveaway
    private val _uiState = MutableStateFlow(Giveaway())
    val uiState: StateFlow<Giveaway>
        get() = _uiState.asStateFlow()

    suspend fun addComment(comment: CommentEntity){
        withContext(Dispatchers.IO) {
            repository.postComment(comment)
        }
    }

    //creamos una funcion que va a utilizar el getDetail del repository y actualizará el valor del mutableStateFlow
    fun fetchGiveawayDetail(id: Int) {
        viewModelScope.launch {

            //asignamos una variable al get del repositorio
            val give=repository.getDetail(id)

            //ahora con collect obtenemos el observable y le asignamos sus valoes al behavioursubject
            give.collect {
               _uiState.value = Giveaway(
                   it.giveaway.id,
                   it.giveaway.title,
                   it.giveaway.description,
                   it.giveaway.thumbnail,
                   it.giveaway.image,
                   it.giveaway.inValueChecker,
                   it.giveaway.worth
               )
            }
        }
    }


    fun setComments(gameId: Int) {
        viewModelScope.launch {
            repository.getDetail(gameId).collect {
                //actualiza el observable con la funcion del repository
                _commentsUiState.value = it.gameComments
            }
        }

    }


}