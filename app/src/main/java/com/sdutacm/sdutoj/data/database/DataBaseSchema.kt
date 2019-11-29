package com.sdutacm.sdutoj.data.database

const val DATABASE_NAME = "sdutoj.db"

const val DATABASE_VERSION = 1

class ProblemTable {

    companion object {

        const val TABLENAME = "problemtable"

        const val PID = "pid"

        const val TITLE = "title"

        const val TIMELIMIT = "time_limit"

        const val MEMORYLIMIT = "memory_limit"

        const val DESCIPTION = "description"

        const val INPUT = "input"

        const val OUTPUT = "output"

        const val SAMPLEINPUT = "sample_input"

        const val SAMPLEOUTPUT = "sample_output"

        const val HINT = "hint"

        const val SOURCE = "source"

        const val ADDEDTIME = "added_time"

        const val ACCEPTED = "accepted"

        const val SUBMISSION = "submission"

    }

}

class SolutionTable{

    companion object{

        const val TABLENAME = "solutiontable"

        const val RUNID = "runid"

        const val UID = "uid"

        const val USERNAME = "user_name"

        const val PID = "pid"

        const val CID = "cid"

        const val RESULT ="result"

        const val TIME = "time"

        const val MEMORY = "memory"

        const val LANGUAGE = "language"

        const val CODELENGTH = "code_length"

        const val SUBMISSIONTIME = "submission_time"

    }

}