package me.spaghetti.minedustry.util;

import me.spaghetti.minedustry.block.properties.ConveyorShape;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final EnumProperty<ConveyorShape> CONVEYOR_SHAPE = EnumProperty.of("shape", ConveyorShape.class);

}
