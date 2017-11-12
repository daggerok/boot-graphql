package daggerok

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BootGrapgqlApplication

fun main(args: Array<String>) {
    SpringApplication.run(BootGrapgqlApplication::class.java, *args)
}
