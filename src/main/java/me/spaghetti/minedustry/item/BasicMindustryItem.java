package me.spaghetti.minedustry.item;

import net.minecraft.item.Item;

public class BasicMindustryItem extends Item {
    private int explosiveness;
    private int flammability;
    private int radioactivity;
    private int charge;

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
