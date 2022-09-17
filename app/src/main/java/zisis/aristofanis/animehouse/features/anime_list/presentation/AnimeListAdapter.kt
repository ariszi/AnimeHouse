package zisis.aristofanis.animehouse.features.anime_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.databinding.ListAnimeItemBinding
import zisis.aristofanis.animehouse.features.anime_list.domain.models.Anime

class AnimeListAdapter(private val action: (AnimeListContract.AnimesEvent.AnimePressed) -> Unit) :
    ListAdapter<Anime, AnimeListAdapter.AnimeListHolder>(AnimeDiffs()) {

    private lateinit var binding: ListAnimeItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListHolder {
        binding = ListAnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeListHolder, position: Int) {
        holder.bind(getItem(position), action)
    }

    class AnimeListHolder(private val binding: ListAnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageBinding = binding.image
        private val titleBinding = binding.title
        private val descriptionBinding = binding.description
        private val genreBinding = binding.genre
        private val viewBinding = binding.root
        fun bind(
            anime: Anime,
            action: (AnimeListContract.AnimesEvent.AnimePressed) -> Unit
        ) = with(binding.root) {
            Glide.with(binding.root.context)
                .load(anime.image)
                .centerCrop()
                .placeholder(R.drawable.ic_not_available)
                .into(imageBinding)

            titleBinding.text = anime.title.english
            genreBinding.text = anime.genres.toString()
            descriptionBinding.text = anime.description
            viewBinding.setOnClickListener { action(AnimeListContract.AnimesEvent.AnimePressed(anime)) }
        }
    }

    class AnimeDiffs : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return (oldItem.averageScore == newItem.averageScore) ||
                    (oldItem.episodes == newItem.episodes) ||
                    (oldItem.trending == newItem.trending) ||
                    (oldItem.title.english == newItem.title.english)
        }
    }

}


