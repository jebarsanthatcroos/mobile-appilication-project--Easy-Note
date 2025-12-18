package com.project.easynotes.ui.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Formats a timestamp to a readable date string
 * Example: "Dec 18, 2025"
 */
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

/**
 * Formats a timestamp to a readable date and time string
 * Example: "Dec 18, 2025 14:30"
 */
fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

/**
 * Formats a timestamp to a relative time string
 * Example: "2 hours ago", "Yesterday", "3 days ago"
 */
fun formatRelativeTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "Just now"
        minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
        hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
        days < 7 -> "$days day${if (days > 1) "s" else ""} ago"
        else -> formatDate(timestamp)
    }
}

/**
 * Formats a timestamp to show time only
 * Example: "14:30"
 */
fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

/**
 * Formats a timestamp to a full date and time string
 * Example: "December 18, 2025 at 2:30 PM"
 */
fun formatFullDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMMM dd, yyyy 'at' h:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}