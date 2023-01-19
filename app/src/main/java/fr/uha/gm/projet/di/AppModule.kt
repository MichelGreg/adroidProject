package fr.uha.gm.projet.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.uha.gm.projet.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase (@ApplicationContext appContext : Context) = AppDatabase.create(appContext)

    @Singleton
    @Provides
    fun provideConseilDao (db : AppDatabase) = db.getConseilDao()

    @Singleton
    @Provides
    fun provideJourDao (db : AppDatabase) = db.getJourDao()

}