package daggerok

import daggerok.domain.Address
import daggerok.domain.AddressRepository
import daggerok.domain.Person
import daggerok.domain.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Configuration
class TestData(val addressRepository: AddressRepository,
               val personRepository: PersonRepository) {

  companion object {
    private val log = LoggerFactory.getLogger("test data logger")
  }

  @Transactional
  fun deleteAll() {
    addressRepository.deleteAll()
    personRepository.deleteAll()
  }

  @Transactional
  fun saveAddress(address: Address) = addressRepository.save(address)

  @Transactional
  fun savePerson(person: Person) = personRepository.save(person)

  @Bean
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  fun runner() = ApplicationRunner {

    deleteAll()

    arrayOf("max", "bax", "fax")
        .map { Person(name = it.capitalize()) }
        .map {
          val address = Address(name = "${it.name}'s address")
          it.address = saveAddress(address)
          return@map it
        }
        .forEach { savePerson(it) }

    personRepository.findAll()
        .forEach { log.info("user: {}", it) }
  }
}
