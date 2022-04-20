package hu.futureofmedia.task.contactsapi.jms;

import hu.futureofmedia.task.contactsapi.DTO.MailDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class JmsProducer {
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Value("${gkz.activemq.queue}")
	String queue;
	
	public void send(MailDTO mailDTO){
		jmsTemplate.convertAndSend(queue, mailDTO);
	}
}