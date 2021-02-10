package net.transferchest.mod.gui.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.loader.TCLoader;

public class TransferChestBlockScreen extends ContainerScreen<TransferChestContainer>
{
    public TransferChestBlockScreen(TransferChestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
    }
    
    public static final ResourceLocation GUI_TEXTURE_LOCATION = new ResourceLocation(TCLoader.MOD_ID, "textures/gui/transferchest.png");
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        
        minecraft.textureManager.bindTexture(GUI_TEXTURE_LOCATION);
        this.blit(matrixStack, x, y, 0, 0, xSize, ySize);
    }
}
