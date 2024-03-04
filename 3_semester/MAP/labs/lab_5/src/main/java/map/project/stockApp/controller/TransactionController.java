package map.project.stockApp.controller;

import map.project.stockApp.model.Role;
import map.project.stockApp.model.Transaction;
import map.project.stockApp.service.TransactionService;
import map.project.stockApp.utils.autorization.CredentialChecker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TransactionController {
    private final TransactionService transactionService;
    private final CredentialChecker credentialChecker;

    public TransactionController(TransactionService transactionService, CredentialChecker credentialChecker) {
        this.transactionService = transactionService;
        this.credentialChecker = credentialChecker;
    }

    @GetMapping("/transactions")
    @ResponseBody
    List<Transaction> seeTransactions() {
        return transactionService.getAll();
    }

    @GetMapping("/transactions/user")
    @ResponseBody
    String seeTransactionsUser(@RequestHeader("Authorization") String authorizationHeader) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.User)) {
//            return transactionService.getByUser(credentialChecker.returnUser(authorizationHeader));
            return transactionService.getByUser(credentialChecker.returnUser(authorizationHeader))
                    .stream()
                    .map(Transaction::toString) // Assuming Transaction has a toString method
                    .collect(Collectors.joining("\n"));
        }

        return null;
    }

    @DeleteMapping(path = "/transactions/delete/{id}")
    public String deleteTransaction(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            transactionService.deleteTransaction(id);
        }
        return "redirect:/transactions";
    }


}

