package daggerok

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestResource {

  @Value("classpath:graphql/schema.graphqls")
  lateinit var schema: Resource

  @GetMapping
  fun index() = mapOf("scema" to schema)
}

@SpringBootApplication
class BootGraphQLApplication

fun main(args: Array<String>) {
  SpringApplication.run(BootGraphQLApplication::class.java, *args)
}
