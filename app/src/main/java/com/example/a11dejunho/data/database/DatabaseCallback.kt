package com.example.a11dejunho.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.a11dejunho.data.entity.ClientEntity
import com.example.a11dejunho.data.entity.ProductEntity
import com.example.a11dejunho.data.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        GlobalScope.launch(Dispatchers.IO) {
            // Database initialization
            // Sample data can be added here
        }
    }
}


