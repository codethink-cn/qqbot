/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `CoreInteractors.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `CoreInteractors.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.interactors;

import cn.qfys521.bot.annotation.Author;
import cn.qfys521.bot.annotation.Command;
import cn.qfys521.bot.annotation.Usage;
import cn.qfys521.bot.command.RegisterCommand;
import cn.qfys521.bot.event.MessageEventKt;
import cn.qfys521.bot.interactors.utils.Base64Util;
import cn.qfys521.bot.interactors.utils.MD5Util;
import cn.qfys521.bot.interactors.utils.URLCodeUtil;
import cn.qfys521.bot.interactors.utils.UnicodeUtil;
import io.github.kloping.qqbot.api.message.MessageEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@SuppressWarnings("unused")
@Author("qfys521")
public class CoreInteractors {
    @Command({"/help", "/帮助", "/菜单"})
    @Usage("/help")
    public void helpMenu(MessageEvent<?, ?> messageEvent) {
        ArrayList<Method> method = RegisterCommand.methodArrayList;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("指令菜单\n");
        ArrayList<String> arrayList = new ArrayList<>();
        for (Method methods : method) {
            Command command = methods.getAnnotation(Command.class);
            if (command != null) {
                if (command.inCommandList()) {
                    arrayList.addAll(Arrays.asList(methods.getAnnotation(Command.class).value()));
                }
            }
        }
        Collections.sort(arrayList);
        for (String s : arrayList) {
            stringBuilder.append(s).append("\n");
        }
        messageEvent.send(stringBuilder.toString());
    }

    @Command({"/echo", "/复述", "/say", "/说"})
    @Usage({"/echo <Message>", "/say <Message>", "/说 <消息>", "/复述 <消息>"})
    public void echo(MessageEvent<?, ?> event) {
        String oriMessage = MessageEventKt.getOriginalContent(event).split(" ")[2];
        event.send(Objects.requireNonNullElse(oriMessage, "用法:/echo <内容>"));
    }

    @Command({"/关于", "/about"})
    @Usage({"/关于", "/about"})
    public void about(MessageEvent<?, ?> messageEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        String a = """
                -={千枫Bot}=-
                为您带来一些Simple小功能
                ======作者======
                框架作者:qfys521
                java-sdk作者:kloping
                ===============
                """.trim();
        stringBuilder.append(a);
        ArrayList<Class<?>> classArrayList = RegisterCommand.classArrayList;
        for (Class<?> clazz : classArrayList) {
            Author author = clazz.getAnnotation(Author.class);
            int commandCount = 0;
            for (Method method : clazz.getMethods()) {
                Command command = method.getAnnotation(Command.class);
                if (command != null) {
                    commandCount++;
                }
            }
            if (author != null) {
                stringBuilder.append("\n").append(clazz.getSimpleName()).append("的").append(commandCount).append("条命令").append(":").append(author.value());
            }
        }
        messageEvent.send(stringBuilder.toString());
    }

    @Command("/Base64")
    @Usage("/Base64 <encode/decode> <text>")
    public void Base64(MessageEvent<?, ?> messageEvent) {
        String oriMessage = MessageEventKt.getOriginalContent(messageEvent);
        String[] oriMsg = oriMessage.split(" ");
        String type = oriMsg[2];
        if (type == null) {
            messageEvent.send("用法:/Base64 <encode/decode> <string>");
            return;
        }
        String input = oriMsg[3];
        if (input == null) {
            messageEvent.send("用法:/Base64 <encode/decode> <string>");
            return;
        }
        if (type.equals("encode")) {
            messageEvent.send(Base64Util.encode(input));
        } else if (type.equals("decode")) {
            messageEvent.send(Base64Util.decode(input));
        } else {
            messageEvent.send("用法:/Base64 <encode/decode> <string>");
        }
    }

    @Command("/MD5")
    @Usage("/MD5 <text>")
    public void MD5(MessageEvent<?, ?> event) {
        String oriMessage = MessageEventKt.getOriginalContent(event);
        if (oriMessage.split(" ")[2] == null) {
            event.send("用法:/MD5 <string>");
        } else {
            event.send(new MD5Util().toMD5(oriMessage.split(" ")[2]));
        }
    }

    @Command("/Unicode")
    @Usage("/Unicode <encode/decode> <text>")
    public void Unicode(MessageEvent<?, ?> messageEvent) {
        UnicodeUtil unicodeUtil = new UnicodeUtil();
        String oriMessage = MessageEventKt.getOriginalContent(messageEvent);
        String[] oriMsg = oriMessage.split(" ");
        String type = oriMsg[2];
        if (type == null) {
            messageEvent.send("用法:/Unicode <encode/decode> <string>");
            return;
        }
        String input = oriMsg[3];
        if (input == null) {
            messageEvent.send("用法:/Unicode <encode/decode> <string>");
            return;
        }
        if (type.equals("encode")) {
            messageEvent.send(unicodeUtil.unicodeEncode(input));
        } else if (type.equals("decode")) {
            messageEvent.send(unicodeUtil.unicodeDecode(input));
        } else {
            messageEvent.send("用法:/Unicode <encode/decode> <string>");
        }
    }

    @Command("/Urlcode")
    @Usage("/Urlcode <decode/encode> <text>")
    public void UrlCode(MessageEvent<?, ?> messageEvent) {
        URLCodeUtil util = new URLCodeUtil();
        String oriMessage = MessageEventKt.getOriginalContent(messageEvent);
        String[] oriMsg = oriMessage.split(" ");
        String type = oriMsg[2];
        if (type == null) {
            messageEvent.send("用法:/UrlCode <encode/decode> <string>");
            return;
        }
        String input = oriMsg[3];
        if (input == null) {
            messageEvent.send("用法:/UrlCode <encode/decode> <string>");
            return;
        }
        if (type.equals("encode")) {
            messageEvent.send(util.URLCodeEncode(input));
        } else if (type.equals("decode")) {
            messageEvent.send(util.URLCodeDecode(input));
        } else {
            messageEvent.send("用法:/UrlCode <encode/decode> <string>");
        }
    }

    @Command({"/指令帮助", "/Command-Help"})
    @Usage({"/指令帮助", "/Command-Help", "/指令帮助 <指令>", "/Command-Help <Command>"})
    public void CommandHelp(MessageEvent<?, ?> event) {
        String oriMessage = MessageEventKt.getOriginalContent(event);
        String arg = oriMessage.split(" ")[2];
        ArrayList<Method> methodArrayList = RegisterCommand.methodArrayList;
        for (Method m : methodArrayList) {
            if (m.getAnnotation(Command.class) != null) {
                for (String s : m.getAnnotation(Command.class).value()) {
                    if (s.equals(arg)) {
                        if (m.getAnnotation(Usage.class) == null) {
                            event.send("该指令尚未注册用法.");
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("指令").append(arg).append("的用法为:\n");
                            for (String string : m.getAnnotation(Usage.class).value()) {
                                stringBuilder.append(string)
                                        .append("\n");
                            }
                            event.send(stringBuilder.toString());
                        }
                    }
                }
            } else {
                event.send("找不到帮助消息");
            }
        }
    }
}
