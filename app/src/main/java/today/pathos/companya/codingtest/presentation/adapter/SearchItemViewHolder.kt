package today.pathos.companya.codingtest.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.subjects.Subject
import today.pathos.companya.codingtest.databinding.ItemThumbnailBinding
import today.pathos.companya.codingtest.domain.entity.SearchItem

class SearchItemViewHolder(
    private val bnd: ItemThumbnailBinding,
    private val clickSubject: Subject<SearchItem>
) :
    RecyclerView.ViewHolder(bnd.root) {
    fun bind(data: SearchItem) {
        bnd.searchItem = data


        bnd.imgThumbnail.setOnLongClickListener {
            clickSubject.onNext(data)
            true
        }

        bnd.executePendingBindings()
    }
}