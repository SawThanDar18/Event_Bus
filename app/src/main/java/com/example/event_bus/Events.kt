package com.example.event_bus

class Events {

    // Event used to send message from fragment to activity.
    class FragmentActivityMessage(val message: String)

    // Event used to send message from activity to fragment.
    class ActivityFragmentMessage(val message: String)

    // Event used to send message from activity to activity.
    class ActivityActivityMessage(val message: String)
}
