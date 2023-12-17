package com.example.ratagamerapp.ui.list.valueChecker

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.example.ratagamerapp.data.db.GiveawayEntity
import com.example.ratagamerapp.data.repository.Giveaway
import com.example.ratagamerapp.databinding.ActivityMainBinding
import com.example.ratagamerapp.databinding.GiveawayListItemBinding
import com.example.ratagamerapp.databinding.ValueCheckerListItemBinding
import com.example.ratagamerapp.ui.list.FragmentGiveAwayListDirections

//este adapter se encarga de estructurar el template de cada item dentro del recycler view
class AdapterValueCheckerList(private val context: Context) :
//usa un listAdapter que es una clase de kotlin
    ListAdapter<GiveawayEntity, AdapterValueCheckerList.ValueCheckerViewHolder>(GiveawayDiffCallBack) {

        //llama al binding de ese template del item
    @SuppressLint("SuspiciousIndentation")
    inner class ValueCheckerViewHolder(private val binding: ValueCheckerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            //bind se encarga de de hacer el binding del titulo y la imagen usando binding e imageRequest
        fun bind(giveaway: GiveawayEntity) {
            binding.giveawayTitle.text = giveaway.title

            val thumbnailRequest = ImageRequest.Builder(context)
                .data(giveaway.image)
                .crossfade(true)
                .target(binding.giveawayThumbnail)
                .build()

            context.imageLoader.enqueue(thumbnailRequest)

                binding.giveawayWorth.text=giveaway.worth
        }

    }

    private object GiveawayDiffCallBack : DiffUtil.ItemCallback<GiveawayEntity>() {
        override fun areItemsTheSame(oldItem: GiveawayEntity, newItem: GiveawayEntity) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: GiveawayEntity, newItem: GiveawayEntity) = oldItem == newItem
    }
//oncreateview hace un binding con el listviewholder y lo infla
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueCheckerViewHolder =
    ValueCheckerViewHolder(
            ValueCheckerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ValueCheckerViewHolder, position: Int) =
        holder.bind(getItem(position))




}