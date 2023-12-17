package com.example.ratagamerapp.ui.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.example.ratagamerapp.data.repository.Giveaway
import com.example.ratagamerapp.databinding.ActivityMainBinding
import com.example.ratagamerapp.databinding.GiveawayListItemBinding
import kotlinx.coroutines.launch

//este adapter se encarga de estructurar el template de cada item dentro del recycler view
class AdapterGiveawayList(private val context: Context,private val viewModel:ViewModelGiveAwayList, private val lifecycleScope: LifecycleCoroutineScope
) :
//usa un listAdapter que es una clase de kotlin
    ListAdapter<Giveaway, AdapterGiveawayList.GiveawayViewHolder>(GiveawayDiffCallBack) {

        //llama al binding de ese template del item
    @SuppressLint("SuspiciousIndentation")
    inner class GiveawayViewHolder(private val binding: GiveawayListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                //les hace en el init un setOnlcick listener para poder navegar con el navController pasando como parametro el id del juego seleccionado
                binding.btnGiveaway.setOnClickListener {
                  val action= FragmentGiveAwayListDirections.
                  actionGiveawaysListFragmentToDetailFragmentGiveaway(getItem(adapterPosition).id)
                    itemView.findNavController().navigate(action)
                }
                binding.btnValueChecker.setOnClickListener {
                    val giveawayId = getItem(adapterPosition).id

                    // Llama a la función del ViewModel dentro de la coroutine
                    lifecycleScope.launch {
                        viewModel.updateValueChecker(getItem(adapterPosition))
                    }
                }
            }

            //bind se encarga de de hacer el binding del titulo y la imagen usando binding e imageRequest
        fun bind(giveaway: Giveaway) {
            binding.giveawayTitle.text = giveaway.title

            val thumbnailRequest = ImageRequest.Builder(context)
                .data(giveaway.image)
                .crossfade(true)
                .target(binding.giveawayThumbnail)
                .build()

            context.imageLoader.enqueue(thumbnailRequest)
        }

    }

    private object GiveawayDiffCallBack : DiffUtil.ItemCallback<Giveaway>() {
        override fun areItemsTheSame(oldItem: Giveaway, newItem: Giveaway) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Giveaway, newItem: Giveaway) = oldItem == newItem
    }
//oncreateview hace un binding con el listviewholder y lo infla
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveawayViewHolder =
        GiveawayViewHolder(
            GiveawayListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: GiveawayViewHolder, position: Int) =
        holder.bind(getItem(position))


}