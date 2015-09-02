# Background

An example program to show CPU going to sleep while a service is running.

Install the app and disconnect the USB cable (with cable connected, the device does not go to sleep).

There are three android Services, each have a start/stop button on the screen. Each service attempts to beep once in every 5 seconds.
Service 1 just start a thread and loops trying to beep. It works only when the device is awake and is suspended when the device goes to sleep.
Service 2 uses AlarmManager to schedule wakeups in every 5 seconds for a beep. The service wakes up the device CPU if it's asleep.
Service 3 uses PowerManager.WakeLock to disallow the device to go to sleep.

```
1.
Press "Start 1" button
Wait for 5 seconds for a "beep"
Turn the screen off
Wait for more than 5 seconds, notice how there's no beeping
Turn the screen on, hear a beep
Press "Stop 1" button to stop the non-working service
```

```
2.
Press "Start 2" button
Wait for 5 seconds for a "beep"
Turn the screen off
Wait for more than 5 seconds, notice how beeping continues
Turn the screen on
Press "Stop 2" button to stop the service
```

```
3.
Press "Start 3" button
Wait for 5 seconds for a "beep"
Turn the screen off
Wait for more than 5 seconds, notice how beeping continues
Turn the screen on
Press "Stop 3" button to stop the service
```
