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

    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/npc/{npcId}")
    public NpcResponse getNpc(@PathVariable("npcId") Long npcId) {
        logger.info("getNpc() called with npcId {}", npcId);
        return gatewayService.getNpc(npcId);
    }

    @GetMapping("/npcs")
    public List<NpcSummary> getAllNpcs(
    ) {
        logger.info("getAllNpcs() called");

        List<NpcSummary> npcs = npcRepository.findAll()
                .stream()
                .map(NpcSummary::from)
                .toList();

        logger.info("getAllNpcs() is returning {} records", npcs.size());

        return npcs;
    }

    @PostMapping("/npc/create")
    public NpcSummary createNpc(@RequestBody NpcSummary npcSummary) {
        logger.info("createNpc() called with npcSummary {}", npcSummary);
        NpcSummary npc = gatewayService.createNpc(npcSummary);
        return NpcSummary.from(npc);
    }


}
