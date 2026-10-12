package com.convo.gateway.play.controller;

import com.convo.gateway.play.dto.NpcResponse;
import com.convo.gateway.play.dto.NpcSummary;
import com.convo.gateway.play.service.GatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    private Logger logger = LoggerFactory.getLogger(GatewayController.class);

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

}
