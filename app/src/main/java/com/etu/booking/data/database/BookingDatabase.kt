package com.etu.booking.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.etu.booking.constant.BOOKING_DATABASE_NAME
import com.etu.booking.constant.BOOKING_LOG_TAG
import com.etu.booking.data.dao.CredentialDao
import com.etu.booking.data.dao.HistoryDao
import com.etu.booking.data.dao.HotelDao
import com.etu.booking.data.dao.LocationDao
import com.etu.booking.data.dao.PersonDao
import com.etu.booking.data.entity.CredentialEntity
import com.etu.booking.data.entity.FacilityEntity
import com.etu.booking.data.entity.HistoryEntity
import com.etu.booking.data.entity.HotelEntity
import com.etu.booking.data.entity.LocationEntity
import com.etu.booking.data.entity.PersonEntity

@Database(
    entities = [
        HotelEntity::class,
        PersonEntity::class,
        LocationEntity::class,
        HistoryEntity::class,
        FacilityEntity::class,
        CredentialEntity::class,
    ],
    version = 1,
)
abstract class BookingDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun hotelDao(): HotelDao
    abstract fun locationDao(): LocationDao
    abstract fun personDao(): PersonDao
    abstract fun credentialDao(): CredentialDao

    companion object {
        private const val MIGRATION_PATH = "database/migration/"
        private val MIGRATIONS = listOf(
            "V1_add_hotels.sql",
            "V2_add_people.sql",
            "V3_add_locations.sql",
            "V4_add_histories.sql",
            "V5_add_facilities.sql",
            "V6_add_credentials.sql",
        )

        @Volatile
        private var INSTANCE: BookingDatabase? = null

        fun getDatabase(context: Context): BookingDatabase = synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context = context,
                klass = BookingDatabase::class.java,
                name = BOOKING_DATABASE_NAME,
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigrationOnDowngrade()
                .fallbackToDestructiveMigration()
                .addCallback(
                    getPrePopulatedCallbackAfterFirstDatabaseCreating(
                        context = context,
                        fileNames = MIGRATIONS
                    )
                )
                .build()
                .also { INSTANCE = it }
        }

        private fun getPrePopulatedCallbackAfterFirstDatabaseCreating(
            context: Context,
            fileNames: List<String>,
        ): Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                fileNames.forEach { filename ->
                    val sqlQuery = context.getAssetsFileAsString(fileName = filename)
                    Log.i(BOOKING_LOG_TAG, "Migration started for file = $filename")
                    Log.d(BOOKING_LOG_TAG, sqlQuery)
                    db.executeSqlWithTransaction(sqlQuery)
                }
            }
        }

        private fun Context.getAssetsFileAsString(fileName: String): String =
            assets.open(MIGRATION_PATH + fileName)
                .bufferedReader()
                .use { it.readText() }

        private fun SupportSQLiteDatabase.executeSqlWithTransaction(sqlQuery: String) {
            beginTransaction()
            try {
                execSQL(sqlQuery)
                setTransactionSuccessful()
                Log.i(BOOKING_LOG_TAG, "Migration successfully applied")
            } catch (expectedException: Exception) {
                Log.e(BOOKING_LOG_TAG, "Migration failed", expectedException)
            } finally {
                endTransaction()
            }
        }
    }
}
