package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.controller.commonUtils.Notification.AndroidPushNotificationsService
import app.circle.foodmood.utils.Constant.Companion.FOODMOOD_EVENT_TOPIC
import app.circle.foodmood.utils.Constant.Companion.FOODMOOD_PROMOTION_TOPIC
import app.circle.foodmood.utils.Constant.Companion.ORDER_NOTIFICATION_TOPIC
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
        notification.put("sound", "default");
        notification.put("body", message);

        val data = JSONObject();
        data.put("orderId", orderId);
        data.put("tag", "foodmood_order_notification");
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


    fun orderNotification(orderId: Long, storeName: String): ResponseEntity<String> {
        val body = JSONObject();
        body.put("to", "/topics/$ORDER_NOTIFICATION_TOPIC")
        body.put("priority", "high");

        val notification = JSONObject();
        notification.put("title", "FoodMood - Order Notification");
        notification.put("sound", "default");
        notification.put("body", "A new order is placed in FoodMood");

        val data = JSONObject();
        data.put("orderId", orderId);
        data.put("orderId", orderId);
        data.put("storeName", storeName);
        data.put("tag", "foodmood_order_notification");

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

    fun eventNotification(eventName: String, tag: String): ResponseEntity<String> {
        val body = JSONObject();
        body.put("to", "/topics/$FOODMOOD_EVENT_TOPIC")
        body.put("priority", "high");


        val data = JSONObject();

        data.put("tag", tag);
        data.put("event", eventName);
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
    fun largeImageNotification(tag: String,title:String,text:String,imageURL:String): ResponseEntity<String> {
        val body = JSONObject();
        //val TEST_FOODMOOD_PROMOTION_TOPIC = "test_food_mood_promotion_topic"

        body.put("to", "/topics/$FOODMOOD_PROMOTION_TOPIC")
        body.put("priority", "high");



        val data = JSONObject();

        data.put("tag", tag);
        data.put("imageUrl", imageURL);
        data.put("body", text);
        data.put("title", title);
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
