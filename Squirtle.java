public class Squirtle extends Pokemon {
    public Squirtle() {
        super("Squirtle", "Agua", 110);

        addAttack(new Attack("Placaje", 0.9, (att, def) -> 10));
        addAttack(new Attack("Pistola Agua", 0.88, (att, def) -> {
            int base = 14;
            if (def.getType().equalsIgnoreCase("Fuego")) base *= 1.5;
            if (def.getType().equalsIgnoreCase("Planta")) base *= 0.75;
            return (int)Math.round(base);
        }));
        addAttack(new Attack("Hidrobomba", 0.65, (att, def) -> {
            int base = 28;
            if (def.getType().equalsIgnoreCase("Fuego")) base *= 1.5;
            if (def.getType().equalsIgnoreCase("Planta")) base *= 0.75;
            return (int)Math.round(base);
        }));
    }
}
