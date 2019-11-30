package com.sdutacm.sdutoj.data.database

const val DATABASE_NAME = "sdutoj.db"

const val DATABASE_VERSION = 1

class ProblemTable {

    companion object {

        const val TABLE_NAME = "problemtable"

        const val PID = "pid"

        const val TITLE = "title"

        const val TIME_LIMIT = "time_limit"

        const val MEMORY_LIMIT = "memory_limit"

        const val DESCRIPTION = "description"

        const val INPUT = "input"

        const val OUTPUT = "output"

        const val SAMPLE_INPUT = "sample_input"

        const val SAMPLE_OUTPUT = "sample_output"

        const val HINT = "hint"

        const val SOURCE = "source"

        const val ADDED_TIME = "added_time"

        const val ACCEPTED = "accepted"

        const val SUBMISSION = "submission"

    }

}

class SolutionTable {

    companion object {

        const val TABLE_NAME = "solutiontable"

        const val RUN_ID = "runid"

        const val UID = "uid"

        const val USERNAME = "user_name"

        const val PID = "pid"

        const val CID = "cid"

        const val RESULT = "result"

        const val TIME = "time"

        const val MEMORY = "memory"

        const val LANGUAGE = "language"

        const val CODE_LENGTH = "code_length"

        const val SUBMISSION_TIME = "submission_time"

    }

}

class ContestTable {

    companion object {

        const val TABLE_NAME = "contesttable"

        const val CID = "cid"

        const val NAME = "name"

        const val TYPE = "type"

        const val START_TIME = "start_time"

        const val END_TIME = "end_time"

        const val REGISTER_START_TIME = "register_start_time"

        const val REGISTER_END_TIME = "register_end_time"

    }

}