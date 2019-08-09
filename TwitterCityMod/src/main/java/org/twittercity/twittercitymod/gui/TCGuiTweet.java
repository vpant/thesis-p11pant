package org.twittercity.twittercitymod.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.data.Tweet.Tweet;

import com.google.gson.JsonParseException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//Tweet profile pic 48x48
public class TCGuiTweet extends GuiScreen {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/tweet.png");
	public static final int WIDTH = 256, HEIGHT = 140;

	private Tweet tweet;
	private List<ITextComponent> cachedComponents;

	private final int tweetWidth = 252;
	//private final int tweetTextAreaHeight = 97;

	private TCGuiTweet.NextPageButton buttonNextPage;
	private TCGuiTweet.NextPageButton buttonPreviousPage;

	private int totalPages = 1;
	private int currPage;

	private ArrayList<String> textPages;

	public TCGuiTweet(Tweet tweet) {
		this.tweet = tweet;

		if (this.textPages == null)
        {
            this.textPages = new ArrayList<String>();
            this.textPages.add("");
            this.totalPages = 1;
        }
	}


	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		super.initGui();
		int i = (this.width - tweetWidth) / 2;
		this.buttonNextPage = (TCGuiTweet.NextPageButton) this.addButton(new TCGuiTweet.NextPageButton(1, i + 200, 115, true));
		this.buttonPreviousPage = (TCGuiTweet.NextPageButton) this.addButton(new TCGuiTweet.NextPageButton(2, i + 35, 115, false));
		this.updateButtons();
	}

	private void updateButtons() {
		this.buttonNextPage.visible = true;//(this.currPage < this.totalPages - 1);
		this.buttonPreviousPage.visible = true;//this.currPage > 0;
	}

	@Override
	public void drawScreen(int mx, int my, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - 252) / 2;
		this.drawTexturedModalRect(i, 2, 0, 0, 252, 140);

		String s1 = I18n.format("book.editTitle");
		int k = this.fontRenderer.getStringWidth(s1);
		this.fontRenderer.drawString(s1, i + 36 + (116 - k) / 2, 34, 0);
		String s2 = I18n.format("book.byAuthor", tweet.getAuthor());
		int i1 = this.fontRenderer.getStringWidth(s2);
		this.fontRenderer.drawString(TextFormatting.RED + s2, i + 36 + (116 - i1) / 2, 60, 0);
		String s5;
		String s4 = I18n.format("book.pageIndicator", this.currPage + 1, this.totalPages);
		if (textPages != null && this.currPage >= 0 && this.currPage < textPages.size()) {
			s5 = textPages.get(this.currPage);
		}
		s5 = tweet.getText();
		//this.pageInsertIntoCurrent(s5);
		
		try {
			ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s5);
			this.cachedComponents = itextcomponent != null
					? GuiUtilRenderComponents.splitText(itextcomponent, 191, this.fontRenderer, true, true)
					: null;
		} catch (JsonParseException var13) {
			this.cachedComponents = null;
		}

		int j1 = this.fontRenderer.getStringWidth(s4);
		this.fontRenderer.drawString(s4, i - j1 + 252 - 44, 18, 0);

		if (this.cachedComponents == null) {
			this.fontRenderer.drawSplitString(s5, i + 36, 34, 116, 0);
		} else {
			int k1 = Math.min(128 / this.fontRenderer.FONT_HEIGHT, this.cachedComponents.size());

			for (int l1 = 0; l1 < k1; ++l1) {
				ITextComponent itextcomponent2 = this.cachedComponents.get(l1);
				this.fontRenderer.drawString(itextcomponent2.getUnformattedText(), i + 36,
						34 + l1 * this.fontRenderer.FONT_HEIGHT, 0);
			}

			ITextComponent itextcomponent1 = this.getClickedComponentAt(mx, my);

			if (itextcomponent1 != null) {
				this.handleComponentHover(itextcomponent1, mx, my);
			}
		}

		super.drawScreen(mx, my, partialTicks);
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 1) {
				if (this.currPage < this.totalPages - 1) {
					++this.currPage;
				} else {
					this.addNewPage();
					if (this.currPage < this.totalPages - 1) {
						++this.currPage;
					}
				}
			} else if (button.id == 2) {
				if (this.currPage > 0) {
					--this.currPage;
				}
			}
			this.updateButtons();
		}
	}

	private void addNewPage() {
		if (this.textPages != null) {
			this.textPages.add("");
			++this.totalPages;
		}
	}

	/**
	 * Processes any text getting inserted into the current page, enforcing the page
	 * size limit
	 */
	private void pageInsertIntoCurrent(String p_146459_1_) {
		String s = this.pageGetCurrent();
		String s1 = s + p_146459_1_;
		int i = this.fontRenderer.getWordWrappedHeight(s1 + "" + TextFormatting.BLACK + "_", 118);

		if (i <= 128 && s1.length() < 256) {
			this.pageSetCurrent(s1);
		}
	}

	/**
	 * Sets the text of the current page as determined by currPage
	 */
	private void pageSetCurrent(String p_146457_1_) {
		if (this.textPages != null && this.currPage >= 0) {
			textPages.add(this.currPage, p_146457_1_);
		}
	}

	/**
	 * Returns the entire text of the current page as determined by currPage
	 */
	private String pageGetCurrent() {
		return this.currPage >= 0 ? textPages.get(this.currPage) : "";
	}

	
	@Nullable
    public ITextComponent getClickedComponentAt(int p_175385_1_, int p_175385_2_)
    {
        if (this.cachedComponents == null)
        {
            return null;
        }
        else
        {
            int i = p_175385_1_ - (this.width - 252) / 2 - 36;
            int j = p_175385_2_ - 2 - 16 - 16;

            if (i >= 0 && j >= 0)
            {
                int k = Math.min(128 / this.fontRenderer.FONT_HEIGHT, this.cachedComponents.size());

                if (i <= 116 && j < this.mc.fontRenderer.FONT_HEIGHT * k + k)
                {
                    int l = j / this.mc.fontRenderer.FONT_HEIGHT;

                    if (l >= 0 && l < this.cachedComponents.size())
                    {
                        ITextComponent itextcomponent = this.cachedComponents.get(l);
                        int i1 = 0;

                        for (ITextComponent itextcomponent1 : itextcomponent)
                        {
                            if (itextcomponent1 instanceof TextComponentString)
                            {
                                i1 += this.mc.fontRenderer.getStringWidth(((TextComponentString)itextcomponent1).getText());

                                if (i1 > i)
                                {
                                    return itextcomponent1;
                                }
                            }
                        }
                    }

                    return null;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }

	@SideOnly(Side.CLIENT)
	static class NextPageButton extends GuiButton {
		private final boolean isForward;

		public NextPageButton(int buttonId, int x, int y, boolean isForwardIn) {
			super(buttonId, x, y, 23, 13, "");
			this.isForward = isForwardIn;
			
		}

		/**
		 * Draws this button to the screen.
		 */
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(TCGuiTweet.TEXTURE);
				int i = 0;
				int j = 194;

				if (flag) {
					i += 23;
				}

				if (!this.isForward) {
					j += 13;
				}

				this.drawTexturedModalRect(this.x, this.y, i, j, 23, 13);
			}
		}
	}
}
