package com.liu.cmd.impl.common;

import com.liu.cmd.Command;
import com.liu.cmd.Commands;
import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.CustomerCommand;
import com.liu.cmd.annotation.EntranceCommand;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.entity.Account;

import java.util.*;

/**
 * @ClassName HelpCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:35
 * @Version 1.0
 **/
@CommandMeta(name = "BZXX", desc = "帮助信息", group = "公共命令")
@AdminCommand
@CustomerCommand
@EntranceCommand
public class HelpCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        if (account == null) {
            entranceHelp();
        }else {
            switch (account.getAccountType()) {
                case CUSTOMER:
                    customerHelp();
                    break;
                case ADMIN:
                    adminHelp();
                    break;
                default:
                    break;
            }
        }
    }

    public void adminHelp() {
        printCommand("管理端", Commands.ADMIN_COMMANDS.values());
    }

    public void customerHelp() {
        printCommand("客户端", Commands.CUSTOMER_COMMANDS.values());
    }

    public void entranceHelp() {
        printCommand("欢迎", Commands.ENTRANCE_COMMANDS.values());
    }

    // 通用的打印命令
    public void printCommand(String title, Collection<Command> collection) {
        printlnInfo("*************" + title + "*************");

        // 将CommandMeta中的group和desc+name组成一个Map
        Map<String, List<String>> helpInfo = new HashMap<>();

        for (Command command : collection) {
            CommandMeta commandMeta = command.getClass().getDeclaredAnnotation(CommandMeta.class);
            String group = commandMeta.group();// 新的map的key值

            // 第一次没有group对应的List时,此时List应该为空
            List<String> func = helpInfo.computeIfAbsent(group, k -> new ArrayList<>());

            func.add(commandMeta.desc() + "(" + commandMeta.name() + ")");
        }

        int i = 0;
        // entrySet():取出键值对的集合  getKey()  getValue()
        for (Map.Entry<String, List<String>> entry : helpInfo.entrySet()) {
            i++;
            printlnInfo(i + "." + entry.getKey());
            int j = 0;
            for (String item : entry.getValue()) {
                j++;
                printlnInfo("\t" + i + "." + j + " " + item);
            }
        }
        printlnInfo("输入菜单括号后的编号（忽略大小写），进行下一操作");
        printlnInfo("********************************************");
    }
}
