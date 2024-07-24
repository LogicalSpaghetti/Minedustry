package me.spaghetti.minedustry.block.blocks.conveyor.conveyor;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ConveyorBlockEntityRenderer implements BlockEntityRenderer<ConveyorBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ConveyorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ConveyorBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int[] progress = entity.getProgress();
        Direction direction = entity.getBeltFacing();
        ItemStack firstStack = entity.getStack(0);
        renderStack(entity, matrices, vertexConsumers, firstStack, progress[0] + 16, direction);
        ItemStack secondStack = entity.getStack(1);
        renderStack(entity, matrices, vertexConsumers, secondStack, progress[1] + 8, direction);
        ItemStack thirdRenderStack = entity.getStack(2);
        renderStack(entity, matrices, vertexConsumers, thirdRenderStack, progress[2], direction);

    }

    private void renderStack(ConveyorBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack renderStack, int distanceAlong, Direction direction) {

        Vec3d vector = Vec3d.of(direction.getOpposite().getVector());
        vector = vector.normalize();
        vector = vector.multiply(distanceAlong / 24f);
        vector = vector.add(Vec3d.of(direction.getVector()).multiply(0.5));
        matrices.push();
        matrices.translate(0.5, 0.16f, 0.5);
        matrices.translate(vector.getX(), 0, vector.getZ());
        matrices.scale(0.35F, 0.35F, 0.35F);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
        assert entity.getWorld() != null;
        this.itemRenderer.renderItem(renderStack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
