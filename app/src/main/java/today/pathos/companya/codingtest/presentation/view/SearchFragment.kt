package today.pathos.companya.codingtest.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import today.pathos.companya.codingtest.R
import today.pathos.companya.codingtest.core.extension.addTo
import today.pathos.companya.codingtest.databinding.FragmentSearchBinding
import today.pathos.companya.codingtest.presentation.adapter.SearchResultAdapter
import today.pathos.companya.codingtest.presentation.viewmodel.MainViewModel
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private val disposableBag = CompositeDisposable()

    private lateinit var bnd: FragmentSearchBinding
    private lateinit var listAdapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        bnd.lifecycleOwner = this
        bnd.vm = viewModel
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = SearchResultAdapter()
        bnd.listSearch.adapter = listAdapter

        listAdapter.clickSubject
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe { viewModel.addFavorite(it) }
            .addTo(disposableBag)
    }

    override fun onDestroy() {
        if (!disposableBag.isDisposed) {
            disposableBag.dispose()
        }

        super.onDestroy()
    }
}
