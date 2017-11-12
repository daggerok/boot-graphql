package daggerok.graphql

import daggerok.domain.PersonRepository
import graphql.GraphQL
import org.slf4j.LoggerFactory
import org.springframework.util.CollectionUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class QueryRestResource(val graphQL: GraphQL,
                        val personRepository: PersonRepository) {
  companion object {
    private val log = LoggerFactory.getLogger(QueryRestResource::class.simpleName)
  }

  /**
   * echo '{"query":"{ people { id name } }"}' | http post :8080
   * echo '{"query":"{ person(id: \"2\") { name } }"}' | http post :8080
   */
  @PostMapping("/")
  fun index(@RequestBody payload: Map<String, Any>): Map<String, Any> {

    val query = payload["query"] as String
    log.debug("query: {}", query)

    val result = graphQL.execute(query)
    val errors = result.errors

    if (CollectionUtils.isEmpty(errors)) {
      val data = result.getData<Any>()
      return mapOf("data" to data)
    }

    log.info("errors: {}", errors)
    return mapOf("errors" to errors)
  }

  @GetMapping("/")
  fun index() = mapOf("scema" to personRepository.findAll())
}
