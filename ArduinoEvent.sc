/*
Example

periodically flashing the inbuilt led on pin 13. extSerialPort.sc must be installed as well.

(
p = SerialPort(
	"/dev/tty.usbmodem26411", //edit to match your port. SerialPort.listDevices
	baudrate: 38400, //check that baudrate is the same as in arduino sketch
	crtscts: true
)
)

(
Pdef(\ledTest,
	Pbind(
		\type, \setSerial16,
		\port, `p, // *must* be a Ref to the SerialPort instance, *not* the instance itself
		\pin, 13,
		\pinVal, Pwhite(0, 1023),
		\dur, 0.1
	).trace
)
)

Pdef(\ledTest).play
Pdef(\ledTest).clear
*/

ArduinoEvent {
	
	*initClass {
		StartUp.add({
			Event.addEventType(\setSerial16, { 
				if(~port.notNil and:{ ~port.isOpen }, { 
					~port.pinValue(~pin, ~pinVal.linlin(0, 1023, 1, 1024).asInt-1);
				})
			});
			Event.addEventType(\setSerialOnOff, {
				if(~port.notNil and:{ ~port.isOpen }, {
					~port.trigger(~pin, ~trigDur);
				})
			})
		})
	}

}