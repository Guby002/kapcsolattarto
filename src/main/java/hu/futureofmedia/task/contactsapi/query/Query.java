package hu.futureofmedia.task.contactsapi.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;

@Service
public class Query implements GraphQLQueryResolver {

	public String helloWorld() {
		return "Hello World";
	}
}