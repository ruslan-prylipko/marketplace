package medium;

import java.util.*;

public class Marketplace {
    private static Marketplace MARKETPLACE;
    private final List<User> users;
    private final List<Product> products;

    // Key is user, and value ia list of bought products;
    private final Map<User, List<Product>> userBuyProductMap;

    private Marketplace() {
        users = new ArrayList<>();
        products = new ArrayList<>();
        userBuyProductMap = new HashMap<>();
    }

    public static Marketplace getInstance() {
        if (MARKETPLACE == null) {
            MARKETPLACE = new Marketplace();
        }
        return MARKETPLACE;
    }

    public void displayListUsers() {
        for (User user: users) {
            System.out.print(user);
        }
    }

    public void displayListProducts() {
        for (Product product: products) {
            System.out.print(product);
        }
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean addProduct(Product product) {
        return products.add(product);
    }

    private User getUserById(UUID userId) {
        for (User user: users) {
            if (user.getId().equals(userId)) {
               return user;
            }
        }
        return null;
    }

    private Product getProductById(UUID productId) {
        for (Product product: products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Buying product by user
     * @param userId id of user who wants to buy product
     * @param productId id of product which user wants to buy
     * @throws ArithmeticException if user has not enough money to buy product
     * @throws NoSuchElementException if user or product with corresponding id does not exist
     */
    public void userBuyProduct(UUID userId, UUID productId) throws ArithmeticException, NoSuchElementException {
        User user = getUserById(userId);
        Product product = getProductById(productId);

        if (user == null) {
            throw new NoSuchElementException("Element does not exist: id = " + userId);
        }
        if (product == null) {
            throw new NoSuchElementException("Element does not exist: id = " + productId);
        }
        if (user.getAmountMoney() < product.getPrice()) {
            throw new ArithmeticException("Not enough money to buy");
        }

        List<Product> list;
        if (userBuyProductMap.containsKey(user)) {
            list = userBuyProductMap.get(user);
            list.add(product);
        } else {
            list = new ArrayList<>();
            list.add(product);
            userBuyProductMap.put(user, list);
        }

        user.setAmountMoney(user.getAmountMoney() - product.getPrice());
        System.out.println("Congratulations to user " + userId + " on the successful purchase of the " + productId + " product");
    }

    /**
     * Display list of user products by user id
     * @param userId id of user whose products will be displayed
     * @throws NoSuchElementException if user with userId does not exist
     */
    public void displayListUserProductsByUserId(UUID userId) throws NoSuchElementException {
        User user = getUserById(userId);
        if (user == null) {
            throw new NoSuchElementException("Element does not exist: id = " + userId);
        }
        List<Product> list = userBuyProductMap.get(user);
        for (Product product: list) {
            System.out.print(product);
        }
    }

    /**
     * Display list of users that bought product by product id
     * @param productId id of product which bought
     * @throws NoSuchElementException if product with productId does not exist
     */
    public void displayListUsersByProductId(UUID productId) throws NoSuchElementException {
        Product product = getProductById(productId);
        if (product == null) {
            throw new NoSuchElementException("Element does not exist: id = " + productId);
        }

        Iterator <Map.Entry<User, List<Product>>> iterator = userBuyProductMap.entrySet().iterator();
        User user;
        List<Product> tempList;
        Map.Entry<User, List<Product>> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            user = entry.getKey();
            tempList = entry.getValue();
            if (tempList.contains(product)) {
                System.out.print(user);
            }
        }
    }

    /**
     * Delete user from the system by his id
     * @param userId id of user that will be deleted
     * @return return true if user was successfully deleted, atherway false
     * @throws NoSuchElementException if user with userId does not exist
     */
    public boolean deleteUser(UUID userId) throws NoSuchElementException {
        User user = getUserById(userId);
        if (user == null) {
            throw new NoSuchElementException("Element does not exist: id = " + userId);
        }
        userBuyProductMap.remove(user);
        return users.remove(user);
    }

    /**
     * Delete product from the system by its id
     * @param productId id of product that will be deleted
     * @return return true if product was successfully deleted, atherway false
     * @throws NoSuchElementException if product with productId does not exist
     */
    public boolean deleteProduct(UUID productId) throws NoSuchElementException {
        Product product = getProductById(productId);
        if (product == null) {
            throw new NoSuchElementException("Element does not exist: id = " + productId);
        }

        Iterator <Map.Entry<User, List<Product>>> iterator = userBuyProductMap.entrySet().iterator();
        List<Product> tempList;
        Map.Entry<User, List<Product>> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            tempList = entry.getValue();
            tempList.remove(product);
        }
        return products.remove(product);
    }
}
