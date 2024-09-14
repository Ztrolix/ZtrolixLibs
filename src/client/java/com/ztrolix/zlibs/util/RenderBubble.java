package com.ztrolix.zlibs.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ztrolix.zlibs.mixin.client.DrawContextAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

@Environment(EnvType.CLIENT)
public class RenderBubble {
    private static final Identifier BACKGROUND = Identifier.of("talkbubbles:textures/gui/background.png");

    public static void renderBubble(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, TextRenderer textRenderer, EntityRenderDispatcher entityRenderDispatcher,
                                    List<String> textList, int width, int height, float playerHeight, int i) {
        matrixStack.push();

        matrixStack.translate(0.0D, playerHeight + 0.9F + (height > 5 ? 0.1F : 0.0F) + 0.0f, 0.0D);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(toEulerXyzDegrees(entityRenderDispatcher.getRotation()).y()));
        matrixStack.scale(0.025F, -0.025F, 0.025F);

        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.7F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        RenderSystem.enablePolygonOffset();
        RenderSystem.polygonOffset(3.0F, 3.0F);
        MinecraftClient client = MinecraftClient.getInstance();
        DrawContext context = DrawContextAccessor.getDrawContext(client, matrixStack, client.getBufferBuilders().getEntityVertexConsumers());
        context.drawTexture(BACKGROUND, -width / 2 - 2, -height - (height - 1) * 7, 5, 5, 0.0F, 0.0F, 5, 5, 32, 32);
        context.drawTexture(BACKGROUND, -width / 2 - 2, -height - (height - 1) * 7 + 5, 5, height + (height - 1) * 8, 0.0F, 6.0F, 5, 1, 32, 32);
        context.drawTexture(BACKGROUND, -width / 2 - 2, 5 + (height - 1), 5, 5, 0.0F, 8.0F, 5, 5, 32, 32);
        context.drawTexture(BACKGROUND, -width / 2 + 3, -height - (height - 1) * 7, width - 4, 5, 6.0F, 0.0F, 5, 5, 32, 32);
        context.drawTexture(BACKGROUND, -width / 2 + 3, -height - (height - 1) * 7 + 5, width - 4, height + (height - 1) * 8, 6.0F, 6.0F,
                5, 1, 32, 32);
        context.drawTexture(BACKGROUND, -width / 2 + 3, 5 + (height - 1), width - 4, 5, 6.0F, 8.0F, 5, 5, 32, 32);
        context.drawTexture(BACKGROUND, width / 2 - 1, -height - (height - 1) * 7, 5, 5, 12.0F, 0.0F, 5, 5, 32, 32);
        context.drawTexture(BACKGROUND, width / 2 - 1, -height - (height - 1) * 7 + 5, 5, height + (height - 1) * 8, 12.0F, 6.0F, 5, 1, 32, 32);
        context.drawTexture(BACKGROUND, width / 2 - 1, 5 + (height - 1), 5, 5, 12.0F, 8.0F, 5, 5, 32, 32);

        RenderSystem.polygonOffset(0.0F, 0.0F);
        RenderSystem.disablePolygonOffset();
        for (int u = textList.size(); u > 0; u--) {
            float h = (float) (-textRenderer.getWidth(textList.get(u - 1))) / 2.0F;
            textRenderer.draw(textList.get(u - 1), h, ((float) textList.size() + (u - textList.size()) * 9), 1315860, false, matrix4f, vertexConsumerProvider,
                    TextRenderer.TextLayerType.NORMAL, 0, i);
        }
        matrixStack.pop();

        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private static Vector3f toEulerXyz(Quaternionf quaternionf) {
        float f = quaternionf.w() * quaternionf.w();
        float g = quaternionf.x() * quaternionf.x();
        float h = quaternionf.y() * quaternionf.y();
        float i = quaternionf.z() * quaternionf.z();
        float j = f + g + h + i;
        float k = 2.0f * quaternionf.w() * quaternionf.x() - 2.0f * quaternionf.y() * quaternionf.z();
        float l = (float) Math.asin(k / j);
        if (Math.abs(k) > 0.999f * j) {
            return new Vector3f(l, 2.0f * (float) Math.atan2(quaternionf.y(), quaternionf.w()), 0.0f);
        }
        return new Vector3f(l, (float) Math.atan2(2.0f * quaternionf.x() * quaternionf.z() + 2.0f * quaternionf.y() * quaternionf.w(), f - g - h + i),
                (float) Math.atan2(2.0f * quaternionf.x() * quaternionf.y() + 2.0f * quaternionf.w() * quaternionf.z(), f - g + h - i));
    }

    private static Vector3f toEulerXyzDegrees(Quaternionf quaternionf) {
        Vector3f vec3f = RenderBubble.toEulerXyz(quaternionf);
        return new Vector3f((float) Math.toDegrees(vec3f.x()), (float) Math.toDegrees(vec3f.y()), (float) Math.toDegrees(vec3f.z()));
    }
}