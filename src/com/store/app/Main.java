package com.store.app;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.store.data.Repository;
import com.store.exceptions.InsufficientStockException;
import com.store.model.*;
import com.store.patterns.*;

public class Main {

	public static void main(String[] args) {
		StoreConfig config = StoreConfig.getInstance();
		System.out.println("Bienvenido a " + config.getStoreName() + "!");
		
		Repository<Product> productRepo = new Repository<>();
		Repository<Customer> customerRepo = new Repository<>();
		Repository<SalesReceipt> receiptRepo =  new Repository<>();
		
		seedDatabase(productRepo, customerRepo);
		
		Customer currentCustomer = customerRepo.findFirst(c -> c.getId() == 1);
		ShoppingCart cart = new ShoppingCart();
		
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		while (running) {
			printMenu();
			System.out.print("Elija una opción: ");
			String choice = scanner.nextLine();
			
			switch (choice) {
			case "1":
				displayCatalog(productRepo);
				break;
			case "2":
				displayCatalogSortedByPrice(productRepo);
				break;
			case "3":
				addToCart(scanner, productRepo, cart);
				break;
			case "4":
				viewCart(cart);
				break;
			case "5":
				applyDiscount(scanner, cart);
				break;
			case "6":
				viewLinkOrTracking(productRepo);
				break;
			case "7":
				processCheckout(cart, currentCustomer, receiptRepo);
				break;
			case "8":
				viewReceiptHistory(receiptRepo);
				break;
			case "0":
				running = false;
				System.out.println("Gracias por visitar " + config.getStoreName() + ". Hasta luego!");
				break;
			default:
				System.out.println("Opción inválida, intente de nuevo.");
			}
		}
		
		scanner.close();
	}
	
	// metodos
	
	private static void printMenu() {
		System.out.println("\n======= MENU PRINCIPAL =======");
        System.out.println("1. Ver catálogo de productos");
        System.out.println("2. Ver catálogo de productos por precio");
        System.out.println("3. Agregar producto a carrito");
        System.out.println("4. Ver carrito y total");
        System.out.println("5. Aplicar descuento");
        System.out.println("6. Descargas digitales");
        System.out.println("7. Finalizar compra");
        System.out.println("8. Ver historial de recibos");
        System.out.println("0. Salir");
        System.out.println("===============================");
	}
	
	private static void seedDatabase(Repository<Product> productRepo, Repository<Customer> customerRepo) {
		productRepo.add(new PhysicalProduct("Teclado mecánico", 1120.00, "Electrónicos", 1.2, 5.00));
		productRepo.add(new PhysicalProduct("Mouse para gaming", 700.00, "Electrónicos", 0.4, 4.00));
		
		productRepo.add(new DigitalProduct("eBook de Masterclass Java", 430.00, "Libros", 15.0, "ebooks/java-guide.pdf"));
		productRepo.add(new DigitalProduct("Licencia de IDE Enterprise", 3400.00, "Software", 500.0, "keys/ide-license.key"));
		
		Address addr = new Address("000 Calle Yoquesé", "Monterrey", "64000", "México");
		Customer customer = new Customer("Fulanito de Tal", "fulano@zutanomail.com", addr);
		customerRepo.add(customer);
	}
	
	private static void displayCatalog(Repository<Product> productRepo) {
		System.out.println("\n--- CATALOGO DE PRODUCTOS ---");
		for (Product p : productRepo.getAll()) {
			System.out.println("ID: " + p.getId() + " | " + p.getName() + " | Precio base: $" + p.getBasePrice() + " | Precio final: $" + String.format("%.2f", p.calculateFinalPrice()) + " (" + p.getClass().getSimpleName() + ")");
		}
	}
	
	private static void displayCatalogSortedByPrice(Repository<Product> productRepo) {
		System.out.println("\n--- CATALOGO DE PRODUCTOS (Ordenado por precio) ---");
		List<Product> sortedList = productRepo.getAll();
		Collections.sort(sortedList);
		
		for (Product p : sortedList) {
			System.out.println("$" + p.getBasePrice() + " - " + p.getName());
		}
	}
	
	private static void addToCart(Scanner scanner, Repository<Product> productRepo, ShoppingCart cart) {
		displayCatalog(productRepo);
		System.out.println("Ingrese ID del producto que quiere agregar: ");
		int id = Integer.parseInt(scanner.nextLine());
		
		Product selectedProduct = productRepo.findFirst(p -> p.getId() == id);
		
		if (selectedProduct != null) {
			System.out.print("Ingrese cantidad: ");
			int qty = Integer.parseInt(scanner.nextLine());
			cart.addItem(selectedProduct, qty);
			System.out.println("Se agregaron " + qty + " de " + selectedProduct.getName() + " al carrito.");
		} else {
			System.out.println("El producto solicitado no fue encontrado.");
		}
	}
	
	private static void viewCart(ShoppingCart cart) {
		System.out.println("\n--- CARRITO DE COMPRAS ---");
		if (cart.getItems().isEmpty()) {
			System.out.println("Su carrito está vacío!");
			return;
		}
		
		for (CartItem item : cart.getItems()) {
			System.out.println("- " + item.getProduct().getName() + " x" + item.getQuantity() + " = $" + String.format("%.2f", item.getItemTotal()));
		}
		System.out.println("Subtotal: $" + String.format("%.2f", cart.getSubtotal()));
		System.out.println("Total general: $" + String.format("%.2f", cart.getTotal()));
	}
	
	private static void applyDiscount(Scanner scanner, ShoppingCart cart) {
		System.out.println("\n--- ELIJA SU DESCUENTO ---");
        System.out.println("1. Porcentaje (15% Off)");
        System.out.println("2. Monto fijo ($20 Off)");
        System.out.println("3. Sin descuento (ok ricky ricón)");
        System.out.println("4. Descuento lambda custom (25% Flash Sale)");
        System.out.print("Choice: ");
        
        String choice = scanner.nextLine();
        switch (choice) {
        case "1":
        	cart.setDiscountStrategy(new PercentageDiscount(15));
        	System.out.println("Se aplicó el descuento de 15%.");
        	break;
        case "2":
        	cart.setDiscountStrategy(new FixedDiscount(20));
        	System.out.println("Se aplicó un descuento fijo de $20.");
        case "3":
        	cart.setDiscountStrategy(new NoDiscount());
        	System.out.println("No se aplicó ningún descuento, y se anuló cualquier descuento existente.");
        	break;
        case "4":
        	cart.setDiscountStrategy(total -> total*0.75);
        	System.out.println("Se aplicó el descuento que puse aquí nada más para usar otro lambda!");
        	break;
        default:
        	System.out.println("Selección inválida.");
        }
	}
	
	private static void viewLinkOrTracking(Repository<Product> productRepo) {
		System.out.println("\n--- VER URL DE DESCARGA O TRACKING ---");
		for (Product p : productRepo.getAll()) {
			if (p instanceof DigitalProduct) {
				DigitalProduct digital = (DigitalProduct) p;
				System.out.println("Se encontró el producto digital: " + digital.getName());
				System.out.println("Y se creó su liga de descarga: " + digital.generateDownloadLink());
			} else if (p instanceof PhysicalProduct) {
				PhysicalProduct phys = (PhysicalProduct) p;
				System.out.println("Se encontró el producto físico: " + phys.getName());
				System.out.println("Y el número de rastreo es: " + phys.generateTrackingGuide());
			}
		}
	}
	
	private static void processCheckout(ShoppingCart cart, Customer customer, Repository<SalesReceipt> receiptRepo) {
		System.out.println("\n--- FINALIZACIÓN DE COMPRA ---");
		if (cart.getItems().isEmpty()) {
			System.out.println("El carrito está vacío. No hay compra para finalizar!");
			return;
		}
		
		try {
			int stockLimit = 10;
			for (CartItem item : cart.getItems()) {
				cart.validateStock(stockLimit, item);
			}
			
			double finalTotal = cart.getTotal();
			SalesReceipt receipt = new SalesReceipt(customer, cart.getItems(), finalTotal);
			
			receiptRepo.add(receipt);
			
			receipt.printReceipt();
			
			cart.clear();
			System.out.println("Compra finalizada con éxito! Se vació el carrito.");
		} catch (InsufficientStockException e) {
			System.out.println("ERROR EN FINALIZACIÓN: " + e.getMessage());
		}
	}
	
	private static void viewReceiptHistory(Repository<SalesReceipt> receiptRepo) {
		System.out.println("\n--- HISTORIAL DE RECIBOS ---");
		if (receiptRepo.count() == 0) {
			System.out.println("Todavía no se ha hecho ningún recibo.");
			return;
		}
		
		for (SalesReceipt r : receiptRepo.getAll()) {
			System.out.println("Recibo No. " + r.getReceiptId() + " | Fecha: " + r.getPurchaseDate() + " | Total: $" + String.format("%.2f", r.getTotalPaid()) + " | Cliente: " + r.getCustomer().getName());
        }
	}	
}
