package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        if (
            account.getUsername().isEmpty() ||
            account.getPassword().length() < 4 ||
            accountDAO.getAccountByUsername(account.getUsername()) != null
        ) {
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account) {
        Account loggedAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (loggedAccount == null || !loggedAccount.getPassword().equals(account.getPassword())) {
            return null;
        }
        return loggedAccount;
    }
}
