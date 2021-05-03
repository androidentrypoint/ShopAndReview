package com.example.shopandreview.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopandreview.room.dao.ProductDao
import com.example.shopandreview.room.entity.ProductEntity
import com.example.shopandreview.room.entity.ReviewEntity

@Database(
    entities = [ProductEntity::class, ReviewEntity::class],
    version = 1
)
abstract class SarDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: SarDatabase? = null

        fun getInstance(context: Context): SarDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        )
                            .also {
                                INSTANCE = it
                            }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SarDatabase::class.java,
                "SarDB"
            )
                .build()
    }


}