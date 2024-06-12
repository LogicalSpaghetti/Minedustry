package me.spaghetti.minedustry.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public abstract class AbstractModHandledScreen<T extends AbstractModScreenHandler> extends HandledScreen<T> {

    public AbstractModHandledScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}
