package com.appa.snoop.data.service

import com.appa.snoop.data.model.notification.response.NotificationResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {
    @GET("api/alert/history")
    suspend fun getNotificationHistory(
    ): List<NotificationResponse>

    @DELETE("api/alert/history/{alertId}")
    suspend fun deleteNotificationHistory(
        @Path("alertId") alertId: Int
    ): String

    @DELETE("api/alert/history/all")
    suspend fun deleteAllNotificationHistory(
    ): String
}