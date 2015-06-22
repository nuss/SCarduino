int ctrlPin; // the pin you're going to send data to
byte data;
byte hiByte;
int val;
int count = 0;
int onOffPins[] = { 2, 4, 7, 8, 12, 13 };
int onOffPinsSize = 6;

void setup()
{
  // initialize the serial communication:
  Serial.begin(115200);
}

void loop() 
{
  // check if data has been sent from the computer:
  if(Serial.available()) {
    data = Serial.read();
    if(count%2 == 0) {
      // which pin
      ctrlPin = data >> 2;
      pinMode(ctrlPin, OUTPUT);
      // now we need the upper 2 bits
      // of the controller-value
      hiByte = data & 3;
    } else {
      val = (hiByte << 8) + data;
      if(inArray(ctrlPin, onOffPins, onOffPinsSize)) {
        if(val == 1) {
          digitalWrite(ctrlPin, HIGH);
        } else {
          digitalWrite(ctrlPin, LOW);
        }
      } else {
        analogWrite(ctrlPin, val);
      }
    }
    if(count%2 == 1) { 
      count = 0;
    } else {
      count++;
    }
  }
}

boolean inArray(int item, int* thisArray, int arrSize) {
  for(int i = 0; i < arrSize; i++) {
    if(item == thisArray[i]) {
      return true;
    }
  }
  return false;
}
