package daggerok.domain

import graphql.GraphQL
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class GraphQLConfig(val peopleDataFetcher: PeopleDataFetcher,
                    val personDataFetcher: PersonDataFetcher) {

  @Value("classpath:graphql/schema.graphqls")
  lateinit var schemaResource: Resource

  @Bean
  fun registry(): TypeDefinitionRegistry {
    val schemaFile = schemaResource.file
    return SchemaParser().parse(schemaFile)
  }

  @Bean
  fun wiring() = RuntimeWiring.newRuntimeWiring()
      .type("Query", {
        it.dataFetcher("people", peopleDataFetcher)
        it.dataFetcher("person", personDataFetcher)
        // TODO: it.dataFetcher("addresses", addressesDataFetcher)
        // TODO: it.dataFetcher("address", addressDataFetcher)
      })
      .build()

  @Bean
  fun graphQLSchema() = SchemaGenerator().makeExecutableSchema(registry(), wiring())

  @Bean
  fun graphQL() = GraphQL
      .newGraphQL(graphQLSchema())
      .build()
}
