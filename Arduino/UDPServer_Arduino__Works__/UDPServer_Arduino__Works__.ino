//Import of library for Internet connection
#include <Ethernet.h>
//Import of library for Json data recognition
#include <ArduinoJson.h>
//Import of library for pwm controller
#include <Adafruit_PWMServoDriver.h>

//Parameters for pwm
#define MIN_PULSE_WIDTH       650
#define MAX_PULSE_WIDTH       2350
#define DEFAULT_PULSE_WIDTH   1500
#define FREQUENCY             50

//Enabling pwm controller on 0x41 input
Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver(0x41);

//MAC mask address (needs to be different from local network)
byte mac[] = {0x90,0xA2,0xDA,0x0D,0x8B,0x8F};

//IP adress for controller (needs to be based on local network)
//First three values should be gateaway for local network
//The third one will be a random number
IPAddress ip(192, 168, 0, 123);

//IPAddress ip(172, 20, 10, 3);
//IPAddress ip1(172, 20, 10, 1);

//Usually a gateway address
IPAddress ip1(192, 168, 0, 1);

//Local port to listen on
EthernetServer server(8032);

//The same port
unsigned int localPort = 8032;

boolean incoming = 0;

//Buffers for receiving and sending data
//Buffer to hold incoming packet
char packetBuffer[UDP_TX_PACKET_MAX_SIZE]; 

//An EthernetUDP instance to let us send and receive packets over UDP
EthernetUDP Udp;

void setup() {
  
  //Begin the work of pwm controller
  pwm.begin();

  //Set pwm to process data on defined frequency
  pwm.setPWMFreq(FREQUENCY);
  
//  pwm.setPWM(0, 0, pulseWidth(60));
//  pwm.setPWM(1, 0, pulseWidth(105));
//  pwm.setPWM(2, 0, pulseWidth(45));
//  pwm.setPWM(3, 0, pulseWidth(35));
//  pwm.setPWM(4, 0, pulseWidth(0));
//  pwm.setPWM(5, 0, pulseWidth(0));
//  pwm.setPWM(6, 0, pulseWidth(0));
//  pwm.setPWM(7, 0, pulseWidth(0));
//  pwm.setPWM(10, 0, pulseWidth(0));
  
  //Start the Ethernet and UDP:
  Ethernet.begin(mac,ip);
  Udp.begin(localPort);

  //Start serial with defined frequency 
  Serial.begin(9600);
}

void loop() {  
  //Wait for data to receive and process
  delay(20);
  
  //If there's data available, read a packet
  int packetSize = Udp.parsePacket();

  //If data is not empty
  if(packetSize){
    
      //Read the packet into packetBuffer
      Udp.read(packetBuffer,UDP_TX_PACKET_MAX_SIZE);
      String data = packetBuffer;

      //If data is a message "Hello", then it is a test data for connection
      if(data == "Hello"){
        //Form a UDP packet to send to the sender
        Udp.beginPacket(Udp.remoteIP(),Udp.remotePort());
        //Write the message to UDP packet
        Udp.write("Connected");
        //End the message and send it
        Udp.endPacket();
      }else{
        //If there is a lot data, it is actions, that need to be processed
        //Create a document
        StaticJsonDocument<200> doc;
        //Get Json data in the doc
        DeserializationError error = deserializeJson(doc, data);
        //If there was an error, say so
        if (error) {
          Serial.print(F("deserializeJson() failed: "));
          Serial.println(error.c_str());
          //Form a UDP packet to send to the sender
          Udp.beginPacket(Udp.remoteIP(),Udp.remotePort());
          //Write the message to UDP packet
          Udp.write("Error");
          //End the message and send it
          Udp.endPacket();
          //End the iteration of loop
          return;
        }
        //Get the data from Json Object
        JsonObject root = doc.as<JsonObject>();
        //Get the servomotor number
        int servonum = (int)root["serv"];
        //Get the value of motor
        int angle = (int)root["val"];
        //Make servomotor move
        pwm.setPWM(servonum, 0, pulseWidth(angle));
        // send a reply, to the IP address and port that sent us the packet we received
        Udp.beginPacket(Udp.remoteIP(),Udp.remotePort());
        Udp.write(packetBuffer);
        Udp.endPacket();
      }
   }
}

//Function for converting the angle value to the length of impulse
int pulseWidth(int angle) {
  int pulse_wide, analog_value;
  pulse_wide   = map(angle, 0, 180, MIN_PULSE_WIDTH, MAX_PULSE_WIDTH);
  analog_value = int(float(pulse_wide) / 1000000 * FREQUENCY * 4096);
  return analog_value;
}
