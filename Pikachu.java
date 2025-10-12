public class Pikachu extends Pokemon {
    public Pikachu() {
        super("Pikachu", "Electrico", 95);

        addAttack(new Attack("Impactrueno", 0.9, (att, def) -> {
            int base = 14;
            if (def.getType().equalsIgnoreCase("Agua")) base *= 1.2;
            return (int)Math.round(base);
        }));
        addAttack(new Attack("Rayo", 0.75, (att, def) -> {
            int base = 24;
            if (def.getType().equalsIgnoreCase("Agua")) base *= 1.2;
            return (int)Math.round(base);
        }));
        addAttack(new Attack("Cola Férrea", 0.85, (att, def) -> 10));
    }
}
