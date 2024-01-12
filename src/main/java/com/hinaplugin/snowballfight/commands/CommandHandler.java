package com.hinaplugin.snowballfight.commands;

import com.google.common.collect.Lists;
import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.commands.subcommands.JoinAllCommand;
import com.hinaplugin.snowballfight.commands.subcommands.JoinCommand;
import com.hinaplugin.snowballfight.commands.subcommands.JoinOtherCommand;
import com.hinaplugin.snowballfight.commands.subcommands.LeaveCommand;
import com.hinaplugin.snowballfight.commands.subcommands.SetCommand;
import com.hinaplugin.snowballfight.commands.subcommands.StartCommand;
import com.hinaplugin.snowballfight.commands.subcommands.StopCommand;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandHandler implements CommandExecutor, TabCompleter {
    public CommandHandler() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            switch (args[0]) {
                case "join":
                    if (!(new JoinCommand()).Join(sender)) {
                        sender.sendMessage(ChatColor.RED + "コマンド実行中にエラーが発生しました．");
                    }
                    break;
                case "leave":
                    if (!(new LeaveCommand()).Leave(sender)) {
                        sender.sendMessage(ChatColor.RED + "コマンド実行中にエラーが発生しました．");
                    }
                    break;
                case "start":
                    if (!(new StartCommand()).Start(sender)) {
                        sender.sendMessage(ChatColor.RED + "コマンド実行中にエラーが発生しました．");
                    }
                    break;
                case "joinall":
                    if (!(new JoinAllCommand()).JoinAll(sender)) {
                        sender.sendMessage(ChatColor.RED + "コマンド実行中にエラーが発生しました．");
                    }
                    break;
                case "joinother":
                    if (args.length == 2) {
                        if (!(new JoinOtherCommand()).onJoinOther(sender, args[1])) {
                            sender.sendMessage(ChatColor.RED + "コマンド実行中にエラーが発生しました．");
                        }
                    } else {
                        sender.sendMessage("usage: /sbf joinother <PlayerName>");
                    }
                    break;
                case "stop":
                    if (!(new StopCommand()).Stop(sender)) {
                        sender.sendMessage(ChatColor.RED + "コマンド実行中にエラーが発生しました．");
                    }
                    break;
                case "set":
                    if (args.length >= 3) {
                        if ((new SetCommand()).Set(sender, args[1], args[2])) {
                            sender.sendMessage(ChatColor.RED + "コマンド実行中にエラーが発生しました．");
                        }
                    } else if (args.length == 2) {
                        if ((new SetCommand()).Set(sender, args[1], null)) {
                            sender.sendMessage(ChatColor.RED + "コマンド実行中にエラーが発生しました．");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "usage: /sbf set <parameter> [value]");
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "コマンドを認識できませんでした．");
            }

            return true;
        } else {
            return false;
        }
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> complete = Lists.newArrayList();
        if (command.getName().equalsIgnoreCase("snowballfight")) {
            if (args.length == 1) {
                if (args[0].isEmpty()) {
                    if (sender.hasPermission("snowballfight.commands.join")) {
                        complete.add("join");
                    }

                    if (sender.hasPermission("snowballfight.commands.leave")) {
                        complete.add("leave");
                    }

                    if (sender.hasPermission("snowballfight.commands.joinall")) {
                        complete.add("joinall");
                    }

                    if (sender.hasPermission("snowballfight.commands.joinother")) {
                        complete.add("joinother");
                    }

                    if (sender.hasPermission("snowballfight.commands.start")) {
                        complete.add("start");
                    }

                    if (sender.hasPermission("snowballfight.commands.stop")) {
                        complete.add("stop");
                    }

                    if (sender.hasPermission("snowballfight.commands.set")) {
                        complete.add("set");
                    }
                } else if (args[0].startsWith("j")) {
                    if (sender.hasPermission("snowballfight.commands.join")) {
                        complete.add("join");
                    }

                    if (sender.hasPermission("snowballfight.commands.joinall")) {
                        complete.add("joinall");
                    }

                    if (sender.hasPermission("snowballfight.commands.joinother")) {
                        complete.add("joinother");
                    }
                } else if (args[0].startsWith("s")) {
                    if (sender.hasPermission("snowballfight.commands.start")) {
                        complete.add("start");
                    }

                    if (sender.hasPermission("snowballfight.commands.stop")) {
                        complete.add("stop");
                    }

                    if (sender.hasPermission("snowballfight.commands.set")) {
                        complete.add("set");
                    }
                } else if (args[0].startsWith("l") && sender.hasPermission("snowballfight.commands.leave")) {
                    complete.add("leave");
                }

                return complete;
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("joinother")) {

                    for (final Player player : SnowBallFight.getPlugin().getServer().getOnlinePlayers()) {
                        complete.add(player.getName());
                    }

                    return complete;
                }

                if (args[0].equalsIgnoreCase("set")) {
                    if (sender.hasPermission("snowballfight.commands.set")) {
                        if (args[1].isEmpty()) {
                            complete.add("life");
                            complete.add("redspawn");
                            complete.add("bluespawn");
                            complete.add("endspawn");
                            complete.add("time");
                            complete.add("startball");
                            complete.add("addball");
                            complete.add("addtime");
                            complete.add("outplayer");
                        } else if (args[1].startsWith("a")) {
                            complete.add("addball");
                            complete.add("addtime");
                        } else if (args[1].startsWith("b")) {
                            complete.add("bluespawn");
                        } else if (args[1].startsWith("e")) {
                            complete.add("endspawn");
                        } else if (args[1].startsWith("l")) {
                            complete.add("life");
                        } else if (args[1].startsWith("o")) {
                            complete.add("outplayer");
                        } else if (args[1].startsWith("r")) {
                            complete.add("redspawn");
                        } else if (args[1].startsWith("s")) {
                            complete.add("startball");
                        } else if (args[1].startsWith("t")) {
                            complete.add("time");
                        }
                    }

                    return complete;
                }
            } else if (args.length == 3 && sender.hasPermission("snowballfight.commands.set") && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("outplayer")) {
                if (args[2].isEmpty()) {
                    complete.add("true");
                    complete.add("false");
                } else if (args[2].startsWith("t")) {
                    complete.add("true");
                } else if (args[2].startsWith("f")) {
                    complete.add("false");
                }

                return complete;
            }
        }

        return null;
    }
}
