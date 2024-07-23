package me.spaghetti.minedustry.client;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class CopperHudOverlay implements HudRenderCallback {
    private static final Identifier filledCopper = new Identifier(Minedustry.MOD_ID,
            "textures/copper/filled_thirst.png");
    private static final Identifier emptyCopper = new Identifier(Minedustry.MOD_ID,
            "textures/copper/empty_thirst.png");
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width/2;
            y = height;
        }

        for(int i = 0; i < 10; i++) {
            drawContext.drawTexture(emptyCopper,x - 94 + (i * 9),y - 54,0,0,12,12,
                    12,12);
        }

        for(int i = 0; i < 10; i++) {
            if(((IEntityDataSaver) MinecraftClient.getInstance().player).getPersistentData().getInt("copper") > i) {
                drawContext.drawTexture(filledCopper,x - 94 + (i * 9),y - 54,0,0,12,12,
                        12,12);
            } else {
                break;
            }
        }
    }
}
