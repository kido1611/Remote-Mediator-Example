package id.kido1611.example.remotemediator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.kido1611.example.remotemediator.data.local.dao.MovieDao
import id.kido1611.example.remotemediator.data.local.dao.RemoteKeyDao
import id.kido1611.example.remotemediator.data.local.model.MovieEntity
import id.kido1611.example.remotemediator.data.local.model.RemoteKey

@Database(
    entities = [
        MovieEntity::class,
        RemoteKey::class
    ],
    version = 14,
    exportSchema = false
)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}