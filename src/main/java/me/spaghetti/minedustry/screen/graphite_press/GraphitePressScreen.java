package me.spaghetti.minedustry.screen.graphite_press;

import com.mojang.blaze3d.systems.RenderSystem;
import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.screen.AbstractModHandledScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GraphitePressScreen extends AbstractModHandledScreen<GraphitePressScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Minedustry.MOD_ID, "textures/gui/gui_layout.png");

    public GraphitePressScreen(GraphitePressScreenHandler handler, PlayerInventory inventory, Text title) {
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
        int x = (width - backgroundWidth) / 2; // todo: choose a better size, add more fancy SC2 style edges
        int y = (height - backgroundHeight) / 2; // just modify the bgw and bgh

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(context, x, y);

        addSlot(context, x, y, 56, 36);
        addSlot(context, x, y, 104, 36);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE, x + 75, y + 40, 176, 0, 26, 8);
        if (handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 75, y + 40, 176 + 26, 0, handler.getScaledProgress(), 8);
        }
    }

    private void addSlot(DrawContext context, int x, int y, int slotX, int slotY) {
        // uses the same position as the actual slot, regardless of border
        // (208, 8) is the location on the sheet
        context.drawTexture(TEXTURE, x + slotX - 1, y + slotY - 1,
                208, 8, 18, 18);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
