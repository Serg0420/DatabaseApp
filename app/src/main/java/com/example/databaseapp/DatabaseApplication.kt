package com.example.databaseapp

import android.app.Application
import android.content.Context
import androidx.room.Room

class DatabaseApplication : Application() {
    private var _showDatabase: ShowDatabase? = null
    val showDatabase get() = requireNotNull(_showDatabase)

    override fun onCreate() {
        super.onCreate()
        _showDatabase = Room
            .databaseBuilder(
                this,
                ShowDatabase::class.java,
                "database_of_shows"
            )
            .allowMainThreadQueries()
            .build()
    }
}

val Context.showDatabase: ShowDatabase
    get() = when (this) {
        is DatabaseApplication -> showDatabase
        else -> applicationContext.showDatabase
    }