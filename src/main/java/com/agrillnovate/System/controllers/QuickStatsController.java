package com.agrillnovate.System.controllers;

import com.agrillnovate.System.dto.QuickStats;
import com.agrillnovate.System.service.QuickStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class QuickStatsController {

    @Autowired
    private QuickStatsService quickStatsService;

    @GetMapping("/quick")
    public QuickStats getQuickStats(@AuthenticationPrincipal User user) {
        return quickStatsService.getQuickStats(user.getUsername());
    }
}
