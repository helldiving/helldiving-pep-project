package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

public AccountService() {
    accountDAO = new AccountDAO();
}

public AccountService(AccountDAO accountDAO) {
    this.accountDAO = accountDAO;
}

public Account registration(Account account) {
    if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
        return null;
    }
    return accountDAO.createAccount(account);
}

public Account login(Account account) {
    return accountDAO.userLogin(account);

}

}
