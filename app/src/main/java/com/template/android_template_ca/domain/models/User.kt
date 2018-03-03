package com.template.android_template_ca.domain.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
class User constructor(@PrimaryKey val id: Int)