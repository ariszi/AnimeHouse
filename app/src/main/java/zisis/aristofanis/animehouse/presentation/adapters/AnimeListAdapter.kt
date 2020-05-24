package zisis.aristofanis.animehouse.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_anime_item.view.*
import timber.log.Timber
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.presentation.utils.inflate

class AnimeListAdapter(private val animeList: List<Anime>) : RecyclerView.Adapter<AnimeListAdapter.AnimeListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListHolder {
        val inflatedView = parent.inflate(R.layout.list_anime_item, false)
        return AnimeListHolder(inflatedView)
    }

    override fun getItemCount(): Int = animeList.size

    override fun onBindViewHolder(holder: AnimeListHolder, position: Int) {
        val itemAnime = animeList[position]
        holder.bindAnime(itemAnime)
    }

    class AnimeListHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            Timber.d("AnimeListAdapter CLICK!")
        }
        fun bindAnime(anime: Anime) {
            Glide.with(view.context)
                .load(anime.image)
                .centerCrop()
                .placeholder(R.drawable.ic_not_available)
                .into(view.image)

            view.title.text = anime.title.romaji
            view.genre.text = anime.genres.toString()
            view.description.text = anime.description
        }

    }

}

