public class Charmander extends Pokemon {
    public Charmander() {
        super("Charmander", "Fuego", 100);

        // Ataque básico: Ascuas
        addAttack(new Attack("Ascuas", 0.9, (att, def) -> {
            int base = 12;
            // ventaja: fuego > planta
            if (def.getType().equalsIgnoreCase("Planta")) base *= 1.5;
            if (def.getType().equalsIgnoreCase("Agua")) base *= 0.75;
            return (int)Math.round(base);
        }));

        // Ataque único: Llamarada
        addAttack(new Attack("Llamarada", 0.7, (att, def) -> {
            int base = 25;
            if (def.getType().equalsIgnoreCase("Planta")) base *= 1.5;
            if (def.getType().equalsIgnoreCase("Agua")) base *= 0.75;
            return (int)Math.round(base);
        }));

        // Ataque de precisión pero bajo daño
        addAttack(new Attack("Picotazo", 0.95, (att, def) -> 8));
    }
}
