package com.example.camel_micro_a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		// Endpoint 1 : timer
		// transformation change the message
		// Endpoint 2 : log

		from("timer:first-timer") // timer endpoint
			// .transform().constant("My message is Hello World") // transform message nul to Hello World
			.transform().constant("Time now is " + java.time.LocalTime.now()) // 상수를 사용하고 있기 때문에 동일한 메시지를 계속 전송
			.to("log:first-timer"); // log endpoint
	}
}
