package me.spaghetti.minedustry.block.helpers;

import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Range;

public class SlotRandomizer {
    public static Vec3i[] getInventoryOffsets(@Range(from=1,to=Integer.MAX_VALUE)int size) {
        // the initial corner is the far North-West corner (negative, negative)
        Vec3i[] offsets = new Vec3i[size * 4];
        for (int i = 0; i < size; i++) {

            offsets[i] = new Vec3i(i, 0, -1); // north face
            offsets[i + size] = new Vec3i(size, 0, i); // east face
            offsets[i + 2 * size] = new Vec3i(size - 1 - i, 0, size); // south face
            offsets[i + 3 * size] = new Vec3i(size - 1 - i, 0, i); // west face
        }

        return offsets;
    }
}
