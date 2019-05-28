package com.example.event_bus

import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.greenrobot.eventbus.Subscribe


class UserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // register the event to listen.
        GlobalBus.bus.register(this)
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener(view)
    }

    fun setClickListener(view: View) {
        val btnSubmit = view.findViewById<View>(R.id.submit) as Button
        btnSubmit.setOnClickListener {
            val etMessage = view.findViewById<View>(R.id.editText) as EditText

            // We are broadcasting the message here to listen to the subscriber.
            val fragmentActivityMessageEvent = Events.FragmentActivityMessage(
                etMessage.text.toString()
            )
            GlobalBus.bus.post(fragmentActivityMessageEvent)
        }
    }

    @Subscribe
    fun getMessage(activityFragmentMessage: Events.ActivityFragmentMessage) {
        val messageView = view!!.findViewById<View>(R.id.message) as TextView
        messageView.text = getString(R.string.message_received) +
                " " + activityFragmentMessage.message

        Toast.makeText(
            activity,
            getString(R.string.message_fragment) +
                    " " + activityFragmentMessage.message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // unregister the registered event.
        GlobalBus.bus.unregister(this)
    }
}
