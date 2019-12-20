package com.sdutacm.sdutoj.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val PROBLEM_TABLE =
            "create table " + ProblemTable.TABLE_NAME + "(" +
                    ProblemTable.PID + " int primary key," +
                    ProblemTable.TYPE + " int not null," +
                    ProblemTable.TITLE + " varchar(50) not null," +
                    ProblemTable.TIME_LIMIT + " int not null," +
                    ProblemTable.MEMORY_LIMIT + " int not null," +
                    ProblemTable.DESCRIPTION + " varchar(500) not null," +
                    ProblemTable.INPUT + " varchar(100) not null," +
                    ProblemTable.OUTPUT + " varchar(100) not null," +
                    ProblemTable.SAMPLE_INPUT + " varchar(50) not null," +
                    ProblemTable.SAMPLE_OUTPUT + " varchar(50) not null," +
                    ProblemTable.HINT + " varchar(50) not null," +
                    ProblemTable.SOURCE + " varchar(30) not null," +
                    ProblemTable.ADDED_TIME + " varchar(30) not null," +
                    ProblemTable.ACCEPTED + " int not null," +
                    ProblemTable.SUBMISSION + " int not null" +
                    ")"

        private const val SOLUTION_TABLE =
            "create table " + SolutionTable.TABLE_NAME + "(" +
                    SolutionTable.RUN_ID + " int primary key," +
                    SolutionTable.UID + " int not null," +
                    SolutionTable.USERNAME + " varchar(20) not null," +
                    SolutionTable.PID + " int not null," +
                    SolutionTable.CID + " int not null," +
                    SolutionTable.RESULT + " int not null," +
                    SolutionTable.TIME + " int not null," +
                    SolutionTable.MEMORY + " int not null," +
                    SolutionTable.LANGUAGE + " varchar(10) not null," +
                    SolutionTable.CODE_LENGTH + " int not null," +
                    SolutionTable.SUBMISSION_TIME + " varchar(30) not null" +
                    ")"

        private const val CONTEST_TABLE =
            "create table " + ContestTable.TABLE_NAME + "(" +
                    ContestTable.CID + " int primary key," +
                    ContestTable.NAME + " varchar(30) not null," +
                    ContestTable.TYPE + " int nut null," +
                    ContestTable.START_TIME + " varchar(30) not null," +
                    ContestTable.END_TIME + " varchar(30) not null," +
                    ContestTable.REGISTER_START_TIME + " varchar(30) not null," +
                    ContestTable.REGISTER_END_TIME + " varchar(30) not null" +
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
        db?.execSQL(PROBLEM_TABLE)
        db?.execSQL(SOLUTION_TABLE)
        db?.execSQL(CONTEST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}