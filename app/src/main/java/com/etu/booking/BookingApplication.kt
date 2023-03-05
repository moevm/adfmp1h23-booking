package com.etu.booking

import android.app.Application
import com.etu.booking.dependecyinjection.context.RepositoryContext
import com.etu.booking.dependecyinjection.context.impl.DefaultRepositoryContext

class BookingApplication : Application() {

    lateinit var repositoryContext: RepositoryContext

    override fun onCreate() {
        super.onCreate()
        repositoryContext = DefaultRepositoryContext(context = this)
    }
}
