package ru.itis.tdportal.mainservice.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Service;
import ru.itis.tdportal.mainservice.models.entities.RedisUser;
import ru.itis.tdportal.mainservice.models.exceptions.RedisUserNotFoundException;
import ru.itis.tdportal.mainservice.repositories.RedisUserRepository;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisUserService {

    private final RedisUserRepository repository;

    private RedisUser getRedisUserOrThrow(String redisUserID) {
        return repository.findById(redisUserID)
                .orElseThrow(() -> new RedisUserNotFoundException(
                        String.format("RedisUser isn't found by id=%s", redisUserID)
                ));
    }

    public String saveRedisUser(@NonNull Long userId) {
        RedisUser redisUser = new RedisUser();
        redisUser.setId(UUID.randomUUID().toString());
        redisUser.setPortalUserId(userId);

        return repository.save(redisUser).getId();
    }

    public void saveToCart(@NonNull Long id, @NonNull String redisUserID) {
        // TODO: ограничить размер корзины; уведомлять если такой элемент уже имеется
        RedisUser redisUser = getRedisUserOrThrow(redisUserID);
        Set<Long> oldCart = getRedisUserCart(redisUserID);
        Set<Long> newCart = SetUtils.union(oldCart, Set.of(id));
        redisUser.setCart(newCart);
        repository.save(redisUser);
    }

    public Set<Long> getRedisUserCart(@NonNull String redisUserID) {
        RedisUser redisUser = getRedisUserOrThrow(redisUserID);
        Set<Long> oldCart = redisUser.getCart();
        return Objects.isNull(oldCart) ? Collections.emptySet() : oldCart;
    }

    public void deleteFromCart(Long modelId, String redisUserID) {
        RedisUser redisUser = getRedisUserOrThrow(redisUserID);
        Set<Long> oldCart = getRedisUserCart(redisUserID);
        Set<Long> newCart = SetUtils.difference(oldCart, Set.of(modelId));
        redisUser.setCart(newCart);
        repository.save(redisUser);
    }
}
