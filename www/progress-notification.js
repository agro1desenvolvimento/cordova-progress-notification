var exec = require('cordova/exec'),
    argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils');

module.exports = (function() {
  var _notification = {};

  _notification.show = function(title, message, indeterminate, successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'ProgressNotification', 'show', [title, message, indeterminate || false]);
  }

  _notification.update = function(value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'ProgressNotification', 'update', [value]);
  }

  _notification.finish = function(message, successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'ProgressNotification', 'finish', [message]);
  }

  return _notification;
}());