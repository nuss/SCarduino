+SerialPort {
	
	/* send 10-bit values to a specified pin. All information gets packed into 2 bytes */
	pinValue { |pin, value, timeout|
		var highByte, lowByte, thisPin, thisVal;
		thisPin = pin.asInt;
		thisVal = value.asInt;
		if(thisVal > 1023, { Error("value must be a number between 0 and 1023").throw });
		lowByte = thisVal & 255;
		highByte = thisVal >> 8;
		highByte = thisPin << 2 + highByte;
		this.putAll(Int8Array[highByte, lowByte], timeout);
	}
	
	/* send a trigger (a short pulse) to a specified pin. All information gets packed into 2 bytes */
	trigger { |pin, trigdur=0.001, timeout|
		fork {
			this.pinValue(pin, 1, timeout);
			trigdur.wait;
			this.pinValue(pin, 0, timeout);
		}
	}

}

/* Arduino code - tested with an arduino uno board

int ctrlPin;      // the pin that the potty is attached to
byte data;
byte hiByte;
int val;
int count = 0;
int onOffPins[] = { 2, 4, 7, 8, 12, 13 };
int onOffPinsSize = 6;

void setup()
{
  // initialize the serial communication:
  Serial.begin(38400);
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
*/

