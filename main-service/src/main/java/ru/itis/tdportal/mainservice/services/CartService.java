package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.common.models.entities.Money;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.mainservice.dtos.CartDto;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.mainservice.models.exceptions.ModelFileIsFreeException;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {

    private final AuthenticationService authenticationService;
    private final RedisUserService redisUserService;
    private final ModelService modelService;

    @Transactional
    public CartDto saveModel(Long modelId) {
        PortalUserDto currentUser = authenticationService.getCurrentUser();

        ModelFile modelFile = modelService.getModelFileOrThrow(modelId);
        Money price = modelFile.getPrice();
        if (Objects.isNull(price) || Objects.isNull(price.getValue())) {
            throw new ModelFileIsFreeException("Model can't be added to cart. It's free now");
        }

        redisUserService.saveToCart(modelId, currentUser.getRedisUserId().toString());
        return getCurrentUserCart();
    }

    @Transactional(readOnly = true)
    public CartDto getCurrentUserCart() {
        PortalUserDto currentUser = authenticationService.getCurrentUser();
        Set<Long> cart = redisUserService.getRedisUserCart(currentUser.getRedisUserId().toString());
        Set<ModelFileDto> modelFileDtos = modelService.getModelFilesByIds(cart);

        CartDto cartDto = new CartDto();
        cartDto.setModelFiles(modelFileDtos);
        return cartDto;
    }

    @Transactional
    public CartDto deleteModel(Long modelId) {
        PortalUserDto currentUser = authenticationService.getCurrentUser();
        redisUserService.deleteFromCart(modelId, currentUser.getRedisUserId().toString());
        return getCurrentUserCart();
    }

    @Transactional
    public void clearCurrentUserCart() {
        PortalUserDto currentUser = authenticationService.getCurrentUser();
        redisUserService.clearCart(currentUser.getRedisUserId().toString());
    }
}
