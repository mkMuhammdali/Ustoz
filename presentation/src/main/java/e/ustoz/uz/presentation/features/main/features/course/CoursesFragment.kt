package e.ustoz.uz.presentation.features.main.features.course

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import dagger.Lazy
import e.ustoz.data.model.teacher_information.online_lesson.OnlineLessonResponse
import e.ustoz.uz.R
import e.ustoz.uz.presentation.features.main.features.common.colorAccent
import e.ustoz.uz.presentation.features.main.features.common.setTint
import e.ustoz.uz.presentation.features.main.features.delegate.OnlineLessonsListRecyclerViewDelegate
import e.ustoz.uz.support.delegate.view.ToolbarDelegate
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import e.ustoz.uz.databinding.FragmentFeatureUserCoursesBinding as ViewBinding

class CoursesFragment @Inject constructor() :
    MvpAppCompatFragment(R.layout.fragment_feature_user_courses),
    CoursesView, Toolbar.OnMenuItemClickListener {
    @Inject
    internal lateinit var lazyPresenter: Lazy<CoursesPresenter>
    private val presenter by moxyPresenter { lazyPresenter.get() }

    private lateinit var binding: ViewBinding
    private val toolbarDelegate by lazy { ToolbarDelegate(this) }
    private val onlineLessonsListRecyclerViewDelegate by lazy {
        OnlineLessonsListRecyclerViewDelegate(this) { Toast.makeText(requireContext(),"Clicked ${it.title}",Toast.LENGTH_LONG).show() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ViewBinding.bind(view)
        binding.apply {
            toolbarDelegate.apply {
                onCreate(toolbar, savedInstanceState)
                inflateMenu(R.menu.menu_main_screen_toolbar, this@CoursesFragment)
                findMenuItemById(R.id.search_menu_item)?.setTint(requireContext().colorAccent)
            }
            onlineLessonsListRecyclerViewDelegate
                .onCreate(binding.onlineLessonsRecyclerViewHorizontal, savedInstanceState)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.search_menu_item -> testMehodToast("SEARCH").let { true }
        R.id.user_info_menu_item -> presenter.navigateToUserInfo().let { true }
        else -> false
    }

    private fun testMehodToast(value: String) {
        Toast.makeText(requireContext(), "$value MENU CLICKED", Toast.LENGTH_LONG).show()
    }

    override fun onLoading() {
    }

    override fun onSuccess(list: List<OnlineLessonResponse>) {
        onlineLessonsListRecyclerViewDelegate.apply {
            clear(); onSuccess(list)
        }
    }

    override fun onFailure(throwable: Throwable) {
    }
}