package com.example.tradingapp.controllers;

import com.example.tradingapp.model.DigitalAsset;
import com.example.tradingapp.services.DigitalAssetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AssetController extends BaseController {

    @Autowired
    private DigitalAssetService digitalAssetService;

    @GetMapping("/assets")
    public List<DigitalAsset> getAssets(HttpSession session) {
        validateLogged(session);
        int userId = (int) session.getAttribute(USER_ID);
        return digitalAssetService.getAllByUserId(userId);
    }
}
