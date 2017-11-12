package daggerok.graphql

import daggerok.domain.Person
import daggerok.domain.PersonRepository
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Service

/**
 * echo '{"query":"{ people { id name } }"}' | http post :8080
 */
@Service
class PeopleDataFetcher(val personRepository: PersonRepository) : DataFetcher<List<Person>> {

  override fun get(environment: DataFetchingEnvironment?) =
      personRepository.findAll()
}

/**
 * echo '{"query":"{ person(id: \"1\") { name } }"}' | http post :8080
 */
@Service
class PersonDataFetcher(val personRepository: PersonRepository) : DataFetcher<Person> {

  override fun get(env: DataFetchingEnvironment?): Person {
    if (!env!!.containsArgument("id")) throw RuntimeException()
    val string = env.arguments["id"] as String
    val id = string.toLong()
    return personRepository.findOne(id)
  }
}

// TODO: AddressesDataFetcher
// TODO: AddressDataFetcher
