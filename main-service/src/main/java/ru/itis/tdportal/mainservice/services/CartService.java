package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.mainservice.dtos.CartDto;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;

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
        modelService.getModelFileOrThrow(modelId);
        redisUserService.saveToCart(modelId, currentUser.getRedisUserId().toString());
        return getCurrentUserCart();
    }

    @Transactional(readOnly = true)
    public CartDto getCurrentUserCart() {
        PortalUserDto currentUser = authenticationService.getCurrentUser();
        Set<Long> cart = redisUserService.getRedisUserCart(currentUser.getRedisUserId().toString());
        Set<ModelFileDto> modelFileDtos = modelService.getModelFilesById(cart);

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
}
