package com.example.ratagamerapp.di

import android.content.Context
import com.example.ratagamerapp.data.db.GiveawayDao
import com.example.ratagamerapp.data.db.GiveawayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGiveawayDatabase(@ApplicationContext context: Context):GiveawayDatabase{
return GiveawayDatabase.getInstance(context)
    }

@Singleton
@Provides
fun providGiveawayDao(database: GiveawayDatabase):GiveawayDao{
    return database.giveawayDao()
    }


}