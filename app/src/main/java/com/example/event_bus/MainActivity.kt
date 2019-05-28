package com.example.event_bus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.TextView
import android.view.View
import org.greenrobot.eventbus.Subscribe
import android.widget.EditText


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment()
    }

    override fun onStart() {
        super.onStart()
        // Register this fragment to listen to event.
        GlobalBus.bus.register(this)
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, UserFragment())
            .commit()
    }

    fun sendMessageToFragment(view: View) {
        val etMessage = findViewById(R.id.activityData) as EditText
        val activityFragmentMessageEvent = Events.ActivityFragmentMessage(etMessage.text.toString())

        GlobalBus.bus.post(activityFragmentMessageEvent)
    }

    @Subscribe
    fun getMessage(fragmentActivityMessage: Events.FragmentActivityMessage) {
        val messageView = findViewById<View>(R.id.message) as TextView
        messageView.text = getString(R.string.message_received) + " " + fragmentActivityMessage.message

        Toast.makeText(
            applicationContext,
            getString(R.string.message_main_activity) + " " + fragmentActivityMessage.message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStop() {
        super.onStop()
        GlobalBus.bus.unregister(this)
    }
}

