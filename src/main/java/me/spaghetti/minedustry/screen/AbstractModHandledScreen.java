package me.spaghetti.minedustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class AbstractModHandledScreen<T extends AbstractModScreenHandler> extends HandledScreen<T> {

    public AbstractModHandledScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}
