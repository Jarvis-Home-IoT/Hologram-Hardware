/* Controlling LED using Firebase console by CircuitDigest(www.circuitdigest.com) */
#include <ESP8266WiFi.h>                                                // esp8266 library
#include <FirebaseArduino.h>                                             // firebase library

#define FIREBASE_HOST "---"                         // the project name address from firebase id
#define FIREBASE_AUTH "---"                    // the secret key generated from firebase
#define WIFI_SSID "---"                                          // input your home or public wifi name 
#define WIFI_PASSWORD "---"                                    // password of wifi ssid

String fireStatus = "";                                                     // led status received from firebase
int led = 14;                                                                // for external led
void setup() {
  Serial.begin(9600);
  delay(1000);  
  pinMode(led, OUTPUT);                 
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                                      // try to connect with wifi
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());                                                      // print local IP address
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);                                       // connect to firebase
}

void loop() {
  fireStatus = Firebase.getString("LED");            
  Serial.println("fireStatus: " + fireStatus); 
  if (fireStatus == "On") {                                                          // compare the input of led status received from firebase
    Serial.println("Led Turned ON");                                                                        // make bultin led ON
    digitalWrite(led, LOW);                                                         // make external led ON
  } 
  else if (fireStatus == "Off") {                                                  // compare the input of led status received from firebase
    Serial.println("Led Turned OFF");                                            // make bultin led OFF
    digitalWrite(led, HIGH);                                                         // make external led OFF
  }
  else {
    Serial.println("Wrong Credential! Please send ON/OFF");
  }
  if (Firebase.failed()) {
    Serial.print("failed to connect firebase : wrong firebase informations.");
    Serial.println(Firebase.error());
    }
    delay(1000);
}
