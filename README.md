# cordova-progress-notification
Cordova Plugin - Progress Notification for Android Plugin


## What is this?
![](https://developer.android.com/images/ui/notifications/progress_bar_summary.png)

https://developer.android.com/training/notify-user/display-progress.html

## Install
```bash
cordova plugin add https://github.com/mateusnava/cordova-progress-notification
```

## How can I use?
It's very simple, it's only 3 self-explanatory methods:

```javascript
progressNotification.show(tittle, message, indeterminate);

progressNotification.status(value); // value can be 0-100

progressNotification.finish(message);
```
