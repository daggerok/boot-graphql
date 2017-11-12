package daggerok.domain

import graphql.GraphQL
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
class QueryRestResource(val personRepository: PersonRepository) {

  companion object {
    private val log = LoggerFactory.getLogger(QueryRestResource::class.simpleName)
  }

  @Value("classpath:graphql/schema.graphqls")
  lateinit var schemaResource: Resource

  private lateinit var graphQL: GraphQL

  @PostConstruct
  fun loadSchema() {
    val schemaFile = schemaResource.file
    val registry = SchemaParser().parse(schemaFile)
    val wiring = RuntimeWiring.newRuntimeWiring().build()
    val schema = SchemaGenerator().makeExecutableSchema(registry, wiring)
    graphQL = GraphQL.newGraphQL(schema).build()
  }

  @PostMapping("/")
  fun index(@RequestBody req: Map<String, String>): Map<String, Any> {
    val query = req["query"].orEmpty()
    log.info("query: {}", query)
//    val executionResult = graphQL.execute(query)
//    val data = executionResult.getData()
    return mapOf("query" to query)
  }

  @GetMapping("/")
  fun index() = mapOf("scema" to personRepository.findAll())
}
