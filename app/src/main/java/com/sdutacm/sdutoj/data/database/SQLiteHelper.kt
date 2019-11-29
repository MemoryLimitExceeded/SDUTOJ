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

        private const val mSolutionTable =
            "create table " + SolutionTable.TABLENAME + "(" +
                    SolutionTable.RUNID + " int primary key," +
                    SolutionTable.UID + " int not null," +
                    SolutionTable.USERNAME + " varchar(20) not null," +
                    SolutionTable.PID + " int not null," +
                    SolutionTable.CID + " int not null," +
                    SolutionTable.RESULT + " int not null," +
                    SolutionTable.TIME + " int not null," +
                    SolutionTable.MEMORY + " int not null," +
                    SolutionTable.LANGUAGE + " varchar(10) not null," +
                    SolutionTable.CODELENGTH + " int not null," +
                    SolutionTable.SUBMISSIONTIME + " varchar(30) not null" +
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
        db?.execSQL(mSolutionTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}