package thunderCore.games.bedWarsManager.gamesManager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable
import thunderCore.ThunderCore.get
import thunderCore.games.bedWarsManager.bedWarsGenerator.CenterGenerator
import thunderCore.games.bedWarsManager.bedWarsGenerator.SpawnGenerator
import thunderCore.games.bedWarsManager.bedWarsGenerator.UpgradeGenerator
import thunderCore.games.bedWarsManager.BedWarsManager
import thunderCore.games.bedWarsManager.teamManager.BedWarsTeamForm
import thunderCore.managers.fileManager.FileManager.copyWorld
import java.time.Duration

class BedWarsTeamGameManager(private val gameForm: BedWarsGameForm) : Listener {
    private var gameOver = false
    private var frozen = true
    private val spawns = gameForm.map.teamSpawns
    private val teams: List<BedWarsTeamForm> = gameForm.teams
    private val players = ArrayList<Player>()

    init {
        val map = gameForm.map
        val gameWorld = map.mapTemplate
        gameForm.id = gameWorld.name + (BedWarsManager.activeGames.size + 1)

        //Copy world
        copyWorld(gameWorld, gameForm.id)
        for (team in gameForm.teams) {
            players.addAll(team.teamMembers)
        }


        //Initialize Shops


        //Initialize Generators

        //SpawnGenerators
        val redGen = SpawnGenerator(spawns[0])
        val yellowGen = SpawnGenerator(spawns[1])
        val greenGen = SpawnGenerator(spawns[2])
        val blueGen = SpawnGenerator(spawns[3])
        val spawnGenerators = ArrayList<SpawnGenerator>()
        spawnGenerators.add(redGen)
        spawnGenerators.add(yellowGen)
        spawnGenerators.add(greenGen)
        spawnGenerators.add(blueGen)

        //UpgradeGenerators
        val upgradeGenerators = ArrayList<UpgradeGenerator>()
        upgradeGenerators.add(UpgradeGenerator(map.upgradeGenerators[0]))
        upgradeGenerators.add(UpgradeGenerator(map.upgradeGenerators[1]))
        upgradeGenerators.add(UpgradeGenerator(map.upgradeGenerators[2]))
        upgradeGenerators.add(UpgradeGenerator(map.upgradeGenerators[3]))

        //CenterGenerators
        val centerGenerators = ArrayList<CenterGenerator>()
        centerGenerators.add(CenterGenerator(map.centerGenerators[0]))
        centerGenerators.add(CenterGenerator(map.centerGenerators[1]))
        centerGenerators.add(CenterGenerator(map.centerGenerators[2]))
        centerGenerators.add(CenterGenerator(map.centerGenerators[3]))

        //Spawn Players
        for ((spawn, teamForm) in teams.withIndex()) {
            val player: List<Player> = teamForm.teamMembers
            for (teamMember in player) {
                teamMember.teleport(spawns[spawn])
            }
        }

        //Initialize Scoreboards and other Hud elements (Once I figure that out)


        //Countdown to game start
        val countdown = intArrayOf(5)
        object : BukkitRunnable() {
            override fun run() {
                if (countdown[0] <= 0) {
                    frozen = false
                    cancel()
                }
                for (player in players) {
                    val title = Title.title(
                        Component.text(NamedTextColor.GREEN.toString() + countdown[0].toString()),
                        Component.empty(),
                        Title.Times.times(
                            Duration.ofMillis(100), Duration.ofMillis(600), Duration.ofMillis(100)
                        )
                    )
                    player.playNote(player.location, Instrument.CHIME, Note.natural(1, Note.Tone.A))
                    player.showTitle(title)
                }
                countdown[0]--
            }
        }.runTaskTimer(get(), 0, 20)
        for (spawnGenerator in spawnGenerators) {
            spawnGenerator.startSpawning()
        }
        for (upgradeGenerator in upgradeGenerators) {
            upgradeGenerator.startSpawning()
        }
        for (centerGenerator in centerGenerators) {
            centerGenerator.startSpawning()
        }

        //8 Minute games
        val time = intArrayOf(480)
        object : BukkitRunnable() {
            override fun run() {
                if (gameOver) {
                    cancel()
                }
                if (time[0] <= 0) {
                    endGame()
                    cancel()
                }
                time[0]--
            }
        }.runTaskTimer(get(), 0, 20)
    }

    fun endGame() {
        gameOver = true
        try {
            for (player in players) {
                player.teleport(Location(Bukkit.getWorld("lobby"), 0.5, 72.0, 0.5))
            }
        } catch (_: Exception) {
            //Do nothing since the player already left the server or isn't in the game anymore
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.player
        if (player.world.name != gameForm.id) {
            return
        }
        player.gameMode = GameMode.SPECTATOR
        val ede = player.lastDamageCause!!
        when (ede.cause) {
            DamageCause.VOID -> {
                for (p in players) {
                    p.sendMessage(Component.text(NamedTextColor.RED.toString() + player.name + NamedTextColor.GOLD + " has died to the void!"))
                }
            }

            DamageCause.ENTITY_ATTACK -> {
                if (ede.entity.type == EntityType.PLAYER) {
                    (ede.entity as Player).killer?.let { gameForm.getTeamByPlayer((ede.entity as Player).killer)!!.addPlayerKill(it) }
                }
                for (p in players) {
                    p.sendMessage(Component.text(NamedTextColor.RED.toString() + player.name + NamedTextColor.GOLD + " was killed by " + ede.entity.name + "!"))
                }
            }

            DamageCause.FALL -> {
                for (p in players) {
                    p.sendMessage(Component.text(NamedTextColor.RED.toString() + player.name + NamedTextColor.GOLD + " fell to their death!"))
                }
            }

            DamageCause.FIRE -> {
                for (p in players) {
                    p.sendMessage(Component.text(NamedTextColor.RED.toString() + player.name + NamedTextColor.GOLD + " burned to death!"))
                }
            }

            DamageCause.SUFFOCATION -> {
                for (p in players) {
                    p.sendMessage(Component.text(NamedTextColor.RED.toString() + player.name + NamedTextColor.GOLD + " suffocated to death!"))
                }
            }
            else -> { //gets mad without a default
            }
        }
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent?) {
        //If player joins back have them rejoin
    }

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        if (event.player.world.name == gameForm.id && frozen) {
            event.isCancelled = true
        }
    }
}
