importScripts('https://www.gstatic.com/firebasejs/3.9.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/3.9.0/firebase-messaging.js');


  // Initialize Firebase
var config = {
  apiKey: "AIzaSyBeJIyLuqJAL9403IM7KvCTYBRb-Mr0c9w",
  authDomain: "pushnotification-d1923.firebaseapp.com",
  databaseURL: "https://pushnotification-d1923.firebaseio.com",
  projectId: "pushnotification-d1923",
  storageBucket: "",
  messagingSenderId: "253356406966",
  appId: "1:253356406966:web:c88aa4000d35f6a7"
};

  firebase.initializeApp(config);
  const messaging = firebase.messaging();

messaging.setBackgroundMessageHandler(function(payload) {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  var notificationTitle = 'Background Message Title';
  var notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png'
  };

  return self.registration.showNotification(notificationTitle,
      notificationOptions);
});
