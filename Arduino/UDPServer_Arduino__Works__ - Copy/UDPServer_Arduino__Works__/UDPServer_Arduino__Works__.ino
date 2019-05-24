//#include <SPI.h>         // needed for Arduino versions later than 0018
#include <Ethernet.h>
//#include <SoftwareSerial.h>
#include <ArduinoJson.h>
#include <Adafruit_PWMServoDriver.h>

#define MIN_PULSE_WIDTH       650
#define MAX_PULSE_WIDTH       2350
#define DEFAULT_PULSE_WIDTH   1500
#define FREQUENCY             50

Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver(0x41);

// Enter a MAC address and IP address for your controller below.
// The IP address will be dependent on your local network:
byte mac[] = {0x90,0xA2,0xDA,0x0D,0x8B,0x8F};
IPAddress ip(192, 168, 0, 123);
//IPAddress ip(172, 20, 10, 3);
//IPAddress ip1(172, 20, 10, 1);
IPAddress ip1(192, 168, 0, 1);
EthernetServer server(8032);
unsigned int localPort = 8032;  
boolean incoming = 0;
// local port to listen on

// buffers for receiving and sending data
char packetBuffer[UDP_TX_PACKET_MAX_SIZE]; //buffer to hold incoming packet,
char  ReplyBuffer[] = "acknowledged";       // a string to send back

// An EthernetUDP instance to let us send and receive packets over UDP
EthernetUDP Udp;

void setup() {
  pwm.begin();
  pwm.setPWMFreq(FREQUENCY);
//  pwm.setPWM(0, 0, pulseWidth(60));
//  pwm.setPWM(1, 0, pulseWidth(105));
//  pwm.setPWM(2, 0, pulseWidth(45));
//  pwm.setPWM(3, 0, pulseWidth(35));
//////  pwm.setPWM(4, 0, pulseWidth(0));
//////  pwm.setPWM(5, 0, pulseWidth(0));
//  pwm.setPWM(6, 0, pulseWidth(0));
//  pwm.setPWM(7, 0, pulseWidth(0));
//  pwm.setPWM(10, 0, pulseWidth(0));
  
  // start the Ethernet and UDP:
  Ethernet.begin(mac,ip);
  Udp.begin(localPort);
  Serial.begin(9600);
}

void loop() {  
  Serial.print("Hi!");
  delay(20);
  // if there's data available, read a packet
  int packetSize = Udp.parsePacket();
  if(packetSize){
      // read the packet into packetBufffer
      Udp.read(packetBuffer,UDP_TX_PACKET_MAX_SIZE);
      String data = packetBuffer;
      if(data == "Hello"){
        Udp.beginPacket(Udp.remoteIP(),Udp.remotePort());
        Udp.write("Connected");
        Udp.endPacket();
      }else{
        StaticJsonDocument<200> doc;
        DeserializationError error = deserializeJson(doc, data);
        if (error) {
          Serial.print(F("deserializeJson() failed: "));
          Serial.println(error.c_str());
          Udp.beginPacket(Udp.remoteIP(),Udp.remotePort());
          Udp.write("Error");
          Udp.endPacket();
          return;
        }
        JsonObject root = doc.as<JsonObject>();
        int servonum = (int)root["serv"];
        int angle = (int)root["val"];
        pwm.setPWM(servonum, 0, pulseWidth(angle));
        // send a reply, to the IP address and port that sent us the packet we received
        Udp.beginPacket(Udp.remoteIP(),Udp.remotePort());
        Udp.write(packetBuffer);
        Udp.endPacket();
      }
   }
}

int pulseWidth(int angle) {
  int pulse_wide, analog_value;
  pulse_wide   = map(angle, 0, 180, MIN_PULSE_WIDTH, MAX_PULSE_WIDTH);
  analog_value = int(float(pulse_wide) / 1000000 * FREQUENCY * 4096);
  return analog_value;
}
