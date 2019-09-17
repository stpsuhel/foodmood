package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.controller.commonUtils.Notification.AndroidPushNotificationsService
import org.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


@Service
class NotificationUtils(val androidPushNotificationsService: AndroidPushNotificationsService) {


    fun sendOrderAcceptNotification(message: String, to: String, orderId: Long, orderStatus: Int): ResponseEntity<String> {

        val body = JSONObject();
        body.put("to", to);
        body.put("priority", "high");

        val notification = JSONObject();
        notification.put("title", "FoodMood - Order Notification");
        notification.put("sound","default");
        notification.put("body", message);

        val data = JSONObject();
        data.put("orderId", orderId);
        data.put("orderStatus", orderStatus);

        body.put("notification", notification);
        body.put("data", data);

        val request = HttpEntity<String>(body.toString());

        val pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            val firebaseResponse = pushNotification.get();

            return ResponseEntity<String>(firebaseResponse, HttpStatus.OK);
        } catch (e: InterruptedException) {
            e.printStackTrace();
        } catch (e: ExecutionException) {
            e.printStackTrace();
        }

        return ResponseEntity<String>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
