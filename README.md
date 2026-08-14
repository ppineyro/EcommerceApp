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



