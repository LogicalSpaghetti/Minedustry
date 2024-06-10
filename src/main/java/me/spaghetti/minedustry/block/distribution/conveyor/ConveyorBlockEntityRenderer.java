package me.spaghetti.minedustry.block.distribution.conveyor;

import net.minecraft.client.MinecraftClient;
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
    public ConveyorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(ConveyorBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        int[] progress = entity.getProgress();
        Direction direction = entity.getBeltFacing();
        ItemStack firstStack = entity.getFirstRenderStack();
        renderStack(entity, matrices, vertexConsumers, itemRenderer, firstStack, progress[0] + 16, direction);
        ItemStack secondStack = entity.getSecondRenderStack();
        renderStack(entity, matrices, vertexConsumers, itemRenderer, secondStack, progress[1] + 8, direction);
        ItemStack thirdRenderStack = entity.getThirdRenderStack();
        renderStack(entity, matrices, vertexConsumers, itemRenderer, thirdRenderStack, progress[2], direction);
    }

    private void renderStack(ConveyorBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemRenderer itemRenderer, ItemStack thirdRenderStack, int distanceAlong, Direction direction) {

        Vec3d vector = Vec3d.of(direction.getOpposite().getVector());
        vector = vector.normalize();
        vector = vector.multiply(distanceAlong / 24f);
        vector = vector.add(Vec3d.of(direction.getVector()).multiply(0.5));
        matrices.push();
        matrices.translate(0.5, 0.16f, 0.5);
        matrices.translate(vector.getX(), 0, vector.getZ());
        matrices.scale(0.35F, 0.35F, 0.35F);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));

        itemRenderer.renderItem(thirdRenderStack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
