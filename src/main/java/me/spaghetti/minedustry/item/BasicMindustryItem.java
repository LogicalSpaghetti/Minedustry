package me.spaghetti.minedustry.item;

import net.minecraft.item.Item;

public class BasicMindustryItem extends Item {
    // these only matter if the item's container gets destroyed
    private final int explosiveness;
    private final int flammability;
    private final int radioactivity;
    private final int charge;

    public BasicMindustryItem(Settings settings, int explosiveness, int flammability, int radioactivity, int charge) {
        super(settings);
        this.explosiveness = explosiveness;
        this.flammability = flammability;
        this.radioactivity = radioactivity;
        this.charge = charge;
    }
    public BasicMindustryItem(Settings settings) {
        super(settings);
        this.explosiveness = 0;
        this.flammability = 0;
        this.radioactivity = 0;
        this.charge = 0;
    }
}
