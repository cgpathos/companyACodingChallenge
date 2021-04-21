package today.pathos.companya.codingtest.presentation.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.presentation.adapter.SearchResultAdapter

@BindingAdapter("app:listData", requireAll = true)
fun setRecyclerViewData(recyclerView: RecyclerView, data: List<SearchItem>) {
    val adapter = recyclerView.adapter as SearchResultAdapter
    adapter.searchResultList = data
}

@BindingAdapter("android:src")
fun setSrc(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .centerCrop()
        .into(imageView)
}