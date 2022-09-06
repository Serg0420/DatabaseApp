package com.example.databaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //компонент, содержащий список всех фрагментов на activity
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, ShowsLstFragment())
            .commit()

    }
}