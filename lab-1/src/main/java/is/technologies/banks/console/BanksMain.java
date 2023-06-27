package is.technologies.banks.console;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Главный класс приложения
 */
public class BanksMain {
    /**
     * Метод main. В зависимости от полученной команды вызывается соответствующий метод у класса-фасада
     */
    public static void main(String[] args) {
        var banksConsoleHandler = new BanksConsoleFacade();
        Scanner in = new Scanner(System.in);
        String command;

        ShowCommands();

        try {
            while (!Objects.equals(command = in.nextLine(), "/exit")) {
                String[] arguments = command.split(" ");

                switch (arguments[0]) {
                    case "/add-bank" -> {
                        String name = arguments[1],
                                transferLimit = arguments[2],
                                withdrawalLimit = arguments[3],
                                balancePercentage = arguments[4],
                                minimalCreditBalance = arguments[5],
                                creditAccountCommission = arguments[6];

                        banksConsoleHandler.addBank(
                                name,
                                transferLimit,
                                withdrawalLimit,
                                balancePercentage,
                                minimalCreditBalance,
                                creditAccountCommission);

                        System.out.println("Банк \"" + name + "\" успешно создан.");

                    }
                    case "/add-client" -> {
                        String name = arguments[1],
                                surname = arguments[2],
                                passportSeries = arguments[3],
                                passportNumber = arguments[4],
                                country = arguments[5],
                                city = arguments[6],
                                street = arguments[7],
                                houseNumber = arguments[8];

                        banksConsoleHandler.addClient(
                                name,
                                surname,
                                passportSeries,
                                passportNumber,
                                country,
                                city,
                                street,
                                houseNumber);

                        System.out.println("Клиент \"" + name + " " + surname + "\" успешно создан.");

                    }
                    case "/add-debit-account" -> {
                        String accountId = banksConsoleHandler.addDebitAccount(arguments[1], arguments[2], arguments[3]);

                        System.out.println("Счёт \"" + accountId + "\" успешно создан.");

                    }
                    case "/add-deposit-account" -> {
                        String accountId = banksConsoleHandler.addDepositAccount(
                                arguments[1],
                                arguments[2],
                                arguments[3],
                                arguments[4],
                                arguments[5]);

                        System.out.println("Счёт \"" + accountId + "\" успешно создан.");

                    }
                    case "/add-credit-account" -> {
                        String accountId = banksConsoleHandler.addCreditAccount(
                                arguments[1],
                                arguments[2],
                                arguments[3],
                                arguments[4]);

                        System.out.println("Счёт \"" + accountId + "\" успешно создан.");

                    }
                    case "/top-up" -> banksConsoleHandler.topUp(arguments[1], arguments[2]);


                    case "/withdraw" -> banksConsoleHandler.withdraw(arguments[1], arguments[2]);


                    case "/transfer" -> banksConsoleHandler.transfer(arguments[1], arguments[2], arguments[3]);


                    case "/get-balance" -> System.out.println(banksConsoleHandler.getBalance(arguments[1]));


                    case "/get-accounts" -> {
                        List<String> accounts = banksConsoleHandler.getAccounts(arguments[1], arguments[2]);

                        for (String account : accounts) {
                            System.out.println(account);
                        }

                    }
                    case "/exit" -> System.exit(0);
                    default -> System.out.println("Неизвестная команда.");
                }
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Метод, показывающий пользователю все возможные команды CLI
     */
    private static void ShowCommands() {
        System.out.println("""

                (!) CENTRAL BANK CLI

                Доступны 11 команд:
                """);

        System.out.println(
                "/add-bank [имя банка] [лимит на перевод] [лимит на снятие] [процент на остаток] [минимальный кредитный баланс] [комиссия (для кредитных счетов)]");

        System.out.println(
                "/add-client [имя клиента] [фамилия клиента] [серия паспорта] [номер паспорта] [страна] [город] [улица] [дом]");

        System.out.println("/add-debit-account [имя банка] [имя клиента] [фамилия клиента]");

        System.out.println(
                "/add-deposit-account [имя банка] [имя клиента] [фамилия клиента] [депозит] [продолжительность]");

        System.out.println("/add-credit-account [имя банка] [имя клиента] [фамилия клиента] [кредит]");

        System.out.println("/top-up [ID счёта] [сумма]");

        System.out.println("/withdraw [ID счёта] [сумма]");

        System.out.println("/transfer [ID счёта отправителя] [ID счёта получателя] [сумма]");

        System.out.println("/get-balance [ID счёта]");

        System.out.println("/get-accounts [имя клиента] [фамилия клиента]");

        System.out.println("/get-notifications [имя клиента] [фамилия клиента]");

        System.out.println("/exit - выход.\n");
    }
}