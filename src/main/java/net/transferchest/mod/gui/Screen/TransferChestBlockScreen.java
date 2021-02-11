package net.transferchest.mod.gui.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.gui.ScrollPanel;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.loader.TCLoader;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TransferChestBlockScreen extends ContainerScreen<TransferChestContainer>
{
    private TransferChestContainer container;
    private WatchersList list;
    
    public TransferChestBlockScreen(TransferChestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }
    
    public static final ResourceLocation GUI_TEXTURE_LOCATION = new ResourceLocation(TCLoader.MOD_ID, "textures/gui/transferchest.png");
    
    @Override protected void init()
    {
        super.init();
        list = new WatchersList(minecraft, 72, 70, guiTop + 8, guiLeft + 97);
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        if(list != null)
        {
            list.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll)
    {
        if(list != null)
            list.mouseScrolled(mouseX, mouseY, scroll);
        
        return super.mouseScrolled(mouseX, mouseY, scroll);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(list != null)
            list.mouseClicked(mouseX, mouseY, button);
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        if(list != null)
            list.mouseReleased(mouseX, mouseY, button);
        
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
    {
        if(list != null)
            list.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
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
    
    private class WatchersList extends ScrollPanel
    {
        public WatchersList(Minecraft client, int width, int height, int top, int left)
        {
            super(client, width, height, top, left);
        }
        
        @Override protected int getContentHeight()
        {
            return container.getWatchers().length * (font.FONT_HEIGHT + 2);
        }
        
        private int getBarHeight()
        {
            int barHeight = (height * height) / this.getContentHeight();
            
            if(barHeight < 32) barHeight = 32;
            
            if(barHeight > height - border * 2)
                barHeight = height - border * 2;
            
            return barHeight;
        }
        
        @Override public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
        {
            this.drawBackground();
            
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder worldr = tess.getBuffer();
            
            double scale = minecraft.getMainWindow().getGuiScaleFactor();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor((int) (left * scale), (int) (minecraft.getMainWindow().getFramebufferHeight() - (bottom * scale)),
                  (int) (width * scale), (int) (height * scale));
            
            int baseY = this.top + border - (int) this.scrollDistance;
            this.drawPanel(matrix, right, baseY, tess, mouseX, mouseY);
            
            RenderSystem.disableDepthTest();
            
            int extraHeight = (this.getContentHeight() + border) - height;
            if(extraHeight > 0)
            {
                int barHeight = getBarHeight();
                
                int barTop = (int) this.scrollDistance * (height - barHeight) / extraHeight + this.top;
                if(barTop < this.top)
                {
                    barTop = this.top;
                }
                
                int barWidth = 6;
                int barLeft = this.left + this.width - barWidth;
                RenderSystem.disableTexture();
                worldr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldr.pos(barLeft, this.bottom, 0.0D).tex(0.0F, 1.0F).color(160, 160, 160, 0xFF).endVertex();
                worldr.pos(barLeft + 6, this.bottom, 0.0D).tex(1.0F, 1.0F).color(160, 160, 160, 0xFF).endVertex();
                worldr.pos(barLeft + barWidth, this.top, 0.0D).tex(1.0F, 0.0F).color(160, 160, 160, 0xFF).endVertex();
                worldr.pos(barLeft, this.top, 0.0D).tex(0.0F, 0.0F).color(160, 160, 160, 0xFF).endVertex();
                tess.draw();
                worldr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldr.pos(barLeft, barTop + barHeight, 0.0D).tex(0.0F, 1.0F).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                worldr.pos(barLeft + barWidth, barTop + barHeight, 0.0D).tex(1.0F, 1.0F).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                worldr.pos(barLeft + barWidth, barTop, 0.0D).tex(1.0F, 0.0F).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                worldr.pos(barLeft, barTop, 0.0D).tex(0.0F, 0.0F).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                tess.draw();
                worldr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldr.pos(barLeft, barTop + barHeight - 1, 0.0D).tex(0.0F, 1.0F).color(225, 225, 225, 0xFF).endVertex();
                worldr.pos(barLeft + barWidth - 1, barTop + barHeight - 1, 0.0D).tex(1.0F, 1.0F).color(225, 225, 225, 0xFF).endVertex();
                worldr.pos(barLeft + barWidth - 1, barTop, 0.0D).tex(1.0F, 0.0F).color(225, 225, 225, 0xFF).endVertex();
                worldr.pos(barLeft, barTop, 0.0D).tex(0.0F, 0.0F).color(225, 225, 225, 0xFF).endVertex();
                tess.draw();
            }
            
            RenderSystem.enableTexture();
            RenderSystem.shadeModel(GL11.GL_FLAT);
            RenderSystem.enableAlphaTest();
            RenderSystem.disableBlend();
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        
        @Override
        protected void drawPanel(MatrixStack mStack, int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY)
        {
            String[] watchers = container.getWatchers();
            //draw entry strings
            for(int i = 0; i < watchers.length; i++)
            {
                String name = watchers[i];
                if(name.length() > 11)
                {
                    name = name.substring(0, 9);
                    name += "...";
                }
                if(name != null && !name.equals(""))
                {
                    font.drawString(mStack, name, left - 2 + width / 2 - font.getStringWidth(name) / 2, relativeY + (12 * i), new Color(65, 65, 65).getRGB());
                }
            }
        }
    }
}
