package ru.itis.tdportal.mainservice.models.entities;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.Set;

@Data
@RedisHash("user")
public class RedisUser {

    @Id
    private String id;
    private Set<Long> cart;
    private Long portalUserId;
}