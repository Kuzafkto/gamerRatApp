package com.example.ratagamerapp.ui.list.valueChecker

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratagamerapp.data.db.GiveawayEntity
import com.example.ratagamerapp.data.repository.Giveaway
import com.example.ratagamerapp.data.repository.GiveawayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelValueCheckerList @Inject constructor(private val repository: GiveawayRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<List<GiveawayEntity>?>(null)
    val uiState: StateFlow<List<GiveawayEntity>?>
        get() = _uiState.asStateFlow()

    // Nuevo MutableStateFlow para la suma de valores 'worth'
    private val _totalWorth = MutableStateFlow(0.0)
    val totalWorth: StateFlow<Double>
        get() = _totalWorth.asStateFlow()

    init {
        viewModelScope.launch {
            repository.valueChecker.collect { giveaways ->
                _uiState.value = giveaways
                // Calcula la suma de los valores 'worth' después de aplicar la transformación
                var sum =  giveaways.sumOf { stringToWorth(it.worth) } ?: 0.0
                sum=sum.round(2)
                _totalWorth.value = sum
                Log.d("total",_totalWorth.value.toString())
            }
        }
    }
    // Función de extensión para redondear el total a dos decimales (aunque por paramtro le pasamos la cantidad)
    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }

    private fun stringToWorth(valor: String): Double {
        // Eliminar el símbolo "$" y cualquier espacio en blanco al principio o al final
        val valorLimpio = valor.trimStart('$').trim()

        // Verificar si el string es "N/A" y devolver 0 en ese caso
        if (valorLimpio == "N/A") {
            return 0.0
        }

        // Intentar convertir el string a double, manejar cualquier excepción
        return try {
            valorLimpio.toDouble()
        } catch (e: NumberFormatException) {
            // Manejar la excepción, por ejemplo, imprimir un mensaje de error y devolver 0
            e.printStackTrace()
            0.0
        }
    }
}
