package daggerok.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Person(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
                  var name: String? = null,
                  var addressId: Long? = null) : Serializable {

  companion object {
    const val serialVersionUID = 2L
  }
}

interface PersonRepository : JpaRepository<Person, Long>
