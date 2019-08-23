package org.twittercity.twittercitymod.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.data.db.Tweet;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
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
	private static BufferedImage bufImage = null;
	DynamicTexture dynTextures = null;
	
	public static final int WIDTH = 256, HEIGHT = 140;
	private Tweet tweet;
	private List<ITextComponent> cachedComponents;
	private List<ArrayList<ITextComponent>> textComponentPages;

	TextureManager textManager;	
	// Should be final but for debugging ignore
	private int TWEET_TEXT_MAX_ROW_LENGTH = 191;
	// private final int tweetTextAreaHeight = 97;

	private TCGuiTweet.NextPageButton buttonNextPage;
	private TCGuiTweet.NextPageButton buttonPreviousPage;

	private int totalPages = 0;
	private int currPage;

	public TCGuiTweet(Tweet tweet) {
		this.tweet = tweet;
		
		bufImage = tweet.getProfilePicture();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		super.initGui();
		int x = (this.width - WIDTH) / 2;
		int y = (this.height - HEIGHT) / 2;
		this.buttonNextPage = (TCGuiTweet.NextPageButton) this
				.addButton(new TCGuiTweet.NextPageButton(1, x + 200, y + 115, true));
		this.buttonPreviousPage = (TCGuiTweet.NextPageButton) this
				.addButton(new TCGuiTweet.NextPageButton(2, x + 35, y + 115, false));
		this.updateButtons();
		
		textManager = this.mc.getTextureManager();
	}

	private void updateButtons() {
		this.buttonNextPage.visible = (this.currPage < this.totalPages - 1);
		this.buttonPreviousPage.visible = this.currPage > 0;
	}

	private void createTextPages(String text) {

		ITextComponent itextcomponent = new TextComponentString(text);
		this.cachedComponents = itextcomponent != null
				? GuiUtilRenderComponents.splitText(itextcomponent, TWEET_TEXT_MAX_ROW_LENGTH, this.fontRenderer, true,
						true)
				: null;

		int tweetRowsPerPage = Math.min(65 / this.fontRenderer.FONT_HEIGHT, this.cachedComponents.size());

		int counter = 0, pageCounter = 0;
		this.textComponentPages = new ArrayList<ArrayList<ITextComponent>>();
		List<ITextComponent> tempList = Lists.<ITextComponent>newArrayList();
		for (ITextComponent component : this.cachedComponents) {
			if (counter <= tweetRowsPerPage) {
				tempList.add(component);
				counter++;
			} else {
				this.textComponentPages.add((ArrayList<ITextComponent>) tempList);
				tempList = Lists.<ITextComponent>newArrayList();
				counter = 0;
				pageCounter++;
				this.addNewPage();
			}
		}
		if (!tempList.isEmpty()) {
			this.textComponentPages.add(pageCounter, (ArrayList<ITextComponent>) tempList);
			this.addNewPage();
		}
		this.updateButtons();
	}

	@Override
	public void drawScreen(int mx, int my, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		textManager.bindTexture(TEXTURE);
		int x = (this.width - WIDTH) / 2;
		int y = (this.height - 140) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, WIDTH, 140);

		if (this.textComponentPages == null) {
			this.createTextPages(this.tweet.getText());
		}

		String s1 = I18n.format("book.editTitle");
		int k = this.fontRenderer.getStringWidth(s1);
		this.fontRenderer.drawString(s1, x + 36 + (180 - k) / 2, y + 15, 0);

		String bookAuthor = I18n.format("book.byAuthor", tweet.getAuthor());
		int i1 = this.fontRenderer.getStringWidth(bookAuthor);
		this.fontRenderer.drawString(TextFormatting.RED + bookAuthor, x + 36 + (180 - i1) / 2, y + 115, 0);

		String pagesCountText = I18n.format("book.pageIndicator", this.currPage + 1, this.totalPages);
		int j1 = this.fontRenderer.getStringWidth(pagesCountText);
		this.fontRenderer.drawString(TextFormatting.RED + pagesCountText, x - j1 + 285 - 44, y + 15, 0);

		if (this.currPage <= this.totalPages - 1) {
			for (int l1 = 0; l1 < this.textComponentPages.get(this.currPage).size(); ++l1) {
				ITextComponent itextcomponent2 = this.textComponentPages.get(this.currPage).get(l1);
				this.fontRenderer.drawString(TextFormatting.WHITE + itextcomponent2.getUnformattedText(), x + 36,
						y + 40 + l1 * this.fontRenderer.FONT_HEIGHT, 0);
			}
		}

		ITextComponent itextcomponent1 = this.getClickedComponentAt(mx, my);
		if (itextcomponent1 != null) {
			this.handleComponentHover(itextcomponent1, mx, my);
		}	

		if (bufImage != null) {
			if(dynTextures == null) {
				dynTextures = new DynamicTexture(bufImage);
			}
			textManager.bindTexture(textManager.getDynamicTextureLocation(Reference.MOD_ID, dynTextures));
		}
		else {
			ResourceLocation placeholderRS = new ResourceLocation(Reference.MOD_ID, "textures/gui/no-image.png");
			textManager.bindTexture(placeholderRS);
			
		}
		this.drawTexturedModalRect((x + WIDTH - 70) / 2, y + 6, 0, 0, 24, 24);
		super.drawScreen(mx, my, partialTicks);
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 1) {
				if (this.currPage < this.totalPages - 1) {
					++this.currPage;
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
		++this.totalPages;
	}

	@Nullable
	public ITextComponent getClickedComponentAt(int p_175385_1_, int p_175385_2_) {
		if (this.cachedComponents == null) {
			return null;
		} else {
			int i = p_175385_1_ - (this.width - 252) / 2 - 36;
			int j = p_175385_2_ - 2 - 16 - 16;

			if (i >= 0 && j >= 0) {
				int k = Math.min(128 / this.fontRenderer.FONT_HEIGHT, this.cachedComponents.size());

				if (i <= 116 && j < this.mc.fontRenderer.FONT_HEIGHT * k + k) {
					int l = j / this.mc.fontRenderer.FONT_HEIGHT;

					if (l >= 0 && l < this.cachedComponents.size()) {
						ITextComponent itextcomponent = this.cachedComponents.get(l);
						int i1 = 0;

						for (ITextComponent itextcomponent1 : itextcomponent) {
							if (itextcomponent1 instanceof TextComponentString) {
								i1 += this.mc.fontRenderer
										.getStringWidth(((TextComponentString) itextcomponent1).getText());

								if (i1 > i) {
									return itextcomponent1;
								}
							}
						}
					}

					return null;
				} else {
					return null;
				}
			} else {
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
				boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
						&& mouseY < this.y + this.height;
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
