package me.spaghetti.minedustry.screen.steam_generator;

import com.mojang.blaze3d.systems.RenderSystem;
import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.screen.AbstractModHandledScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SteamGeneratorScreen extends AbstractModHandledScreen<SteamGeneratorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Minedustry.MOD_ID, "textures/gui/single_tank.png");

    public SteamGeneratorScreen(SteamGeneratorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        // change label locations here
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderFluidTank(context, x, y);
    }

    private void renderFluidTank(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE, x + 17, y + 74 - handler.getScaledProgress(), 176 + 32, 0, 16, handler.getScaledProgress());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
