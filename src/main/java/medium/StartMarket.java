package medium;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

import static medium.Util.checkNumber;
import static medium.Util.checkString;

public class StartMarket {
    public static void showMenu() {
        System.out.println("Menu:");
        System.out.println("addUser - add new user to the system\n" +
                "addProduct - add new product to the system\n" +
                "deleteUser - delete user from the system\n" +
                "deleteProduct - delete product from the system\n" +
                "listUsers - display list of all users\n" +
                "listProducts - display list of all products\n" +
                "buyProduct - buying product by user\n" +
                "listUserProducts - display list of user products by user id\n" +
                "listProductByUsers - display list of users that bought product by product id\n" +
                "menu - display menu\n" +
                "exit - exit\n");
    }

    public static User addUser(Scanner scanner) {
        User user = new User();
        System.out.println("Enter first name:");
        user.setFirstName(checkString(scanner.nextLine()));
        System.out.println("Enter last name:");
        user.setLastName(checkString(scanner.nextLine()));
        System.out.println("Enter amount of money:");
        user.setAmountMoney(checkNumber(Double.parseDouble(scanner.nextLine())));
        return user;
    }

    public static Product addProduct(Scanner scanner) {
        Product product = new Product();
        System.out.println("Enter name:");
        product.setName(checkString(scanner.nextLine()));
        System.out.println("Enter price:");
        product.setPrice(checkNumber(Double.parseDouble(scanner.nextLine())));
        return product;
    }

    public static UUID enterUUID(Scanner scanner) {
        return UUID.fromString(scanner.nextLine());
    }

    public static void execute(Marketplace marketplace, String command, Scanner scanner) {
        switch (command) {
            case "addUser":
                marketplace.addUser(addUser(scanner));
                break;
            case "addProduct":
                marketplace.addProduct(addProduct(scanner));
                break;
            case "deleteUser":
                System.out.println("Enter user id:");
                marketplace.deleteUser(enterUUID(scanner));
                break;
            case "deleteProduct":
                System.out.println("Enter product id:");
                marketplace.deleteProduct(enterUUID(scanner));
                break;
            case "listUsers":
                marketplace.displayListUsers();
                break;
            case "listProducts":
                marketplace.displayListProducts();
                break;
            case "buyProduct":
                System.out.println("Enter user id:");
                UUID userId = enterUUID(scanner);
                System.out.println("Enter product id:");
                UUID productId = enterUUID(scanner);
                marketplace.userBuyProduct(userId, productId);
                break;
            case "listUserProducts":
                System.out.println("Enter user id:");
                marketplace.displayListUserProductsByUserId(enterUUID(scanner));
                break;
            case "listProductByUsers":
                System.out.println("Enter product id:");
                marketplace.displayListUsersByProductId(enterUUID(scanner));
                break;
            case "menu":
                showMenu();
                break;
            default:
                System.out.println("Incorrect command\nTry again");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Marketplace!\n");
        showMenu();
        System.out.println("Enter command:");

        Marketplace marketplace = Marketplace.getInstance();

        Scanner scanner = new Scanner(System.in);
        String command;
        command = scanner.nextLine();

        while (!command.equals("exit")) {
            try {
                execute(marketplace, command, scanner);
            } catch (NoSuchElementException | IllegalArgumentException | ArithmeticException ex) {
                ex.printStackTrace();
                System.out.println();
                showMenu();
            }
            System.out.println("Enter command:");
            command = scanner.nextLine();
        }
        scanner.close();
    }
}
