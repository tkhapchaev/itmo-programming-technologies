package is.technologies.banks.test;

import is.technologies.banks.business.entities.accounts.CreditAccount;
import is.technologies.banks.business.entities.accounts.DebitAccount;
import is.technologies.banks.business.entities.accounts.DepositAccount;
import is.technologies.banks.business.entities.accounts.DepositPercentage;
import is.technologies.banks.business.entities.bank.Bank;
import is.technologies.banks.business.entities.client.Client;
import is.technologies.banks.business.entities.transactions.Refill;
import is.technologies.banks.business.entities.transactions.Transfer;
import is.technologies.banks.business.entities.transactions.Withdrawal;
import is.technologies.banks.business.exceptions.*;
import is.technologies.banks.business.models.Address;
import is.technologies.banks.business.models.DepositRange;
import is.technologies.banks.business.models.Passport;
import is.technologies.banks.business.services.CentralBankImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Класс, содержащий тесты для центрального банка
 */
public class CentralBankTests {
    private final CentralBankImpl centralBank;

    public CentralBankTests() {
        centralBank = new CentralBankImpl();
    }

    /**
     * Тест на корректное срабатывание механизма перевода
     */
    @Test
    public void transferMoney_moneyHasBeenSuccessfullyTransferred() throws PassportException, AddressException, AccountException, MoneyException {
        Bank bank = centralBank.addBank(
                "Bank",
                100000,
                100000,
                3.5,
                500000,
                500,
                new DepositPercentage());

        Client client1 = centralBank.addClient(
                "Client 1",
                "Client 1",
                new Passport(100, 100),
                new Address("Russia", "Moscow", "Noviy arbat", 10));

        Client client2 = centralBank.addClient(
                "Client 2",
                "Client 2",
                new Passport(200, 200),
                new Address("Russia", "Moscow", "Noviy arbat", 15));

        DebitAccount debitAccount1 = centralBank.addDebitAccount(bank, client1);
        DebitAccount debitAccount2 = centralBank.addDebitAccount(bank, client2);

        var refill = new Refill(debitAccount1, 20000);
        var transfer = new Transfer(debitAccount1, debitAccount2, 10000);

        centralBank.executeTransaction(refill);
        centralBank.executeTransaction(transfer);

        Assert.assertEquals(10000, debitAccount1.getBalance(), 1);
        Assert.assertEquals(10000, debitAccount2.getBalance(), 1);
        Assert.assertTrue(centralBank.getTransactions().contains(transfer));
    }

    /**
     * Тест на корректное срабатывание механизма начисления коммиссий/процента на остаток
     */
    @Test
    public void checkReplenishment_replenishmentIsCorrect() throws PassportException, AddressException, AccountException, MoneyException {
        Bank bank = centralBank.addBank(
                "Bank",
                100000,
                100000,
                3.65,
                500000,
                500,
                new DepositPercentage());

        Client client = centralBank.addClient(
                "Client",
                "Client",
                new Passport(200, 200),
                new Address("Russia", "Moscow", "Noviy arbat", 15));

        DebitAccount debitAccount = centralBank.addDebitAccount(bank, client);

        var refill = new Refill(debitAccount, 500000);
        centralBank.executeTransaction(refill);

        centralBank.update(31);

        Assert.assertEquals(650000, debitAccount.getBalance(), 1);
    }

    /**
     * Тест на корректное срабатывание механизма получения уведомлений и подписки клиентов на различные новости внутри банка
     */
    @Test
    public void setNewCreditAccountCommission_clientHasReceivedNotification() throws PassportException, AddressException, BankException, AccountException {
        Bank bank = centralBank.addBank(
                "Bank",
                100000,
                100000,
                3.5,
                500000,
                500,
                new DepositPercentage());

        Client client = centralBank.addClient(
                "Client",
                "Client",
                new Passport(200, 200),
                new Address("Russia", "Moscow", "Noviy arbat", 15));

        CreditAccount creditAccount = centralBank.addCreditAccount(bank, client, 550000);
        String expectedNotification = "Notification from bank \"" + bank.name + "\": the commission for using your account with a negative balance has been changed. New value: 800.0.";

        bank.setCreditAccountCommission(800);

        Assert.assertTrue(client.getNotifications().contains(expectedNotification));
    }

    /**
     * Тест на отмену транзакций
     */
    @Test
    public void cancelTransaction_transactionHasBeenSuccessfullyCancelled() throws PassportException, AddressException, CentralBankException, AccountException, MoneyException {
        Bank bank = centralBank.addBank(
                "Bank",
                100000,
                100000,
                3.5,
                500000,
                500,
                new DepositPercentage());

        Client client = centralBank.addClient(
                "Client",
                "Client",
                new Passport(200, 200),
                new Address("Russia", "Moscow", "Noviy arbat", 15));

        DebitAccount debitAccount = centralBank.addDebitAccount(bank, client);

        var refill = new Refill(debitAccount, 500000);
        var withdrawal = new Withdrawal(debitAccount, 200000);

        centralBank.executeTransaction(refill);
        centralBank.executeTransaction(withdrawal);

        Assert.assertTrue(centralBank.getTransactions().contains(refill));
        Assert.assertTrue(centralBank.getTransactions().contains(withdrawal));

        centralBank.cancelTransaction(withdrawal);

        Assert.assertTrue(centralBank.getTransactions().contains(refill));
        Assert.assertFalse(centralBank.getTransactions().contains(withdrawal));
    }

    /**
     * Тест на ограничения депозитного счёта (перевод денег)
     */
    @Test
    public void transferMoneyBeforeDepositHasExpired_throwException() throws PassportException, AddressException, AccountException, DepositPercentageException, MoneyException {
        Bank bank = centralBank.addBank(
                "Bank",
                100000,
                100000,
                3.5,
                500000,
                500,
                new DepositPercentage());

        bank.addDepositRange(new DepositRange(0, 2000000, 3));

        Client client1 = centralBank.addClient(
                "Client 1",
                "Client 1",
                new Passport(100, 100),
                new Address("Russia", "Moscow", "Noviy arbat", 10));

        Client client2 = centralBank.addClient(
                "Client 2",
                "Client 2",
                new Passport(200, 200),
                new Address("Russia", "Moscow", "Noviy arbat", 15));

        DepositAccount depositAccount = centralBank.addDepositAccount(bank, client1, 1000000, 50);
        DebitAccount debitAccount = centralBank.addDebitAccount(bank, client2);

        var refill = new Refill(depositAccount, 50000);
        var transfer = new Transfer(depositAccount, debitAccount, 10000);

        centralBank.executeTransaction(refill);

        Assert.assertThrows(AccountException.class, () -> centralBank.executeTransaction(transfer));
    }

    /**
     * Тест на ограничения депозитного счёта (снятие денег)
     */
    @Test
    public void withdrawMoneyBeforeDepositHasExpired_throwException() throws PassportException, AddressException, AccountException, DepositPercentageException, MoneyException {
        Bank bank = centralBank.addBank(
                "Bank",
                100000,
                100000,
                3.5,
                500000,
                500,
                new DepositPercentage());

        bank.addDepositRange(new DepositRange(0, 2000000, 3));

        Client client = centralBank.addClient(
                "Client",
                "Client",
                new Passport(100, 100),
                new Address("Russia", "Moscow", "Noviy arbat", 10));

        DepositAccount depositAccount = centralBank.addDepositAccount(bank, client, 1000000, 50);

        var refill = new Refill(depositAccount, 50000);
        var withdrawal = new Withdrawal(depositAccount, 10000);

        centralBank.executeTransaction(refill);

        Assert.assertThrows(AccountException.class, () -> centralBank.executeTransaction(withdrawal));
    }

    /**
     * Тест на механизм ограчения счётов у неполностью идентифицорованных пользователей (снятие со счёта)
     */
    @Test
    public void exceedWithdrawalLimitForRestrictedAccount_throwException() throws AccountException, MoneyException {
        Bank bank = centralBank.addBank(
                "Bank",
                100000,
                100000,
                3.5,
                500000,
                500,
                new DepositPercentage());

        Client client = centralBank.addClient("Client", "Client", null, null);

        DebitAccount debitAccount = centralBank.addDebitAccount(bank, client);

        var refill = new Refill(debitAccount, 500000);
        var withdrawal = new Withdrawal(debitAccount, 200000);

        centralBank.executeTransaction(refill);

        Assert.assertThrows(AccountException.class, () -> centralBank.executeTransaction(withdrawal));
    }

    /**
     * Тест на механизм ограчения счётов у неполностью идентифицорованных пользователей (перевод со счёта)
     */
    @Test
    public void exceedTransferLimitForRestrictedAccount_throwException() throws PassportException, AddressException, AccountException, MoneyException {
        Bank bank = centralBank.addBank(
                "Bank",
                100000,
                100000,
                3.5,
                500000,
                500,
                new DepositPercentage());

        Client client1 = centralBank.addClient("Client 1", "Client 1", null, null);

        Client client2 = centralBank.addClient(
                "Client 2",
                "Client 2",
                new Passport(100, 100),
                new Address("Russia", "Moscow", "Noviy arbat", 10));

        DebitAccount debitAccount1 = centralBank.addDebitAccount(bank, client1);
        DebitAccount debitAccount2 = centralBank.addDebitAccount(bank, client2);

        var refill = new Refill(debitAccount1, 500000);
        var transfer = new Transfer(debitAccount1, debitAccount2, 200000);

        centralBank.executeTransaction(refill);

        Assert.assertThrows(AccountException.class, () -> centralBank.executeTransaction(transfer));
    }
}