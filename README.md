A couple of utilities extending SuperCollider's SerialPort-class resp. C-code for the Arduino board. The code has been tested with an Arduino UNO and Arduino 1.0.1

Installation
-----------
Put all SuperCollider-extensions (*.sc) into your userExtensionDir (execute 

	Platform.userExtensionDir 

in SuperCollider to see where that is). Open all *.ino files in Arduino and flash your Arduino-board.

A simple test
------------
	/* execute in SuperCollider. Arduino must already be flashed */
	
	(
	//SerialPort.listDevices;
	p = SerialPort(
		//edit to match your port. SerialPort.listDevices
		"/dev/tty.usbmodem26411",
		//check that baudrate is the same as in arduino sketch
		baudrate: 38400,
		crtscts: true
	)
	)
	
	// the inbuilt led on pin 13 should flash for 1 second
	p.trigger(13, 1);
	
	// close the port when done
	p.close;
