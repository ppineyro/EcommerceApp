# EcommerceApp
App de e-Commerce para la primera semana de Java en una Academy

La idea es que sea una app de e-commerce que se pueda manejar desde la consola, para no complicarme con asuntos visuales
El plan de desarrollo es este:
1. Construir excepciones
2. Construir interfaces
3. Construir la clase base y las subclases
4. Implementación de elementos como has-a e inmutabilidad
5. Patrones de diseño
6. Generics
7. Construir la aplicación principal

Este orden es en afán de evitar que salgan errores al compilar y para no perderme en el proceso de desarrollo.
En este README se detallará el proceso que seguí para llegar al producto final.

***ARQ Y DISEñO***

Antes que nada voy a definir la arquitectura del proyecto y dar una descripción general del diseño.
Como ya mencioné antes, para este proyecto decidí irme simple y crear una aplicación de consola de Java standalone que está diseñada para simular la lógica operacional de una plataforma de e-commerce o comercio en línea. Sentí que esta es una buena manera de incorporar el temario de la semana porque pues hay que implementar cosas como el manejo de un catálogo, estrategias para precios dinámicos, logísticas de envío y sus preciois, carritos de compras, y registros de ventas inmutables.
En el documento adicional, *diagramaproducto.png* que se encuentra en el repositorio, se puede observar el diagrama UML que hice al inicio cuando comencé a diseñar este programa.
De manera más detallada:
En cuanto a la herencia y la abstracción, la clase abstracta Product establece atributos compartidos y define un contrato a través de un método calculateFinalPrice().
PhysicalProduct y DigitalProduct ambos heredan de Product y cuentan con sus propios algoritmos para calcular el precio total.
En cuanto a la composición, los clientes (clase Customer) tienen (has a) una dirección (Address). Además, el carrito de compras (ShoppingCart) contiene una colección de instancias de CartItem y cada una de estas en cambio tiene una referencia a algún Product.
En cuanto a instancias de polimorfismo e interfaces, existe una interfaz Shippable que se implementa solamente por PhysicalProduct para manejar los costos de envío y su variación dependiendo del peso de los productos así como los tracking codes independientemenete de los productos digitales comprados.
además de eso Product implementa Comparable<Product> para poder definir el orden de los productos.

En cuanto a patrones de diseño se me ocurrió usar el Strategy pattern para desasociar los descuentos del carrito de compras en sí y hacer que le delegue la responsabilidad del cálculo total a una interfaz strategy intercambiable (descuento por porcentaje, fijo y sin descuento) y esto hará que se pueda cambiar el algoritmo e ntiempo real en el checkout y esto se me hace una buena implementación de Strategy.
Para el patrón de singleton se puede implementar en la configuración de la aplicación. Podemos crear un StoreConfig que encapsule parámetros globales dentro de una sola instancia accesible durante todo el ciclo de vida de la aplicación
Además puedo usar generics para crear un contenedor de almacenamiento genérico y type-safe  para poder administrar colecciones de entidades de dominio (es decir aquellos objetos que tienen una identidad única y un ciclo de vida continuo y lógica encapsulada).
Y puedo implementar exceptions personalizadas para tratar casos donde la lógica del negocio falle.
Finalmente creo que la inmutabilidad se puede implementar en los registros de ventas para crear recibos/facturas que después de ser creados no se puedan modificar.

***PROCESO***

Primero definí las excepciones porque no dependen de nada más y requieren el menos esfuerzo en desarrollar, aparte es bueno que estén para después.
Luego me fui a hacer las interfaces. Hice dos: Shippable, que es para asegurarse que la mercancía física tenga lógica de envío, y DiscountStrategy, que es donde voy a usar el patrón de strategy después para aplicar descuentos diferentes dinámicamente a los cálculos.
Shippable va en el package com.store.model y DiscountStrategy va en el package com.store.patterns. Explicar por qué está de más pero es porque uno va en la lógica de la tienda y el otro es parte de los patrones que usaré.
La interfaz Shippable la creé porque en el modelo de e-commerce que diseñé vamos a tener productos físicos y digitales, y no tiene sentido poner los métodos de envío directamente en la clase Product que definiré después, porque así los productos digitales heredarían los métodos de shipping y pues no tiene sentido.
Así es que separo las funcionalidades de envío de la lógica principal de la clase Product. Y la interfaz tiene una firma para el tracking guide, y una para el costo de envío.
DiscountStrategy está para evitar tener que programar un chorro de if-elses en la lógica del carrito de compras porque pues no es buena práctica.
Con esto así cuando cree la lógica del carrrito no tiene que ver cómo se calcula, simplemente puede llamar el applyDiscount() y ya.
Además de eso el solamente darle a esta interfaz un método abstracto la vuelve una interfaz funcional, lo cual nos permitirá más adelante crear clases anónimas en Main y usar lambdas directamente al aplicar descuentos.

Después me senté a diseñar la jerarquía de cómo iba a estar todo definido de la clase Product y sus subclases. Lo separé en tres: la clase abstracta padre Product, y las subclases concretas PhysicalProduct y DigitalProduct.
Hice que Product fuera abstracto porque así me hace sentido, tipo, en una tienda real nuncca se vende un "producto" así nomás, ¿no? Siempre es algo. Se vende un libro, una taza, un cepillo, lo que sea.
Entonces al hacer que Product sea una clase abstracta no se tiene que crear un producto mediante new Product(...) y simplemente puedo establecer características que tienen todos los productos en común, y un método abstracto que haga que todas las subclases tengan que tener su propia lógica de calcular precios que llamaré calculateFinalPrice().
También voy a implementar aquí Comparable<Product> para poder ordenar los productos en la tienda, y voy a implementar compareTo(Product other) para que llamar Collections.sort(productList) organize los productos automáticamente en orden de precio ascendiente (ascendente? no recuerdo)
También quiero notar que el uso de protected el basePrice en Product es porque si fuera private, las subclases no podrían accederle sin getters. Si fuera public, cualquier otra clase que no tenga nada que ver también tiene acceso, y pues eso no quiero. Entonces protected es la mejor opción aquí porque así las subclases pueden tener acceso exclusivo a estos atributos.
Finalmente de lo importante en esta parte quiero notar que incluí un contador estático para IDs que aumenta en cantidad cada vez que un constructor de Product es ejecutado y esto debería garantizar que cada producto en la tienda tenga su propio ID automáticamente.
en este paso no hay mucho más que comentar además de que puse en las subclases los constructores, getters y setters, y métodos necesarios para el funcionamiento de cada una. Incluyendo cambios pequeñitos e importantes como el que los productos digitales no cuentan con precio de envío.


El siguiente paso fue crear otras clases de apoyo y de funcionamiento, primero creé Address que es estrictamente para representar las direcciones de los clientes, luego creé Customer y aquí implementé la relación has-a porque un Customer tiene un Address.
Después cree la clase CartItem que es para cada producto que está en el carrito de compras, algo importante de notar aquí es que hice referencia a Product y como Product es abstracto con dos subclases los campos que tienen que ver con Product pueden ser o PhysicalProduct o DigitalProduct
Ya que está CartItem lo que sigue es crear el carrito de compras en sí, la clase ShoppingCart. Yo como desarrollador quiero que sea básicamente un contenedor dinámico para todos los productos que un usuario quiera comprar. Entonces el ShoppingCart tiene (has a) una lista de objetos de CartItem lo cual implementé como List<CartItem>.
Me aseguré de que la lógica de los descuentos estuviera separada del carrito en sí para poder aplicar el diseño strategy como lo había mencionado antes e hice que el cálulo total del precio a pagar funcionase sin que la clase tenga que saber si un producto es físico o digital.
Usé la interfaz List como el tipo de variable para la lista de objetos en el carrito en lugar de implementarla concretamente en caso de que quisiese cambiar el tipo de lista ya después y no tener que redefinir eso, y definí discountStrategy que es un campo que contiene una referencia a cual sea el algoritmo de descuento que esté activo. Creo que lo implementé bien porque al ser una interfaz DiscountStrategy puede tener en él cualquier objeto que lo implemente sin importar que sea un porcentaje o una cantidad fija.
Luego inicializé la lista items como una ArrayList vacía en el constructor para evitar alguna excepción de null pointer al agregar objetos y agregué métodos para agregar y quitar objetos. En addItem pongo el Product que es abstracto y quantity en un nuevo CartItem y agrego eso a la lista. Y en el removeItem uso el método de java removeIf que encontré junto a una expresión lambda que checa cada objeto en el carrito y lo quita si su ID de producto coincide con el ID del producto que se quiere quitar.
Después siguió el cálculo del subtotal en getSubtotal donde se itera a través de cada CartItem y se llama item.getItemTotal(). Este método internamente llama a product.calculateFinalPrice(), y por como lo diseñé incluye shipping si el producto es físico y lo excluye si es digital.
Luego en getTotal se toma el subtotal con getSubtotal, se checa si existe un descuento a aplicar, y si es null no aplica nada. Pero si existe un descuento, le delega ese cálculo final a discountStrategy.applyDiscount(subtotal).
Finalmente lo último que tengo en el carrito es el método validateStock que revisa que la tienda tenga el suficiente inventario en existencia para satisfacer la solicitud del carrito y si no, arroja una excepción InsufficientStockException que definí al inicio del desarrollo.
Luego creé SalesReceipt, que desde el inicio tengo en mente que tiene que ser perfectamente inmutable. Así que la clase es final; ninguna clase la puede extender o hacerle override, los campos también son finales para que sólo puedan ser asignados una vez dentro del constructor, las referencias internas a colecciones no pueden ser modificadas por cosas de afuera y el contador que se usa para el folio/ID de los recibos es estático para compartirse en memoria a través de todas las instancias de recibos. Y está garantizado que los Ids sean únicos.
en SalesReceipt si en this.items yo hubiera puesto this.items = items; en lugar de this.items = new ArrayList<>(items); alguna clase ajena podría todavía tener alguna referencia a esa lista original y agregarle o quitarle objetos después de que el recibo fuera generado. Si está envuelto de esta manera la clase SalesReceipt tiene su propia copia de como estuvo la lista en el momento de impresión.
Similarmente getItems() retorna new ArrayList<>(items) para que en caso de que alguna clase o método llame a ver los objetos en el recibo, les da una copia, para que no puedan limpiar o modificar de otro modo la lista interna que de verdad tiene el recibo.
Finalmente en esta parte tengo el printReceipt que pues solo le da formato al recibo para que se vea bonito.

Ahora toca implementar el StoreConfig en el cual voy a aplicar el patrón singleton y las tres formas de descuento que implementarán DiscountStrategy.
StoreConfig contiene datos que deben ser globales como el nombre de la tienda y cosas como el tipo de moneda y la tasa de impuestos. Para evitar tener varios objetos de configuración pues ponemos todo aquí.
Para volverlo un singleton pues el constructor lo voy a hacer privado, voy a hacer un private static StoreConfig instance; dentro de la clase y pondré un método getInstance() público estático. 
Ya que está eso avanzo a implementar las opciones que delineé en el diseño del patrón Strategy para los tipos diferentes de descuento para los productos, haré tres clases, PercentageDiscount, FixedDiscount y NoDiscount que todas implementarán al método DiscountStrategy.
En el de desc. por porcentaje puse una variable privada double percentage, en el constructor se toma el porcentaje y se valida que sea usable, y en el método se hace el cálculo applyDiscount.
En el de descuento fijo es lo mismo pero con un valor fijo en lugar de un porcentaje, y en el que no aplica descuento pues no se hace nada y se retorna el total igualito.