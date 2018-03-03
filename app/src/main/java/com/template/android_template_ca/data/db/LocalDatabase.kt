package com.template.android_template_ca.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import com.template.android_template_ca.data.db.dao.UserDao
import com.template.android_template_ca.domain.models.User

@Database(version = 1, entities = [User::class])
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract val userDao: UserDao

}