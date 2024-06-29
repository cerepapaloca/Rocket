package Plugin.command;

import Plugin.RocketCore;
import Plugin.config.MainConfigManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.Particle;


import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.Bukkit.*;


public class CommandRocket implements CommandExecutor {
    private  final RocketCore plugin;
    public CommandRocket(RocketCore plugin){
        this.plugin = plugin;
    }
    boolean self;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("Rocket.Self")) {
                if (args.length == 0) {

                    Comprobacion(player, args, true);
                } else if (player.hasPermission("Rocket.Other")) {
                    Comprobacion(player, args, false);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.pnotallow2));//"&4No esta permitodo usar en otros jugadores"
                }
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.pnotallow1));//"&4No tienes permiso para usar el /rocket"
            }
        }else {//En caso se use en consola
            if(args.length == 0) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix+ MainConfigManager.cdoesnotspecifyargument));//"&4Tiene que espesificar nombre del jugador o le haras rocket a la cosola"
            }else {
                Comprobacion(null, args, false);
            }
        }

        return false;
    }
    private int cr;
    private boolean terminado = true;

    public void Comprobacion(Player player, String[] args, Boolean self){
        if (terminado) {
            if (self){
                Player target = player;
                Secuencia (target);
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.pseflexecute));//"Auto rocker iniciado"
                if(MainConfigManager.register) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.pexecuteby + MainConfigManager.playercolor + player.getName() + "&r " + MainConfigManager.ptarget + MainConfigManager.playercolor + target.getName()));//"&rhacia el jugador &b"
                }
            }else{
                String nameplayer = args[0];
                Player target = getServer().getPlayerExact(nameplayer);
                if (target != null) {
                    Secuencia(target);
                    //mesajes de ejecucion con exito
                    if (player != null) {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.pexecuteby + MainConfigManager.playercolor + player.getName()));//"Iniciado por "
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.cexecutetarget + MainConfigManager.playercolor + target.getName()));
                        if(MainConfigManager.register) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.pexecuteby + MainConfigManager.playercolor + player.getName() + "&r " + MainConfigManager.ptarget + MainConfigManager.playercolor + target.getName()));//"&rhacia el jugador &b"
                        }
                    } else {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.punknownplayer));//"Iniciado por la consola o por un jugador desconocido"
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.cexecutetarget + MainConfigManager.playercolor + target.getName()));
                    }

                    //no se encotreo el usuario
                } else if (player != null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.nofoundtarget));//"&3No se encontro el jugador o esta en modo espectador"
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.nofoundtarget));//"&3No se encontro el jugador"
                }
            }
        //no a terminado la secuencia
        }else{
            if (player != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',MainConfigManager.prefix + MainConfigManager.pinprogress));//"&4No se puede ejecutar el comando en proseso, espere a que termine"
            }else{
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',MainConfigManager.prefix + MainConfigManager.pinprogress));//"&4No se puede ejecutar el comando en proseso, espere a que termine"
            }

        }
    }

    /////////////////////////////////
    /////INICIO DE LA SECUENCIA//////
    /////////////////////////////////
    public void Secuencia(Player target) {
        cr = MainConfigManager.countdown;
        terminado = false;

        String nameworld = target.getWorld().getName();
        Timer timer = new Timer();
        Timer altura = new Timer();
        Timer trasado = new Timer();

        //Explocion
        TimerTask taskaltura = new TimerTask() {
            public void run() {
                //Cuenta regresiva
                //En Ejecucion
                Objects.requireNonNull(getWorld(nameworld)).playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 2, 1);
                Objects.requireNonNull(getWorld(nameworld)).spawnParticle(Particle.TOTEM,target.getLocation(),MainConfigManager.totem, 0, 0, 0, 2);
                Objects.requireNonNull(getWorld(nameworld)).spawnParticle(Particle.EXPLOSION_HUGE,target.getLocation(),MainConfigManager.explosionhuge, 2, 2,2, 0);
                Objects.requireNonNull(getWorld(nameworld)).spawnParticle(Particle.EXPLOSION_LARGE,target.getLocation(),MainConfigManager.explosionlarge, 2, 2,2, 0);
                altura.cancel();
                trasado.cancel();
                Bukkit.getScheduler().runTask(plugin, () -> {
                    target.setHealth(0);
                });
                RocketCore.cm = true;
                broadcastMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.playercolor + target.getName() + MainConfigManager.playercolor + MainConfigManager.messagedeath));
                terminado = true;
            }
        };

        //Despege
        TimerTask task = new TimerTask() {
            public void run() {
                //Cuenta regresiva

                if (cr != 0){
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.pcountdown + cr));
                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1, 0.793701F);
                }else{
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + MainConfigManager.pcountdowniszero));
                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1, 1.587401F);
                }
                if (cr <= 0) {
                    //En Ejecucion
                    target.setVelocity(new Vector(0, MainConfigManager.forcejump, 0));
                    Objects.requireNonNull(getWorld(nameworld)).playSound(target.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.PLAYERS, 1, 1);
                    Objects.requireNonNull(getWorld(nameworld)).playSound(target.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                    timer.cancel();
                    altura.scheduleAtFixedRate(taskaltura, MainConfigManager.delayexplocion, 1000); //tiempo de explocin
                }
                --cr;
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000); //tiempo en MS

        TimerTask tasktrasado = new TimerTask() {

            public void run() {
                Objects.requireNonNull(getWorld(nameworld)).spawnParticle(Particle.FIREWORKS_SPARK,target.getLocation(),6, 0.0,0.0,0.0, 0.2, null);

            }
        };
        altura.scheduleAtFixedRate(tasktrasado, 0, 100); //tiempo en MS


    }//FIN DE LA SECUENCIA//
}





