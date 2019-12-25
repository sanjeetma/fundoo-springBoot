package com.bridgelabz.fundo.util;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundo.model.RabbitMessage;

@Component
public class JMSUtility {
	
	public static final String EXCHANGE_NAME = "tips_tx";
	   public static final String ROUTING_KEY = "tips";
	   	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	RabbitTemplate tempRabbit;

	public void sendMail(String to, String url, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setText(url + token);
		System.err.println(token);
		mailSender.send(message);
		
	}

//	public void sendMailForForget( String to, String url, String token) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(to);
//		message.setText(url+token);	
//		mailSender.send(message);
//	}
	
	public void sendRabbit(RabbitMessage message) {
		tempRabbit.convertAndSend("tips_tx","tips",message);
	}

	@RabbitListener(queues = "default_parser_q")
	public void sendToRabitMq(RabbitMessage message) throws Exception{
		sendMail(message.getEmail(), message.getLink(), message.getToken());
	}
}
