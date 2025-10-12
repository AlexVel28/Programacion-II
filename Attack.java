import java.util.Random;

public class Attack {
    private final String name;
    private final double precision; // 0.0 - 1.0
    private final Damage rule;

    private static final Random rnd = new Random();

    public Attack(String name, double precision, Damage rule) {
        this.name = name;
        this.precision = Math.max(0.0, Math.min(1.0, precision));
        this.rule = rule;
    }

    public String getName() {
        return name;
    }

    public double getPrecision() {
        return precision;
    }

    public int execute(Pokemon attacker, Pokemon defender) throws AttackMissedException {
        double roll = rnd.nextDouble();
        if (roll > precision) {
            throw new AttackMissedException(attacker.getName() + " intentó " + name + " pero falló (roll=" + String.format("%.3f", roll) + ", prec=" + precision + ")");
        }
        int damage = rule.apply(attacker, defender);
        if (damage < 0) damage = 0;
        return damage;
    }

    public String toString() {
        return name + " (prec: " + String.format("%.2f", precision) + ")";
    }
}
