package net.aminecraftdev.custombosses;

import lombok.Getter;
import net.aminecraftdev.custombosses.api.BossAPI;
import net.aminecraftdev.custombosses.commands.BossCmd;
import net.aminecraftdev.custombosses.container.BossEntityContainer;
import net.aminecraftdev.custombosses.file.BossesFileHandler;
import net.aminecraftdev.custombosses.file.ConfigFileHandler;
import net.aminecraftdev.custombosses.file.EditorFileHandler;
import net.aminecraftdev.custombosses.file.LangFileHandler;
import net.aminecraftdev.custombosses.managers.*;
import net.aminecraftdev.custombosses.managers.files.BossItemFileManager;
import net.aminecraftdev.custombosses.managers.files.BossesFileManager;
import net.aminecraftdev.custombosses.utils.Debug;
import net.aminecraftdev.custombosses.utils.IReloadable;
import net.aminecraftdev.custombosses.utils.Message;
import net.aminecraftdev.custombosses.utils.ServerUtils;
import net.aminecraftdev.custombosses.utils.command.SubCommandService;
import net.aminecraftdev.custombosses.utils.file.YmlFileHandler;
import net.aminecraftdev.custombosses.utils.version.VersionHandler;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author AMinecraftDev
 * @version 1.0.0
 * @since 06-Sep-17
 */
public class CustomBosses extends JavaPlugin implements IReloadable {

    @Getter private BossEntityContainer bossEntityContainer;
    @Getter private BossMechanicManager bossMechanicManager;
    @Getter private BossLocationManager bossLocationManager;
    @Getter private BossListenerManager bossListenerManager;
    @Getter private BossCommandManager bossCommandManager;
    @Getter private BossItemFileManager itemStackManager;
    @Getter private BossEntityManager bossEntityManager;
    @Getter private BossesFileManager bossesFileManager;
    @Getter private BossPanelManager bossPanelManager;
    @Getter private BossHookManager bossHookManager;
    @Getter private VersionHandler versionHandler;
    @Getter private DebugManager debugManager;

    @Getter private YmlFileHandler langFileHandler, editorFileHandler, configFileHandler;
    @Getter private FileConfiguration lang, editor, config;

    @Getter private boolean debug = false;

    @Override
    public void onEnable() {
        long beginMs = System.currentTimeMillis();

        Debug.setPlugin(this);
        new BossAPI(this);
        new Metrics(this);
        new ServerUtils(this);

        this.debugManager = new DebugManager();
        this.versionHandler = new VersionHandler();
        this.bossEntityContainer = new BossEntityContainer();
        this.bossMechanicManager = new BossMechanicManager(this);
        this.bossEntityManager = new BossEntityManager(this);
        this.bossHookManager = new BossHookManager(this);
        this.bossLocationManager = new BossLocationManager(this);

        loadFileManagersAndHandlers();

        this.bossPanelManager = new BossPanelManager(this);

        createFiles();
        reloadFiles();

        this.itemStackManager.reload();
        this.bossesFileManager.reload();
        this.bossCommandManager = new BossCommandManager(new BossCmd(), this);
        this.bossListenerManager = new BossListenerManager(this);
        this.bossPanelManager.load();

        //RELOAD/LOAD ALL MANAGERS
        this.bossHookManager.reload();
        this.bossLocationManager.reload();
        this.itemStackManager.reload();
        this.bossesFileManager.reload();
        this.bossMechanicManager.load();

        saveMessagesToFile();

        this.bossCommandManager.load();
        this.bossListenerManager.load();

        ServerUtils.get().logDebug("Loaded all fields and managers, saved messages and plugin is initialized and ready to go. (took " + (System.currentTimeMillis() - beginMs) + "ms).");
    }

    @Override
    public void reload() {
        this.itemStackManager.reload();
        this.bossesFileManager.reload();
        this.bossMechanicManager.load();

        reloadFiles();

        this.bossPanelManager.reload();
        this.bossHookManager.reload();
        this.bossLocationManager.reload();
        this.debug = getConfig().getBoolean("Settings.debug", false);

        Message.setFile(getLang());
    }

    private void loadFileManagersAndHandlers() {
        this.itemStackManager = new BossItemFileManager(this);
        this.bossesFileManager = new BossesFileManager(this);

        this.langFileHandler = new LangFileHandler(this);
        this.editorFileHandler = new EditorFileHandler(this);
        this.configFileHandler = new ConfigFileHandler(this);
    }

    private void reloadFiles() {
        this.lang = this.langFileHandler.loadFile();
        this.editor = this.editorFileHandler.loadFile();
        this.config = this.configFileHandler.loadFile();
    }

    private void createFiles() {
        this.editorFileHandler.createFile();
        this.langFileHandler.createFile();
        this.configFileHandler.createFile();
    }

    private void saveMessagesToFile() {
        FileConfiguration lang = getLang();

        for(Message message : Message.values()) {
            if(!lang.contains(message.getPath())) {
                lang.set(message.getPath(), message.getDefault());
            }
        }

        this.langFileHandler.saveFile(lang);
        Message.setFile(lang);
    }
}
