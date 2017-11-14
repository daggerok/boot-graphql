package daggerok.graphql

import graphql.GraphQL
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import java.io.InputStreamReader

@Configuration
class GraphQLConfig(val resources: ResourceLoader,
                    val peopleDataFetcher: PeopleDataFetcher,
                    val personDataFetcher: PersonDataFetcher) {
/*
  companion object {
    private val log = org.slf4j.LoggerFactory.getLogger(GraphQLConfig::class.simpleName)
  }
*/
  @Bean
  fun registry(): TypeDefinitionRegistry {
    val resource = resources.getResource("classpath:schema.graphqls")

    InputStreamReader(resource.inputStream).use {
      return SchemaParser().parse(it.readText())
    }
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
