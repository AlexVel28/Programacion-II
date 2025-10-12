import java.util.*;
import java.util.stream.Collectors;

public abstract class Pokemon {
    private final String name;
    private final String type;
    private final int maxHP;
    private int currentHP;
    private final List<Attack> attacks = new ArrayList<>();

    public Pokemon(String name, String type, int maxHP) {
        this.name = name;
        this.type = type;
        this.maxHP = Math.max(1, maxHP);
        this.currentHP = this.maxHP;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentHP() { return currentHP; }

    public boolean isFainted() { return currentHP <= 0; }

    public void receiveDamage(int dmg) {
        currentHP -= dmg;
        if (currentHP < 0) currentHP = 0;
    }

    public void healToFull() {
        currentHP = maxHP;
    }

    public void addAttack(Attack a) { attacks.add(a); }

    public List<Attack> getAttacksSortedByName() {
        return attacks.stream()
                .sorted(Comparator.comparing(Attack::getName))
                .collect(Collectors.toList());
    }

    public List<Attack> getAttacks() {
        return Collections.unmodifiableList(attacks);
    }

    public String toString() {
        return name + " [" + type + "] HP: " + currentHP + "/" + maxHP;
    }

    public Attack chooseAttackForCPU() {
        return attacks.stream()
                .sorted(Comparator.comparingDouble(Attack::getPrecision).reversed()
                        .thenComparing(Attack::getName))
                .findFirst()
                .orElse(attacks.get(0));
    }
}
