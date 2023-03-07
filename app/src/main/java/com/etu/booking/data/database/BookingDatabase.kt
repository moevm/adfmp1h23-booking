package com.etu.booking.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.etu.booking.data.constant.BOOKING_DATABASE_NAME
import com.etu.booking.data.dao.HistoryDao
import com.etu.booking.data.dao.HotelDao
import com.etu.booking.data.dao.LocationDao
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
    ],
    version = 6,
)
abstract class BookingDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun hotelDao(): HotelDao
    abstract fun locationDao(): LocationDao

    companion object {
        private const val LOG_TAG = "SQL"
        private const val MIGRATION_PATH = "database/migration/"
        private const val MIGRATION_FILENAME_1_2 = "V1_add_hotels.sql"
        private const val MIGRATION_FILENAME_2_3 = "V2_add_people.sql"
        private const val MIGRATION_FILENAME_3_4 = "V3_add_locations.sql"
        private const val MIGRATION_FILENAME_4_5 = "V4_add_histories.sql"
        private const val MIGRATION_FILENAME_5_6 = "V5_add_facilities.sql"

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
                .addMigrations(
                    getMigration(context, 1, 2, MIGRATION_FILENAME_1_2),
                    getMigration(context, 2, 3, MIGRATION_FILENAME_2_3),
                    getMigration(context, 3, 4, MIGRATION_FILENAME_3_4),
                    getMigration(context, 4, 5, MIGRATION_FILENAME_4_5),
                    getMigration(context, 5, 6, MIGRATION_FILENAME_5_6),
                )
                .build()
                .also { INSTANCE = it }
        }

        private fun getMigration(
            context: Context,
            startVersion: Int,
            endVersion: Int,
            migrationName: String,
        ) = Migration(startVersion, endVersion) {
            val sqlQuery = context.getAssetsFileAsString(fileName = migrationName)
            Log.d(LOG_TAG, sqlQuery)

            Log.i(LOG_TAG, "Migration from $startVersion to $endVersion started")
            it.executeSqlWithTransaction(sqlQuery)
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
                Log.i(LOG_TAG, "Migration successfully applied")
            } catch (expectedException: Exception) {
                Log.e(LOG_TAG, "Migration failed", expectedException)
            } finally {
                endTransaction()
            }
        }
    }
}
