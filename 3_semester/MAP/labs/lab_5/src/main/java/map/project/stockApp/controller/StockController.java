package map.project.stockApp.controller;

import map.project.stockApp.controller.requests.GrowthStockRequest;
import map.project.stockApp.controller.requests.HybridStockRequest;
import map.project.stockApp.controller.requests.SimpleStockRequest;
import map.project.stockApp.controller.requests.ValueStockRequest;
import map.project.stockApp.model.*;
import map.project.stockApp.service.*;
import map.project.stockApp.utils.autorization.CredentialChecker;
import map.project.stockApp.utils.factory.StockValuationFactory;
import map.project.stockApp.utils.strategyAndTemplateMethod.AbstractStockProfit;
import map.project.stockApp.utils.strategyAndTemplateMethod.StockProfitBearMarket;
import map.project.stockApp.utils.strategyAndTemplateMethod.StockProfitBullMarket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StockController {
    private final ValueStockService valueStockService;
    private final GrowthStockService growthStockService;
    private final HybridStockService hybridStockService;
    private final StockService simpleStockService;
    private final MarketService marketService;
    private final CompanyService companyService;
    public AbstractStockProfit stockProfit;


    private final CredentialChecker credentialChecker;

    public StockController(ValueStockService valueStockService, GrowthStockService growthStockService, StockService simpleStockService, MarketService marketService, CompanyService companyService, CredentialChecker credentialChecker, HybridStockService hybridStockService) {
        this.valueStockService = valueStockService;
        this.growthStockService = growthStockService;
        this.simpleStockService = simpleStockService;
        this.marketService = marketService;
        this.companyService = companyService;
        this.credentialChecker = credentialChecker;
        this.hybridStockService = hybridStockService;

    }

    @GetMapping("/stocks/growth")
    @ResponseBody
    List<String> seeGrowthStocks() {
        List<GrowthStock> growthStocks = growthStockService.getAll();
        List<String> res = new ArrayList<>();
        for (GrowthStock growthStock : growthStocks) {

            res.add(growthStock.toString() + growthStock.getCompany().toString());
        }

        return res;
    }

    @GetMapping("/stocks/value")
    @ResponseBody
    List<String> seeValueStocks() {
        List<ValueStock> valueStocks = valueStockService.getAll();
        List<String> res = new ArrayList<>();
        for (ValueStock valueStock : valueStocks) {

            res.add(valueStock.toString() + valueStock.getCompany().toString());
        }

        return res;
    }


    @GetMapping("/stocks/value/profit/{conditions}")
    @ResponseBody
    public List<String> seeValueProfit(@PathVariable("conditions") String conditions) {
        if (conditions.equals("BullMarket")) {
            stockProfit = new StockProfitBullMarket();
        } else {
            stockProfit = new StockProfitBearMarket();
        }
        stockProfit.setStrategy(StockValuationFactory.getValuation("Value"));
        List<ValueStock> valueStocks = valueStockService.getAll();
        List<String> profits = new ArrayList<>();
        for (ValueStock valueStock : valueStocks) {

            double profit = stockProfit.calculateProfit(valueStock);
            profits.add(valueStock.toString() + " profit " + profit);
        }

        return profits;
    }

    @GetMapping("/stocks/growth/profit/{conditions}")
    @ResponseBody
    public List<String> seeGrowthProfit(@PathVariable("conditions") String conditions) {
        if (conditions.equals("BullMarket")) {
            stockProfit = new StockProfitBullMarket();
        } else {
            stockProfit = new StockProfitBearMarket();
        }
        stockProfit.setStrategy(StockValuationFactory.getValuation("Growth"));
        List<GrowthStock> growthStocks = growthStockService.getAll();
        List<String> profits = new ArrayList<>();

        for (GrowthStock growthStock : growthStocks) {

            double profit = stockProfit.calculateProfit(growthStock);
            profits.add(growthStock.toString() + " profit " + profit);
        }

        return profits;
    }

    @PostMapping("/stocks/growth/add")
    public String addGrowthStock(@RequestHeader("Authorization") String authorizationHeader, @RequestBody GrowthStockRequest request) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            GrowthStock growthStock = new GrowthStock(request.getId(), request.getName(), this.companyService.searchCompany(request.getCompany()), this.marketService.searchMarket(request.getMarket()), request.getGrowth_rate());
            growthStockService.addGrowthStockObject(growthStock);
        }
        return "redirect:/stocks/growth";


    }


    @DeleteMapping(path = "/stocks/growth/delete/{id}")
    public String deleteGrowthStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            growthStockService.removeGrowthStock(id);
        }
        return "redirect:/stocks/growth";
    }

    @PutMapping("/stocks/growth/update/{id}")
    public String updateGrowthStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id, @RequestBody GrowthStockRequest growthStockRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            growthStockService.updateGrowthStock(id, growthStockRequest.getName(), this.companyService.searchCompany(growthStockRequest.getCompany()), this.marketService.searchMarket(growthStockRequest.getMarket()), growthStockRequest.getGrowth_rate());
        }
        return "redirect:/stocks/growth";
    }

    @PutMapping("/stocks/growth/update/undo/{id}")
    public String updateGrowthStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            growthStockService.undoUpdate(id);
        }
        return "redirect:/stocks/growth";
    }

    @PostMapping("/stocks/value/add")
    public String addValueStock(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ValueStockRequest request) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            ValueStock valueStock = new ValueStock(request.getId(), request.getName(), this.companyService.searchCompany(request.getCompany()), this.marketService.searchMarket(request.getMarket()), request.getDivident_rate());
            valueStockService.addValueStockObject(valueStock);
        }
        return "redirect:/stocks/value";
    }

    @DeleteMapping(path = "/stocks/value/delete/{id}")
    public String deleteValueStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            valueStockService.removeValueStock(id);
        }
        return "redirect:/stocks/value";

    }

    @PutMapping("/stocks/value/update/{id}")
    public String updateValueStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id, @RequestBody ValueStockRequest valueStockRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            System.out.println(valueStockRequest);
            valueStockService.updateValueStock(id, valueStockRequest.getName(), this.companyService.searchCompany(valueStockRequest.getCompany()), this.marketService.searchMarket(valueStockRequest.getMarket()), valueStockRequest.getDivident_rate());
        }
        return "redirect:/stocks/value";
    }

    @PutMapping("/stocks/value/update/undo/{id}")
    public String updateValueStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            valueStockService.undoUpdate(id);
        }
        return "redirect:/stocks/value";
    }


    @GetMapping("/stocks/hybrid")
    @ResponseBody
    List<String > seeHybridStocks() {
        List<HybridStock> hybridStocks = hybridStockService.getAll();
        List<String> res = new ArrayList<>();
        for (HybridStock hybridStock : hybridStocks) {

            res.add(hybridStock.toString() + hybridStock.getCompany().toString());
        }

        return res;
    }


    @GetMapping("/stocks/hybrid/profit/{conditions}")
    @ResponseBody
    public List<String> seeHybridProfit(@PathVariable("conditions") String conditions) {
        if (conditions.equals("BullMarket")) {
            stockProfit = new StockProfitBullMarket();
        } else {
            stockProfit = new StockProfitBearMarket();
        }
        stockProfit.setStrategy(StockValuationFactory.getValuation("Hybrid"));
        List<HybridStock> hybridStocks = hybridStockService.getAll();
        List<String> profits = new ArrayList<>();

        for (HybridStock hybridStock : hybridStocks) {

            double profit = stockProfit.calculateProfit(hybridStock);
            profits.add(hybridStock.toString() + " profit " + profit);
        }

        return profits;
    }

    @PostMapping("/stocks/hybrid/add")
    public String addHybridStock(@RequestHeader("Authorization") String authorizationHeader, @RequestBody HybridStockRequest request) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            HybridStock hybridStock = new HybridStock(request.getId(), request.getName(), this.companyService.searchCompany(request.getCompany()), this.marketService.searchMarket(request.getMarket()), request.getGrowth_rate(), request.getDivident_rate());
            hybridStockService.addHybridStockObject(hybridStock);
        }
        return "redirect:/stocks/hybrid";


    }


    @PutMapping("/stocks/hybrid/update/undo/{id}")
    public String updateHybridStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            hybridStockService.undoUpdate(id);
        }
        return "redirect:/stocks/hybrid";
    }


    @DeleteMapping(path = "/stocks/hybrid/delete/{id}")
    public String deleteHybridStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            hybridStockService.removeHybridStock(id);
        }
        return "redirect:/stocks/hybrid";

    }

    @PutMapping("/stocks/hybrid/update/{id}")
    public String updateHybridStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id, @RequestBody HybridStockRequest hybridStockRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            System.out.println(hybridStockRequest);
            hybridStockService.updateHybridStock(id, hybridStockRequest.getName(), this.companyService.searchCompany(hybridStockRequest.getCompany()), this.marketService.searchMarket(hybridStockRequest.getMarket()), hybridStockRequest.getGrowth_rate(), hybridStockRequest.getDivident_rate());
        }
        return "redirect:/stocks/hybrid";
    }


    @GetMapping("/stocks/simple")
    @ResponseBody
    List<String> seeSimpleStocks() {
        List<Stock> stocks = simpleStockService.getAll();
        List<String> res = new ArrayList<>();
        for (Stock stock : stocks) {

            res.add(stock.toString() + stock.getCompany().toString());
        }

        return res;
    }


    @GetMapping("/stocks/simple/profit/{conditions}")
    @ResponseBody
    public List<String> seeSimpleStockProfit(@PathVariable("conditions") String conditions) {
        if (conditions.equals("BullMarket")) {
            stockProfit = new StockProfitBullMarket();
        } else {
            stockProfit = new StockProfitBearMarket();
        }
        stockProfit.setStrategy(StockValuationFactory.getValuation("Simple"));
        List<Stock> stocks = simpleStockService.getAll();
        List<String> profits = new ArrayList<>();

        for (Stock stock : stocks) {

            double profit = stockProfit.calculateProfit(stock);
            profits.add(stock.toString() + " profit " + profit);
        }

        return profits;
    }

    @PostMapping("/stocks/simple/add")
    public String addSimpleStock(@RequestHeader("Authorization") String authorizationHeader, @RequestBody SimpleStockRequest request) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            Stock stock = new Stock(request.getId(), request.getName(), this.companyService.searchCompany(request.getCompany()), this.marketService.searchMarket(request.getMarket()));
            simpleStockService.addSimpleStockObject(stock);
        }
        return "redirect:/stocks/hybrid";


    }


    @PutMapping("/stocks/simple/update/undo/{id}")
    public String updateSimpleStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            simpleStockService.undoUpdate(id);
        }
        return "redirect:/stocks/simple";
    }


    @DeleteMapping(path = "/stocks/simple/delete/{id}")
    public String deleteSimpleStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            simpleStockService.removeSimpleStock(id);
        }
        return "redirect:/stocks/simple";

    }

    @PutMapping("/stocks/simple/update/{id}")
    public String updateSimpleStock(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id, @RequestBody SimpleStockRequest simpleStockRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            System.out.println(simpleStockRequest);
            simpleStockService.updateSimpleStock(id, simpleStockRequest.getName(), this.companyService.searchCompany(simpleStockRequest.getCompany()), this.marketService.searchMarket(simpleStockRequest.getMarket()));
        }
        return "redirect:/stocks/simple";
    }

}
