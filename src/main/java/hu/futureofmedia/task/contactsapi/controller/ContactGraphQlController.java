package hu.futureofmedia.task.contactsapi.controller;

import graphql.ExecutionResult;
import hu.futureofmedia.task.contactsapi.service.GraphQLService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/graph")
@RestController
public class ContactGraphQlController {
    @Autowired
    GraphQLService graphQLService;

    @PostMapping
    public ResponseEntity<Object> getContactConfigs(@RequestBody String query) {
        {    //System.out.println(service.getAllEmployees().get(0).getAddress().getCountry());
            ExecutionResult execute = graphQLService.getGraphQL().execute(query);
            return new ResponseEntity<>(execute, HttpStatus.OK);
        }
    }
}