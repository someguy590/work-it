package com.someguy590.workit.utils

import android.content.Context
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.someguy590.workit.DB
import org.koin.core.annotation.Single

@Single
fun db(context: Context) = DB(
    AndroidSqliteDriver(
        DB.Schema,
        context,
        "work_it.db",
        callback = AndroidSqliteDriver.Callback(
            DB.Schema,
            migration2,
            migration3
        )
    )
)

private val migration2 = AfterVersion(2) { driver ->
    driver.execute(
        null,
        """
            CREATE TABLE Workout_temp (
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            weight REAL NOT NULL,
            sets INTEGER NOT NULL
            );
        """.trimIndent(),
        0
    )

    driver.execute(
        null,
        """
                        INSERT INTO Workout_temp (id, name, weight, sets)
                        SELECT id, name, weight, reps
                        FROM Workout;
                    """.trimIndent(),
        0
    )

    driver.execute(
        null,
        """
                        DROP TABLE Workout
                    """.trimIndent(),
        0
    )

    driver.execute(
        null,
        """
                        ALTER TABLE Workout_temp RENAME TO Workout
                    """.trimIndent(),
        0
    )
}

private val migration3 = AfterVersion(3) { driver ->
    driver.execute(
        null,
        """
            CREATE TABLE Workout_temp (
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            weight TEXT NOT NULL,
            sets INTEGER NOT NULL
            );
        """.trimIndent(),
        0
    )

    driver.execute(
        null,
        """
            INSERT INTO Workout_temp (id, name, weight, sets)
            SELECT id, name, CAST(weight AS Text), sets
            FROM Workout;
        """.trimIndent(),
        0
    )

    driver.execute(
        null,
        """
            DROP TABLE Workout
        """.trimIndent(),
        0
    )

    driver.execute(
        null,
        """
            ALTER TABLE Workout_temp RENAME TO Workout
        """.trimIndent(),
        0
    )
}
