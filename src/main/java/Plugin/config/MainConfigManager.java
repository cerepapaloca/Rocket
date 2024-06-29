package Plugin.config;

import Plugin.RocketCore;
import com.sun.security.auth.login.ConfigFile;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.ObjectInputFilter;

public class MainConfigManager {

    private CustomConfig configfile;
    private RocketCore plugin;

    public MainConfigManager(RocketCore plugin) {
        this.plugin = plugin;
        configfile = new CustomConfig("config.yml",null,plugin);
        configfile.registerConfig();
    }
    //otros
    public static Boolean register;

    //secuencia
    public static String prefix;
    public static double forcejump;
    public static int delayexplocion;
    public static int countdown;

    //Catidad de Particulas
    public static int totem;
    public static int explosionhuge;
    public static int explosionlarge;

    //masajes
    public static String messagedeath;
    public static String pexecuteby;
    public static String ptarget;
    public static String punknownplayer;
    public static String pcountdown;
    public static String pcountdowniszero;
    public static String cexecutetarget;
    public static String pseflexecute;
    public static String playercolor;

    //errores
    public static String pnotallow1;
    public static String pnotallow2;
    public static String cdoesnotspecifyargument;
    public static String nofoundtarget;
    public static String pinprogress;

    public CustomConfig customConfig;

    public CustomConfig mainconfigmanager(RocketCore plugin) {
        this.customConfig = new CustomConfig("config.yml", null,plugin);;
        customConfig.registerConfig();
        loadCustomConfig();
        return null;
    }

    public void loadCustomConfig() {
        FileConfiguration config = customConfig.getConfig();
        //otras varibles
        register = config.getBoolean("config.other.register", true);
        forcejump = config.getDouble("config.sequence.forcejump", 3);
        countdown = config.getInt("config.sequence.countdown", 3);
        delayexplocion = config.getInt("config.sequence.delaydeexplocation", 600);
        //Paticulas
        totem = config.getInt("config.particleamount.totem",50);
        explosionhuge = config.getInt("config.particleamount.explosionhuge",2);
        explosionlarge = config.getInt("config.particleamount.explosionlarge",10);
        //mesajes funcionales
        playercolor = config.getString("config.message.playercolor","&b");
        prefix = config.getString("config.message.messageprefix", "&f[&r&4Rocket&f]&r");
        //Los mesajes que tenga c es para consola y los que tenga p es para el jugador y si no tiene es para los dos
        //mesajes
        pexecuteby = config.getString("config.message.pexecuteby", "Iniciado por ");
        ptarget = config.getString("config.message.ptarget","&rhacia el jugador ");
        punknownplayer = config.getString("config.message.punknownplayer","Iniciado por la consola o por un jugador desconocido");
        pcountdown = config.getString("config.message.pcountdown","Despege En ");
        pcountdowniszero = config.getString("config.message.pcountdowniszero","Despege ya");
        cexecutetarget = config.getString("config.message.cexecutetarget","Iniciado a ");
        messagedeath = config.getString("config.message.messagedeath", "&r Ha Sido Aniquilado");
        pseflexecute = config.getString("config.message.pseflexecute", "Auto rocker iniciado");
        //mesajes de error
        pnotallow1 = config.getString("config.message.error.pnotallow1","&4No tienes permiso para usar el /rocket");
        pnotallow2 = config.getString("config.message.error.pnotallow2","&4No esta permitodo usar en otros jugadores");
        cdoesnotspecifyargument = config.getString("config.message.error.cdoesnotspecifyargument","&4Tiene que espesificar nombre del jugador o le haras rocket a la cosola");
        nofoundtarget = config.getString("config.message.error.nofoundtarget","&3No se encontro el jugador");
        pinprogress = config.getString( "config.message.error.pinprogress","&4No se puede ejecutar el comando en proseso, espere a que termine");

    }

    public void reloadCustomConfig() {
        customConfig.reloadConfig();
        loadCustomConfig();
    }
}
