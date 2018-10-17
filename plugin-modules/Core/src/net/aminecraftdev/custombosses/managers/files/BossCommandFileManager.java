package net.aminecraftdev.custombosses.managers.files;

import lombok.Getter;
import net.aminecraftdev.custombosses.CustomBosses;
import net.aminecraftdev.custombosses.file.CommandsFileHandler;
import net.aminecraftdev.custombosses.utils.ILoadable;
import net.aminecraftdev.custombosses.utils.IReloadable;
import net.aminecraftdev.custombosses.utils.ISavable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 17-Oct-18
 */
public class BossCommandFileManager implements ILoadable, ISavable, IReloadable {

    private Map<String, List<String>> commandsMap = new HashMap<>();
    private CommandsFileHandler commandsFileHandler;

    public BossCommandFileManager(CustomBosses customBosses) {
        File file = new File(customBosses.getDataFolder(), "commands.json");

        this.commandsFileHandler = new CommandsFileHandler(customBosses, true, file);
    }

    @Override
    public void load() {
        this.commandsMap = this.commandsFileHandler.loadFile();
    }

    @Override
    public void reload() {
        load();
    }

    @Override
    public void save() {
        this.commandsFileHandler.saveFile(this.commandsMap);
    }

    public List<String> getCommands(String id) {
        return this.commandsMap.getOrDefault(id, null);
    }

    public Map<String, List<String>> getCommandsMap() {
        return new HashMap<>(this.commandsMap);
    }

    public boolean addNewCommand(String id, List<String> commands) {
        if(this.commandsMap.containsKey(id)) return false;

        commandsMap.put(id, commands);
        return true;
    }

    public void removeCommand(String id) {
        commandsMap.remove(id);
    }
}
