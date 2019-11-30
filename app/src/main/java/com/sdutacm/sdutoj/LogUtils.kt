package com.sdutacm.sdutoj

import android.util.Log

class LogUtils {
    companion object {

        private val LOG_LEVEL = 5

        private val ERROE_LEVEL = 5

        private val WARN_LEVEL = 4

        private val INFO_LEVEL = 3

        private val DEBUG_LEVEL = 2

        private val VERBOSE_LEVEL = 1

        @JvmStatic
        fun e(msg : String) {
            if(!checkLogShow(ERROE_LEVEL)){
                return
            }
            Log.e("LOG_LEVEL $LOG_LEVEL",msg)
        }

        @JvmStatic
        fun w(msg : String) {
            if(!checkLogShow(WARN_LEVEL)){
                return
            }
            Log.w("LOG_LEVEL $LOG_LEVEL",msg)
        }

        @JvmStatic
        fun i(msg : String) {
            if(!checkLogShow(INFO_LEVEL)){
                return
            }
            Log.i("LOG_LEVEL $LOG_LEVEL",msg)
        }

        @JvmStatic
        fun d(msg : String) {
            if(!checkLogShow(DEBUG_LEVEL)){
                return
            }
            Log.d("LOG_LEVEL $LOG_LEVEL",msg)
        }

        @JvmStatic
        fun v(msg : String) {
            if(!checkLogShow(VERBOSE_LEVEL)){
                return
            }
            Log.v("LOG_LEVEL $LOG_LEVEL",msg)
        }

        @JvmStatic
        private fun checkLogShow(level: Int): Boolean {
            return level >= LOG_LEVEL
        }
    }
}