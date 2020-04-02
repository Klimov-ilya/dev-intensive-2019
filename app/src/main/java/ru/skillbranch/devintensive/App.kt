package ru.skillbranch.devintensive

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        PreferencesRepository.getAppTheme().also {
            AppCompatDelegate.setDefaultNightMode(it)
        }

    }

    companion object {
        private lateinit var instance: App

        fun applicationContext() = instance.applicationContext

    }

}