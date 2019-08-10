var tokenn = ''
// Initialize Firebase
let config = {
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
messaging.requestPermission().then(function () {
    //getToken(messaging);
    return messaging.getToken();
}).then(function (token) {

/*    tokenn = token
    document.getElementById("token").innerText = token*/
    console.log(token);
})
    .catch(function (err) {
        console.log('Permission denied', err);
    });


messaging.onMessage(function (payload) {
    console.log('onMessage: ', payload);
});


function sendpush() {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', 'key=AAAAOv03-LY:APA91bHPJ1-kxtNytnAJ7pPy3qUmLG5KWljhcbqnKYyUMt5sBMqgtlObJsjnaUeSfkrMoDi7Nz5myqlxut9WXDVtd8eS4gwTWJic5vPry0hf8IdCVBGwYrCRnnePXADl5Rv1FMDmeaJY');

    const body = `{
  "data":{
    "title":"New Text Message",
    "image":"https://firebase.google.com/images/social.png",
    "message":"Hello how are you?",
    "meta":{
    "type":"small",
    "info":"Search"
 }
  },
  "to":"` + tokenn + `"}`;

    const init = {
        method: 'POST',
        headers,
        body
    }

    fetch('https://fcm.googleapis.com/fcm/send', init)
        .then((response) => {
            return response.text(); // or .json() or .blob() ...
        })
        .then((text) => {
            // text is the response body

            console.log(text.data)

            self.registration.showNotification(text.data.title,
                text.data.message);
        })
        .catch((e) => {
            // error in e.message
            console.log(e)

        });


}
