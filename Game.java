import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private final Scanner sc = new Scanner(System.in);
    private final Random rnd = new Random();

    private final Map<Integer, Pokemon> pokedex = new LinkedHashMap<>();
    private final List<String> battleLog = new ArrayList<>();
    private final List<Integer> damageHistory = new ArrayList<>();
    private int missCount = 0;

    public Game() {
        pokedex.put(1, new Charmander());
        pokedex.put(2, new Squirtle());
        pokedex.put(3, new Bulbasaur());
        pokedex.put(4, new Pikachu());
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.start();
    }

    public void start() {
        System.out.println("=== Mini-Pokémon (Consola) ===");
        System.out.print("Ingrese su nombre y apellido: ");
        String playerName = sc.nextLine().trim();
        if (playerName.isEmpty()) playerName = "Jugador";

        Pokemon player = null;
        Pokemon cpu = null;

        while (player == null) {
            try {
                player = choosePokemonFromUser();
            } catch (InvalidChoiceException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        cpu = chooseRandomDifferentPokemon(player);
        System.out.println("CPU ha elegido: " + cpu.getName());

        player.healToFull();
        cpu.healToFull();
        battleLog.add("Inicio de batalla: " + player.getName() + " vs " + cpu.getName() + " (Jugador: " + player.getName() + ")");

        boolean playersTurn = true;
        while (!player.isFainted() && !cpu.isFainted()) {
            if (playersTurn) {
                System.out.println("\nTu turno. HP -> " + player.getName() + ": " + player.getCurrentHP() + "  |  " + cpu.getName() + ": " + cpu.getCurrentHP());
                try {
                    Attack chosen = chooseAttackFromUser(player);
                    try {
                        int dmg = chosen.execute(player, cpu);
                        cpu.receiveDamage(dmg);
                        damageHistory.add(dmg);
                        String ev = player.getName() + " usó " + chosen.getName() + " y causó " + dmg + " de daño. (" + cpu.getName() + " HP=" + cpu.getCurrentHP() + ")";
                        battleLog.add(ev);
                        System.out.println(ev);
                    } catch (AttackMissedException ame) {
                        missCount++;
                        String ev = "Jugador: " + ame.getMessage();
                        battleLog.add(ev);
                        System.out.println(ev);
                    }
                } catch (InvalidChoiceException ice) {
                    System.out.println("Entrada inválida: " + ice.getMessage());
                    continue;
                }
            } else {
                System.out.println("\nTurno CPU. HP -> " + player.getName() + ": " + player.getCurrentHP() + "  |  " + cpu.getName() + ": " + cpu.getCurrentHP());
                Attack cpuAttack = cpu.chooseAttackForCPU();
                try {
                    int dmg = cpuAttack.execute(cpu, player);
                    player.receiveDamage(dmg);
                    damageHistory.add(dmg);
                    String ev = "CPU (" + cpu.getName() + ") usó " + cpuAttack.getName() + " y causó " + dmg + " de daño. (" + player.getName() + " HP=" + player.getCurrentHP() + ")";
                    battleLog.add(ev);
                    System.out.println(ev);
                } catch (AttackMissedException ame) {
                    missCount++;
                    String ev = "CPU: " + ame.getMessage();
                    battleLog.add(ev);
                    System.out.println(ev);
                }
            }

            System.out.println("Estado -> " + player.getName() + ": " + player.getCurrentHP() + " / " + player.getMaxHP()
                    + " | " + cpu.getName() + ": " + cpu.getCurrentHP() + " / " + cpu.getMaxHP());

            playersTurn = !playersTurn;
        }

        System.out.println("\n=== BATALLA FINALIZADA ===");
        String result;
        if (player.isFainted() && cpu.isFainted()) {
            result = "Empate: ambos se debilitaron.";
        } else if (cpu.isFainted()) {
            result = "¡Felicidades " + playerName + "! Ganaste.";
        } else {
            result = "Perdiste. Ganó la CPU (" + cpu.getName() + ").";
        }
        battleLog.add("Resultado: " + result);
        System.out.println(result);

        showSummary(playerName);
    }

    private Pokemon chooseRandomDifferentPokemon(Pokemon chosen) {
        List<Pokemon> others = pokedex.values().stream()
                .filter(p -> !p.getName().equals(chosen.getName()))
                .collect(Collectors.toList());
        Pokemon cpu = others.get(rnd.nextInt(others.size()));
        return cpu;
    }

    private Pokemon choosePokemonFromUser() throws InvalidChoiceException {
        System.out.println("Seleccione su Pokémon:");
        pokedex.forEach((k, v) -> System.out.println(k + ". " + v.getName() + " [" + v.getType() + "] (HP " + v.getMaxHP() + ")"));
        System.out.print("Ingrese número: ");
        String input = sc.nextLine().trim();
        try {
            int choice = Integer.parseInt(input);
            if (!pokedex.containsKey(choice)) throw new InvalidChoiceException("Opción fuera de rango.");
            Pokemon p = pokedex.get(choice);
            System.out.println("Has elegido: " + p.getName());
            return p;
        } catch (NumberFormatException nfe) {
            throw new InvalidChoiceException("No es un número válido.");
        }
    }

    private Attack chooseAttackFromUser(Pokemon player) throws InvalidChoiceException {
        List<Attack> attacks = player.getAttacksSortedByName();
        System.out.println("Elige un ataque:");
        for (int i = 0; i < attacks.size(); i++) {
            Attack a = attacks.get(i);
            System.out.println((i+1) + ". " + a.toString());
        }
        System.out.print("Ingrese número de ataque: ");
        String s = sc.nextLine().trim();
        try {
            int idx = Integer.parseInt(s) - 1;
            if (idx < 0 || idx >= attacks.size()) throw new InvalidChoiceException("Índice de ataque inválido.");
            return attacks.get(idx);
        } catch (NumberFormatException nfe) {
            throw new InvalidChoiceException("Entrada no numérica para ataque.");
        }
    }

    private void showSummary(String playerName) {
        System.out.println("\n--- Resumen de batalla ---");

        System.out.println("\nEventos (últimos):");
        battleLog.stream().skip(Math.max(0, battleLog.size() - 10)).forEach(System.out::println);


        System.out.println("\nEstadísticas:");
        System.out.println("Total de ataques fallidos: " + missCount);

        List<Integer> top3 = damageHistory.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Top 3 golpes (daño): " + top3);

        OptionalDouble avg = damageHistory.stream().mapToInt(Integer::intValue).average();
        System.out.println("Promedio de daño por golpe (considerando golpes exitosos): " + (avg.isPresent() ? String.format("%.2f", avg.getAsDouble()) : "N/A"));

        Map<String, Long> conteo = battleLog.stream()
                .collect(Collectors.groupingBy(ev -> {
                    if (ev.startsWith("Jugador") || ev.contains(playerName) || ev.startsWith(playerName) || ev.startsWith(playerName + ":")) return "Jugador";
                    if (ev.startsWith("CPU") || ev.startsWith("CPU (")) return "CPU";
                    return "Otros";
                }, Collectors.counting()));

        System.out.println("Conteo de eventos por actor: " + conteo);

        System.out.println("\nLog completo:");
        battleLog.forEach(System.out::println);

        System.out.println("\nGracias por jugar. ¡Fin!");
    }
}
