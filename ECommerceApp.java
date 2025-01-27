package wiprotrainingday2;


	import java.io.*;
	import java.util.*;

	class Product {
	    private int id;
	    private String name;
	    private double price;

	    public Product(int id, String name, double price) {
	        this.id = id;
	        this.name = name;
	        this.price = price;
	    }

	    public int getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }

	    public double getPrice() {
	        return price;
	    }

	    @Override
	    public String toString() {
	        return "Product[ID: " + id + ", Name: " + name + ", Price: $" + price + "]";
	    }
	}

	class Customer {
	    private int id;
	    private String name;

	    public Customer(int id, String name) {
	        this.id = id;
	        this.name = name;
	    }

	    public int getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }

	    @Override
	    public String toString() {
	        return "Customer[ID: " + id + ", Name: " + name + "]";
	    }
	}

	class Order {
	    private int orderId;
	    private Customer customer;
	    private List<Product> products;
	    private double totalCost;

	    public Order(int orderId, Customer customer) {
	        this.orderId = orderId;
	        this.customer = customer;
	        this.products = new ArrayList<>();
	    }

	    public void addProduct(Product product) {
	        products.add(product);
	        totalCost += product.getPrice();
	    }

	    public double getTotalCost() {
	        return totalCost;
	    }

	    @Override
	    public String toString() {
	        return "Order[ID: " + orderId + ", Customer: " + customer.getName() + 
	               ", Total Cost: $" + totalCost + ", Products: " + products + "]";
	    }

	    public String toFileString() {
	        return "OrderID: " + orderId + ", Customer: " + customer.getName() + 
	               ", Total Cost: $" + totalCost + ", Products: " + products + "\n";
	    }
	}

	public class ECommerceApp {
	    private List<Product> products = new ArrayList<>();
	    private List<Order> orders = new ArrayList<>();
	    private static int orderCounter = 1;

	    public void addProduct(int id, String name, double price) {
	        products.add(new Product(id, name, price));
	        System.out.println("Product added successfully.");
	    }

	    public void removeProduct(int id) {
	        products.removeIf(product -> product.getId() == id);
	        System.out.println("Product removed successfully.");
	    }

	    public Product getProductById(int id) {
	        for (Product product : products) {
	            if (product.getId() == id) return product;
	        }
	        return null;
	    }

	    public void createOrder(Customer customer, List<Integer> productIds) {
	        Order order = new Order(orderCounter++, customer);
	        for (int productId : productIds) {
	            Product product = getProductById(productId);
	            if (product != null) order.addProduct(product);
	        }
	        orders.add(order);
	        System.out.println("Order created successfully: " + order);
	        saveOrderHistory(order);
	    }

	    public void saveOrderHistory(Order order) {
	        try (FileWriter writer = new FileWriter("order_history.txt", true)) {
	            writer.write(order.toFileString());
	            System.out.println("Order saved to history.");
	        } catch (IOException e) {
	            System.out.println("Error saving order history: " + e.getMessage());
	        }
	    }

	    public void displayProducts() {
	        if (products.isEmpty()) {
	            System.out.println("No products available.");
	        } else {
	            System.out.println("Available Products:");
	            for (Product product : products) {
	                System.out.println(product);
	            }
	        }
	    }

	    public static void main(String[] args) {
	        ECommerceApp app = new ECommerceApp();
	        Scanner scanner = new Scanner(System.in);

	        // Sample data
	        app.addProduct(1, "Laptop", 1200.00);
	        app.addProduct(2, "Phone", 800.00);
	        app.addProduct(3, "Tablet", 300.00);

	        while (true) {
	            System.out.println("\nE-Commerce Menu:");
	            System.out.println("1. Display Products");
	            System.out.println("2. Add Product");
	            System.out.println("3. Remove Product");
	            System.out.println("4. Create Order");
	            System.out.println("5. Exit");
	            System.out.print("Choose an option: ");

	            int choice = scanner.nextInt();
	            switch (choice) {
	                case 1:
	                    app.displayProducts();
	                    break;

	                case 2:
	                    System.out.print("Enter product ID: ");
	                    int id = scanner.nextInt();
	                    scanner.nextLine(); // Consume newline
	                    System.out.print("Enter product name: ");
	                    String name = scanner.nextLine();
	                    System.out.print("Enter product price: ");
	                    double price = scanner.nextDouble();
	                    app.addProduct(id, name, price);
	                    break;

	                case 3:
	                    System.out.print("Enter product ID to remove: ");
	                    int removeId = scanner.nextInt();
	                    app.removeProduct(removeId);
	                    break;

	                case 4:
	                    System.out.print("Enter customer ID: ");
	                    int customerId = scanner.nextInt();
	                    scanner.nextLine(); // Consume newline
	                    System.out.print("Enter customer name: ");
	                    String customerName = scanner.nextLine();
	                    Customer customer = new Customer(customerId, customerName);

	                    System.out.print("Enter product IDs (comma separated): ");
	                    String[] ids = scanner.nextLine().split(",");
	                    List<Integer> productIds = new ArrayList<>();
	                    for (String pid : ids) productIds.add(Integer.parseInt(pid.trim()));

	                    app.createOrder(customer, productIds);
	                    break;

	                case 5:
	                    System.out.println("Exiting...");
	                    scanner.close();
	                    System.exit(0);

	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}


