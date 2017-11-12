package daggerok.domain

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
class QueryRestResource(val personRepository: PersonRepository) {

  @Value("classpath:graphql/schema.graphqls")
  lateinit var schemaResource: Resource

  @PostConstruct
  fun loadSchema() {
    val schema = schemaResource.file
  }

  @GetMapping("/")
  fun index() = mapOf("scema" to personRepository.findAll())
}
