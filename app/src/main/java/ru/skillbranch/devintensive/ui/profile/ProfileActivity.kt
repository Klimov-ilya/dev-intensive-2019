package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }
    private lateinit var viewFields: Map<String, TextView>
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViewModel()
        initViews(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel() {
        viewModel.getProfileData().observe(this, Observer {
            updateUI(it)
        })
        viewModel.getTheme().observe(this, Observer {
            updateTheme(it)
        })
    }

    private fun updateTheme(appTheme: Int) {
        delegate.localNightMode = appTheme
    }

    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            for ((k, v) in viewFields) {
                v.text = it[k].toString()
            }
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickname" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )
        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE) ?: false
        showCurrentMode(isEditMode)
        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }
        btn_switch_theme.setOnClickListener { viewModel.switchTheme() }
    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf("firstName", "lastName", "about", "repository").contains(it.key) }
        for ((_, value) in info) {
            value as EditText
            value.isFocusable = isEdit
            value.isFocusableInTouchMode = isEdit
            value.isEnabled = isEdit
            value.background.alpha = if (isEdit) 255 else 0
        }
        ic_eye.visibility = if (isEdit) GONE else VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit) {
            val filter: ColorFilter? = if (isEdit) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }
            val icon = resources.getDrawable(
                if (isEdit) R.drawable.ic_save_black_24dp else R.drawable.ic_edit_black_24dp, theme
            )
            background.colorFilter = filter
            setImageDrawable(icon)
        }

    }

    private fun saveProfileInfo() {
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

}
