package com.redmechax00.astonintensivecourseproject.di.modules

import android.content.Context
import com.redmechax00.astonintensivecourseproject.data.local.dao.AppDao
import com.redmechax00.astonintensivecourseproject.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DaoModule {

    companion object {

        @Singleton
        @Provides
        fun provideAppDao(context: Context): AppDao {
            return AppDatabase.getDatabase(context).dao()
        }
    }
}