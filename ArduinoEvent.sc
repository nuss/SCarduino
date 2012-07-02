ArduinoEvent {
	classvar <>initDelay=0.01;
	
	*initClass {
		{
			Event.addEventType(\setSerial16, { 
				if(~port.notNil and:{ ~port.isOpen }, { 
					~port.pinValue(~pin, ~pinVal.linlin(0, 1023, 1, 1024).asInt-1);
				})
			})
		}.defer(initDelay)
	}

}