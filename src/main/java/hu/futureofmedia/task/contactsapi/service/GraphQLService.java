package hu.futureofmedia.task.contactsapi.service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import hu.futureofmedia.task.contactsapi.datafetcher.*;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import static java.time.ZonedDateTime.now;

@Service
public class GraphQLService {

    @Autowired
    ContactRepository contactRepository;

    @Value("classpath:query.graphqls")
    Resource resource;

    private GraphQL graphQL;
    @Autowired
    private TenContactDataFetcher tenContactDataFetcher;
    @Autowired
    private ContactDataFetcher contactDataFetcher;
    @Autowired
    private CreateContactDataFetcher createContactDataFetcher;
    @Autowired
    private ContactUpdateDataFetcher contactUpdateDataFetcher;
    @Autowired
    private DeleteContactDataFetcher deleteContactDataFetcher;
    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {


        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }


    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("ContactQueries", typeWiring -> typeWiring
                        .dataFetcher("findTenForUser", tenContactDataFetcher)
                        .dataFetcher("contact", contactDataFetcher))
                .type(TypeRuntimeWiring.newTypeWiring("ContactMutations")
                        .dataFetcher("newContact", createContactDataFetcher)
                        .dataFetcher("updateContact", contactUpdateDataFetcher)
                        .dataFetcher("deleteContact", deleteContactDataFetcher))
                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}
