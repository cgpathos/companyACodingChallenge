package today.pathos.companya.codingtest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import today.pathos.companya.codingtest.R
import today.pathos.companya.codingtest.databinding.ItemThumbnailBinding
import today.pathos.companya.codingtest.domain.entity.SearchItem

class SearchResultAdapter : RecyclerView.Adapter<SearchItemViewHolder>() {
    val clickSubject: Subject<SearchItem> = PublishSubject.create<SearchItem>().toSerialized()

    var searchResultList: List<SearchItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val bnd = DataBindingUtil.inflate<ItemThumbnailBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_thumbnail, parent, false
        )
        return SearchItemViewHolder(bnd, clickSubject)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(searchResultList[position])
    }

    override fun getItemCount(): Int = searchResultList.size
}