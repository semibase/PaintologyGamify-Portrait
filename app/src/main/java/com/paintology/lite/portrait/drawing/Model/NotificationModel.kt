package com.paintology.lite.portrait.drawing.Model

import com.paintology.lite.portrait.drawing.util.NotificationType

data class NotificationModel(
    val title: String?,
    val text: String?,
    val type: NotificationType,
    val postId: String?,
    val userId: String?
)
