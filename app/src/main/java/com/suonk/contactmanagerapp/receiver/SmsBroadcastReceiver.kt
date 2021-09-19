package com.suonk.contactmanagerapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.suonk.contactmanagerapp.models.data.Message
import android.widget.Toast


class SmsBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val SMS_BUNDLE = "pdus"
    }

    private var message: Message? = null

    override fun onReceive(context: Context?, intent: Intent?) {
//        var intentExtras = intent!!.extras
//
//        if (intentExtras != null) {
//            val sms = intentExtras.get(SMS_BUNDLE)
//            var smsMessageStr = ""
//            var phone_no = ""
//
//
//            for (i in 0 until sms.) {
//                val smsMessage: SmsMessage = SmsMessage.createFromPdu(sms.get(i) as ByteArray?)
//                val smsBody: String = smsMessage.getMessageBody()
//                val address: String = smsMessage.getOriginatingAddress()
//                val timeMillis: Long = smsMessage.getTimestampMillis()
//                val date = Date(timeMillis)
//                val format = SimpleDateFormat("dd/mm/yy")
//                val dateText: String = format.format(date)
//                smsMessageStr += """
//                    ${address}at	$dateText
//
//                    """.trimIndent()
//                smsMessageStr += """
//                    $smsBody
//
//                    """.trimIndent()
//                phone_no += address
//                mChats.setSender(address)
//                mChats.setMessage(smsBody)
//            }
//            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show()
//
//            val broadcastIntent = Intent()
//            broadcastIntent.action = "SMS_RECEIVED_ACTION"
//            broadcastIntent.putExtra("message", smsMessageStr)
//            broadcastIntent.putExtra("mob_no", phone_no)
//        }
    }
}