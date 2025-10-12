public class Bulbasaur extends Pokemon {
    public Bulbasaur() {
        super("Bulbasaur", "Planta", 105);

        addAttack(new Attack("Látigo Cepa", 0.9, (att, def) -> 11));
        addAttack(new Attack("Hoja Afilada", 0.85, (att, def) -> {
            int base = 18;
            if (def.getType().equalsIgnoreCase("Agua")) base *= 1.5;
            if (def.getType().equalsIgnoreCase("Fuego")) base *= 0.75;
            return (int)Math.round(base);
        }));
        addAttack(new Attack("Drenadoras", 0.75, (att, def) -> {
            int base = 20;
            if (def.getType().equalsIgnoreCase("Agua")) base *= 1.5;
            if (def.getType().equalsIgnoreCase("Fuego")) base *= 0.75;
            return (int)Math.round(base);
        }));
    }
}
