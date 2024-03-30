package com.example.roomdatabasekotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.roomdatabasekotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var database: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // this provides an instance of database (getDatabase method can be found in ContactDatabase class).
        database = ContactDatabase.getDatabase(this)

        // this launches it on a separate thread.
        GlobalScope.launch {
            database.contactDao().insertContact(Contact(0, "XAIHOF", "70000", Date(), 1))
        }

        binding.button.setOnClickListener {
            database.contactDao().getContact().observe(this, Observer {
                Log.d("XAIHOF", it.toString())
            })
        }

    }
}