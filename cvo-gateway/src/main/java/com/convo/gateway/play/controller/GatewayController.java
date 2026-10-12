package com.convo.gateway.play.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// This controller may not be needed because routes
// declared in application.properties are handled by Spring Cloud Gateway
// However, operations which require additional processing or logic may be implemented here in the future.
@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    private Logger logger = LoggerFactory.getLogger(GatewayController.class);

    public GatewayController() {
    }

}
