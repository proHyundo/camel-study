package com.example.camel_micro_a;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;

	@Autowired
	private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

	// Endpoint 1 : timer
	// transformation : change the message
	// Endpoint 2 : log
	@Override
	public void configure() throws Exception {
		/*
		from("timer:first-timer") // timer endpoint
			// .transform().constant("My message is Hello World") // transform message nul to Hello World
			.transform().constant("Time now is " + java.time.LocalTime.now()) // 상수를 사용하고 있기 때문에 동일한 메시지를 계속 전송
			.to("log:first-timer"); // log endpoint

		// 더 동적으로 만들 수 있는 방법 1
		from("timer:second-timer")
			.bean("getCurrentTime") // 메시지를 수신할 때 마다 getCurrentTimeBean의 getCurrentTime 메소드가 호출된다.
			.to("log:second-timer");

		// 더 동적으로 만들 수 있는 방법 2
		from("timer:third-timer")
			.bean(getCurrentTimeBean)
			.to("log:third-timer");
		*/

		// Processing & Transformation 차이
		// Processing : 메시지를 변경하지 않고 메시지를 처리하는 것
		// Transformation : 메시지를 변경하는 것

		from("timer:fourth-timer")
			.log("after from : ${body}")
			.transform().constant("My constant message")
			.log("after transform : ${body}")
			.bean(getCurrentTimeBean)
			.log("after transform bean : ${body}")
			.bean(simpleLoggingProcessingComponent)
			.log("after processing bean : ${body}")
			.to("log:fourth-timer");
	}

}

// 더 동적으로 만들 수 있는 방법이다.
// 일반적으로 실시간 애플리케이션에 대해 이야기할 때 여기서는 매우 복잡한 로직이 사용된다. 우선, 이것은 단순한 예제이다.
// 이상적으로는 해당 클래스는 별도의 패키지에 별도의 공용 클래스로 포함되어야 합니다.
@Component
class GetCurrentTimeBean {

	public String getCurrentTime() {
		return "Time now is " + java.time.LocalTime.now();
	}

}

// Processing 을 위한 Bean
@Component
class SimpleLoggingProcessingComponent {

	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}

}
