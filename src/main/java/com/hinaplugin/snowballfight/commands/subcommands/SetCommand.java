package com.hinaplugin.snowballfight.commands.subcommands;

import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.TimeChanger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetCommand {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();

    public SetCommand() {
    }

    public boolean Set(@NotNull CommandSender sender, @NotNull String parameter, @Nullable String value) {
        try {
            if (!sender.hasPermission("snowballfight.commands.set")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission perform this commands!");
            } else {
                Location location;
                Player player;
                switch (parameter) {
                    case "life":
                        if (value == null) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set life <life>");
                            return false;
                        }

                        if (this.isNumeric(value)) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set life <life>");
                            return false;
                        }

                        this.plugin.getConfig().set("life", this.changeNumber(value));
                        this.plugin.saveConfig();
                        this.plugin.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "残機を" + value + "に設定しました．");
                        break;
                    case "redspawn":
                        if (sender instanceof Player) {
                            player = (Player)sender;
                            location = player.getLocation();
                            this.SetStringParameter("red-location.world", location.getWorld().getName());
                            this.SetDoubleParameter("red-location.x", Math.floor(location.getX()) + 0.5);
                            this.SetDoubleParameter("red-location.y", Math.floor(location.getY()));
                            this.SetDoubleParameter("red-location.z", Math.floor(location.getZ()) + 0.5);
                            this.SetIntegerParameter("red-location.facing", this.changeFacing(player.getFacing()));
                            player.sendMessage(ChatColor.AQUA + "赤チームのスポーン地点を，\nworld: " + location.getWorld().getName() + ", x: " + (Math.floor(location.getX()) + 0.5) + ", y: " + Math.floor(location.getY()) + ", z: " + (Math.floor(location.getZ()) + 0.5) + "に設定しました．");
                        } else {
                            sender.sendMessage("このコマンドはプレイヤーのみ実行できます．");
                        }
                        break;
                    case "bluespawn":
                        if (sender instanceof Player) {
                            player = (Player)sender;
                            location = player.getLocation();
                            this.SetStringParameter("blue-location.world", location.getWorld().getName());
                            this.SetDoubleParameter("blue-location.x", Math.floor(location.getX()) + 0.5);
                            this.SetDoubleParameter("blue-location.y", Math.floor(location.getY()));
                            this.SetDoubleParameter("blue-location.z", Math.floor(location.getZ()) + 0.5);
                            this.SetIntegerParameter("blue-location.facing", this.changeFacing(player.getFacing()));
                            player.sendMessage(ChatColor.AQUA + "青チームのスポーン地点を，\nworld: " + location.getWorld().getName() + ", x: " + (Math.floor(location.getX()) + 0.5) + ", y: " + Math.floor(location.getY()) + ", z: " + (Math.floor(location.getZ()) + 0.5) + "に設定しました．");
                        } else {
                            sender.sendMessage("このコマンドはプレイヤーのみ実行できます．");
                        }
                        break;
                    case "endspawn":
                        if (sender instanceof Player) {
                            player = (Player)sender;
                            location = player.getLocation();
                            this.SetStringParameter("end-location.world", location.getWorld().getName());
                            this.SetDoubleParameter("end-location.x", Math.floor(location.getX()) + 0.5);
                            this.SetDoubleParameter("end-location.y", Math.floor(location.getY()));
                            this.SetDoubleParameter("end-location.z", Math.floor(location.getZ()) + 0.5);
                            this.SetIntegerParameter("end-location.facing", this.changeFacing(player.getFacing()));
                            player.sendMessage(ChatColor.AQUA + "試合終了時のスポーン地点を，\nworld: " + location.getWorld().getName() + ", x: " + (Math.floor(location.getX()) + 0.5) + ", y: " + Math.floor(location.getY()) + ", z: " + (Math.floor(location.getZ()) + 0.5) + "に設定しました．");
                        } else {
                            sender.sendMessage("このコマンドはプレイヤーのみ実行できます．");
                        }
                        break;
                    case "time":
                        if (value == null) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set time <time>");
                            return false;
                        }

                        if (this.isNumeric(value)) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set time <time>");
                            return false;
                        }

                        this.plugin.getConfig().set("time", this.changeNumber(value));
                        this.plugin.saveConfig();
                        this.plugin.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "試合時間を" + (new TimeChanger(this.changeNumber(value))).Change() + "に設定しました．");
                        break;
                    case "startball":
                        if (value == null) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set startball <amount>");
                            return false;
                        }

                        if (this.isNumeric(value)) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set startball <amount>");
                            return false;
                        }

                        this.plugin.getConfig().set("start-ball", this.changeNumber(value));
                        this.plugin.saveConfig();
                        this.plugin.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "初期配布を" + value + "個に設定しました．");
                        break;
                    case "addball":
                        if (value == null) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set addball <amount>");
                            return false;
                        }

                        if (this.isNumeric(value)) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set addball <amount>");
                            return false;
                        }

                        this.plugin.getConfig().set("add-ball", this.changeNumber(value));
                        this.plugin.saveConfig();
                        this.plugin.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "追加配布を" + value + "個に設定しました．");
                        break;
                    case "addtime":
                        if (value == null) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set addtime <time>");
                            return false;
                        }

                        if (this.isNumeric(value)) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set addtime <time>");
                            return false;
                        }

                        this.plugin.getConfig().set("add-time", this.changeNumber(value));
                        this.plugin.saveConfig();
                        this.plugin.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "追加配布までの時間を" + (new TimeChanger(this.changeNumber(value))).Change() + "に設定しました．");
                        break;
                    case "outplayer":
                        if (value == null) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set outplayer <true|false>");
                            return false;
                        }

                        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                            sender.sendMessage(ChatColor.RED + "usage: /sbf set outplayer <true|false>");
                        } else {
                            this.plugin.getConfig().set("out-player", this.changeBoolean(value));
                            this.plugin.saveConfig();
                            this.plugin.reloadConfig();
                            String bool = value.equalsIgnoreCase("true") ? "有効" : "無効";
                            sender.sendMessage(ChatColor.GREEN + "参加者以外のスタートコマンドを" + bool + "に設定しました．");
                        }
                }

            }
            return false;
        } catch (final Exception exception) {
            new LogTrace(exception);
            return true;
        }
    }

    private boolean isNumeric(@NotNull String number) {
        return !number.chars().allMatch(Character::isDigit);
    }

    private int changeNumber(@NotNull String number) {
        return Integer.parseInt(number);
    }

    private boolean changeBoolean(@NotNull String bool) {
        return Boolean.parseBoolean(bool);
    }

    private void SetStringParameter(@NotNull String key, @NotNull String value) {
        this.plugin.getConfig().set(key, value);
        this.plugin.saveConfig();
        this.plugin.reloadConfig();
    }

    private void SetDoubleParameter(@NotNull String key, @NotNull Double value) {
        this.plugin.getConfig().set(key, value);
        this.plugin.saveConfig();
        this.plugin.reloadConfig();
    }

    private void SetIntegerParameter(@NotNull String key, @NotNull Integer value) {
        this.plugin.getConfig().set(key, value);
        this.plugin.saveConfig();
        this.plugin.reloadConfig();
    }

    private int changeFacing(@NotNull BlockFace blockFace) {
        switch (blockFace) {
            case WEST:
                return 90;
            case NORTH:
                return 180;
            case EAST:
                return 270;
            case SOUTH:
            default:
                return 0;
        }
    }
}
