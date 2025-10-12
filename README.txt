Mini juego de Pokémon

Este proyecto es un mini-juego por turnos inspirado en Pokémon, desarrollado en Java como parte del Examen Parcial II de Programación II (Universidad Mariano Gálvez de Guatemala). El objetivo es aplicar de forma integrada los siguientes conceptos:

- Programación Orientada a Objetos (abstracción, herencia y polimorfismo)  
- Manejo de excepciones personalizadas  
- Uso de colecciones (`List`, `Map`)  
- Procesamiento con Streams y Lambda

Características del programa:

- Menú interactivo por consola con validación de entrada.
- Cuatro Pokémon iniciales con ataques únicos y reglas de ventaja por tipo.
- Ataques con nombre, precisión y regla de daño (usando lambdas).
- Manejo de excepciones:
  - `InvalidChoiceException`: opción inválida del usuario.
  - `AttackMissedException`: cuando un ataque falla por precisión.
- Registro de batalla en listas (`battleLog`, `damageHistory`).
- Estadísticas finales usando **Java Streams**:
  - Total de fallos.
  - Top 3 golpes más fuertes.
  - Promedio de daño.
  - Conteo de eventos por actor (Jugador / CPU).
- Uso de **polimorfismo** para definir comportamientos de ataque según el tipo de Pokémon.
- Implementación limpia, clara y extensible.
