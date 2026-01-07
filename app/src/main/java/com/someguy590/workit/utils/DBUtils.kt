package com.someguy590.workit.utils

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.someguy590.workit.DB
import org.koin.core.annotation.Single

@Single
fun db (context: Context) = DB(AndroidSqliteDriver(DB.Schema, context, "work_it.db"))
