package fr.uha.gm.android.helper

import java.util.Date

object CompareUtil {

    fun compare(lhs: String?, rhs: String?): Boolean {
        if (lhs == null && rhs == null) return true
        if (lhs == null && rhs != null) return false
        if (lhs != null && rhs == null) return false
        return lhs.equals(rhs)
    }

    fun compare(lhs: Enum<*>?, rhs: Enum<*>?): Boolean {
        if (lhs == null && rhs == null) return true
        if (lhs == null && rhs != null) return false
        if (lhs != null && rhs == null) return false
        return lhs!!.ordinal == rhs!!.ordinal
    }

    fun compare(lhs: Date?, rhs: Date?): Boolean {
        if (lhs == null && rhs == null) return true
        if (lhs == null && rhs != null) return false
        if (lhs != null && rhs == null) return false
        return lhs!!.time == rhs!!.time
    }

    fun compare(lhs: Int?, rhs: Int?): Boolean {
        if (lhs == null && rhs == null) return true
        if (lhs == null && rhs != null) return false
        if (lhs != null && rhs == null) return false
        return lhs == rhs
    }

    fun compare(lhs: Long?, rhs: Long?): Boolean {
        if (lhs == null && rhs == null) return true
        if (lhs == null && rhs != null) return false
        if (lhs != null && rhs == null) return false
        return lhs == rhs
    }

    fun compare(lhs: Float?, rhs: Float?): Boolean {
        if (lhs == null && rhs == null) return true
        if (lhs == null && rhs != null) return false
        if (lhs != null && rhs == null) return false
        return lhs == rhs
    }

    fun compare(lhs: Any?, rhs: Any?): Boolean {
        if (lhs == null && rhs == null) return true
        if (lhs == null && rhs != null) return false
        if (lhs != null && rhs == null) return false
        return lhs == rhs
    }

}