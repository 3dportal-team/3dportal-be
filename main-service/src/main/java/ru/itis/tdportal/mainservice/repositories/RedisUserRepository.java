package ru.itis.tdportal.mainservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.tdportal.mainservice.models.entities.RedisUser;

public interface RedisUserRepository extends CrudRepository<RedisUser, String> {
}
