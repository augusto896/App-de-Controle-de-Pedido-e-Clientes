package com.example.a11dejunho.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a11dejunho.data.dao.ClientDao
import com.example.a11dejunho.data.dao.OrderDao
import com.example.a11dejunho.data.dao.ProductDao
import com.example.a11dejunho.data.dao.UserDao
import com.example.a11dejunho.data.entity.ClientEntity
import com.example.a11dejunho.data.entity.OrderEntity
import com.example.a11dejunho.data.entity.ProductEntity
import com.example.a11dejunho.data.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ClientEntity::class,
        ProductEntity::class,
        OrderEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun clientDao(): ClientDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { instance = it }
            }
    }
}

