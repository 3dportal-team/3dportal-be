package ru.itis.tdportal.mainservice.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tdportal.mainservice.services.OrderBatchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-batch")
public class OrderBatchController {

    private final OrderBatchService orderBatchService;

}
