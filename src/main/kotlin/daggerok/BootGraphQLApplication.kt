package daggerok

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BootGraphQLApplication

fun main(args: Array<String>) {
  SpringApplication.run(BootGraphQLApplication::class.java, *args)
}
