package fr.clem28l.HomePlugin.Cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class HomeCmd implements CommandExecutor {
    private static Location location;
    private static HashMap<String, Location> homes = new HashMap<>();

    private static HashMap<Player, Location> oldLocation = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (msg.equalsIgnoreCase("sethome")) {

                location = player.getLocation();
                player.sendMessage("tu as set un home");

                homes.put(args[0], location);

            } else if (msg.equalsIgnoreCase("home")) {
                String homeName = args[0];
                location = player.getLocation();
                if (!homes.containsKey(homeName)) {
                    sender.sendMessage("§aCe home n'existe pas.");
                }else {
                    Location homeLocation = homes.get(homeName);
                    player.teleport(homeLocation);
                    sender.sendMessage("Vous avez été téléporté au home §e" + homeName + " ! ");
                }
                if (!oldLocation.containsKey(player)) {
                    oldLocation.put(player,location);
                }else {
                    oldLocation.replace(player,location);
                }

            } else if (msg.equalsIgnoreCase("delhome")) {
                String homeName = args[0];
                homes.remove(homeName);
                sender.sendMessage("Le home§e" + homeName + " a été supprimer !");
            } else if (msg.equalsIgnoreCase("back")) {
                player.teleport(oldLocation.get(player));
            }

            // Sauvegarde des homes dans le fichier YAML à chaque modification
            HomeCmd.saveHomes();
        }
        return false;
    }

public static void saveHomes() {
    try {
        File file = new File("homes.yml");
        FileWriter writer = new FileWriter(file);

        Yaml yaml = new Yaml(new Constructor(Map.class));
        String output = yaml.dump(homes);
        writer.write(output);

        writer.flush();
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void loadHomes() {
    try {
        File file = new File("homes.yml");
        if (!file.exists()) {
            file.createNewFile();
            return;
        }

        Yaml yaml = new Yaml(new Constructor(Map.class));
        FileInputStream fis = new FileInputStream(file);
        Map<String, Object> map = yaml.load(fis);

        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value instanceof Location) {
                homes.put(key, (Location) value);
            }
        }
        fis.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
