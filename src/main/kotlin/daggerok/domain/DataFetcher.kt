package daggerok.domain

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Service

@Service
class PeopleDataFetcher(val personRepository: PersonRepository) : DataFetcher<List<Person>> {

  override fun get(environment: DataFetchingEnvironment?) =
      personRepository.findAll()
}
