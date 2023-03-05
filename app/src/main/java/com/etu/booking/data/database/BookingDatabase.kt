package com.etu.booking.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.etu.booking.data.constant.BOOKING_DATABASE_NAME
import com.etu.booking.data.dao.HotelDao
import com.etu.booking.data.entity.HotelEntity

@Database(
    entities = [HotelEntity::class],
    version = 2,
)
abstract class BookingDatabase : RoomDatabase() {

    abstract fun hotelDao(): HotelDao

    companion object {
        private const val LOG_TAG = "SQL"
        private const val MIGRATION_PATH = "database/migration/"
        private const val MIGRATION_FILENAME_1_2 = "V1_add_hotels.sql"

        @Volatile
        private var INSTANCE: BookingDatabase? = null

        fun getDatabase(context: Context): BookingDatabase = synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context = context,
                klass = BookingDatabase::class.java,
                name = BOOKING_DATABASE_NAME,
            )
                .addMigrations(
                    getMigration(context, 1, 2, MIGRATION_FILENAME_1_2)
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
