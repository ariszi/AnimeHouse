package zisis.aristofanis.animehouse.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_anime_item.view.*
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract
import zisis.aristofanis.animehouse.presentation.utils.inflate

class AnimeListAdapter(private val action: ((AnimeListContract.Event.ListItemClickIntentAction) -> Unit)) :
    ListAdapter<Anime, AnimeListAdapter.AnimeListHolder>(AnimeDiffs()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListHolder {
        val inflatedView = parent.inflate(R.layout.list_anime_item, false)
        return AnimeListHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: AnimeListHolder, position: Int) {
        holder.bind(getItem(position), action)
    }
    class AnimeListHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            anime: Anime,
            action: (AnimeListContract.Event.ListItemClickIntentAction) -> Unit
        ) = with(view) {
            Glide.with(view.context)
                .load(anime.image)
                .centerCrop()
                .placeholder(R.drawable.ic_not_available)
                .into(view.image)

            view.title.text = anime.title.english
            view.genre.text = anime.genres.toString()
            view.description.text = anime.description
            view.setOnClickListener { action(AnimeListContract.Event.ListItemClickIntentAction(anime)) }
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


