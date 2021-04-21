package today.pathos.companya.codingtest.presentation.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.widget.editorActionEvents
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import today.pathos.companya.codingtest.R
import today.pathos.companya.codingtest.core.extension.addTo
import today.pathos.companya.codingtest.core.extension.toastIt
import today.pathos.companya.codingtest.databinding.ActivityMainBinding
import today.pathos.companya.codingtest.presentation.adapter.MainPagerAdapter
import today.pathos.companya.codingtest.presentation.viewmodel.MainViewModel
import today.pathos.companya.codingtest.presentation.viewmodel.MainViewState

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val TOAST_DURATION = 1000L
    }

    private val viewModel: MainViewModel by viewModels()
    private val disposableBag = CompositeDisposable()

    private lateinit var bnd: ActivityMainBinding
    private lateinit var pagerAdapter: MainPagerAdapter
    private lateinit var pageChangeCallback: ViewPager2.OnPageChangeCallback

    private val backButtonSubject: Subject<Long> = BehaviorSubject.createDefault(0L).toSerialized()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
    }

    override fun onDestroy() {
        bnd.pagerMain.unregisterOnPageChangeCallback(pageChangeCallback)

        if (!disposableBag.isDisposed) {
            disposableBag.dispose()
        }

        super.onDestroy()
    }

    override fun onBackPressed() {
        backButtonSubject.onNext(System.currentTimeMillis())
    }

    private fun initView() {
        viewModel.viewState.observe(this, { handleViewState(it) })

        backButtonSubject.toFlowable(BackpressureStrategy.BUFFER)
            .buffer(2, 1)
            .map { it[0] to it[1] }
            .subscribe { t ->
                if (t.second - t.first < TOAST_DURATION) {
                    viewModel.purgeData()
                } else {
                    toastIt(getString(R.string.main_exit_msg))
                }
            }
            .addTo(disposableBag)

        bnd.etSearch.editorActionEvents()
            .subscribe {
                if (it.actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchImage(it.view.text.toString())
                    it.view.clearFocus()
                    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        it.view.windowToken,
                        0
                    )

                }
            }
            .addTo(disposableBag)

        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bnd.etSearch.isEnabled = position == 0
            }
        }

        pagerAdapter = MainPagerAdapter(this)
        bnd.pagerMain.adapter = pagerAdapter
        bnd.pagerMain.registerOnPageChangeCallback(pageChangeCallback)

        TabLayoutMediator(bnd.tabMain, bnd.pagerMain) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.main_search)
            } else {
                tab.text = getString(R.string.main_favorite)
            }
        }.attach()
    }

    private fun handleViewState(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.Loading -> {
                bnd.layoutLoading.visibility = View.VISIBLE
            }
            is MainViewState.Success -> {
                bnd.layoutLoading.visibility = View.GONE
            }
            is MainViewState.SuccessAddFavorite -> {
                bnd.layoutLoading.visibility = View.GONE
                toastIt(getString(R.string.main_add_favorite_success))
            }
            is MainViewState.SuccessDelFavorite -> {
                bnd.layoutLoading.visibility = View.GONE
                toastIt(getString(R.string.main_del_favorite_success))
            }
            is MainViewState.Error -> {
                bnd.layoutLoading.visibility = View.GONE
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage(viewState.error.message ?: "알 수 없는 에러입니다.")
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.main_alert_confirm), null)
                    .show()
            }
            is MainViewState.FinishApp -> {
                finish()
            }
        }
    }
}
