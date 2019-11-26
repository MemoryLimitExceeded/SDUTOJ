package com.sdutacm.sdutoj.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        private const val mProblemTable =
            "create table " + ProblemTable.TABLENAME + "(" +
                    ProblemTable.PID + " int primary key," +
                    ProblemTable.TITLE + " varchar(50) not null," +
                    ProblemTable.TIMELIMIT + " int not null," +
                    ProblemTable.MEMORYLIMIT + " int not null," +
                    ProblemTable.DESCIPTION + " varchar(500) not null," +
                    ProblemTable.INPUT + " varchar(100) not null," +
                    ProblemTable.OUTPUT + " varchar(100) not null," +
                    ProblemTable.SAMPLEINPUT + " varchar(50) not null," +
                    ProblemTable.SAMPLEOUTPUT + " varchar(50) not null," +
                    ProblemTable.HINT + " varchar(50) not null," +
                    ProblemTable.SOURCE + " varchar(30) not null," +
                    ProblemTable.ADDEDTIME + " varchar(30) not null," +
                    ProblemTable.ACCEPTED + " int not null," +
                    ProblemTable.SUBMISSION + " int not null" +
                    ")"

        @Volatile
        private var instance: SQLiteHelper? = null

        fun newInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: SQLiteHelper(context).also { instance = it }
            }

        fun getInstance() = instance

    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(mProblemTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}