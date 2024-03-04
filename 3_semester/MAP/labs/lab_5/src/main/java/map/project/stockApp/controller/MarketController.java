package map.project.stockApp.controller;

import map.project.stockApp.controller.requests.MarketRequest;
import map.project.stockApp.model.Market;
import map.project.stockApp.model.Role;
import map.project.stockApp.service.MarketService;
import map.project.stockApp.utils.autorization.CredentialChecker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MarketController {
    private final MarketService marketService;
    private final CredentialChecker credentialChecker;

    public MarketController(MarketService marketService, CredentialChecker credentialChecker) {
        this.marketService = marketService;
        this.credentialChecker = credentialChecker;
    }

    @GetMapping("/markets")
    @ResponseBody
    List<Market> seeMarkets() {
        return marketService.getAll();
    }

    @PostMapping("/markets/add")
    public String addMarket(@RequestHeader("Authorization") String authorizationHeader, @RequestBody MarketRequest marketRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            Market market = new Market(marketRequest.getId(), marketRequest.getName(), marketRequest.getLocation());
            marketService.addMarketObject(market);
        }
        return "redirect:/markets";
    }

    @DeleteMapping(path = "/markets/delete/{id}")
    public String deleteMarket(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            marketService.deleteMarket(id);
        }
        return "redirect:/markets";
    }

    @PutMapping(path = "/markets/update/{id}")
    public String updateMarket(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id, @RequestBody MarketRequest marketRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            marketService.updateMarket(id, marketRequest.getName(), marketRequest.getLocation());
        }
        return "redirect:/markets";
    }
}
