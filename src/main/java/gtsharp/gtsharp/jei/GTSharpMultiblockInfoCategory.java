package gtsharp.gtsharp.jei;

import com.google.common.collect.Lists;
import gregtech.integration.jei.multiblock.MultiblockInfoRecipeWrapper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.gui.recipes.RecipeLayout;

public class GTSharpMultiblockInfoCategory implements IRecipeCategory<MultiblockInfoRecipeWrapper> {

    private final IDrawable background;

    public GTSharpMultiblockInfoCategory(IJeiHelpers helpers) {
        this.background = helpers.getGuiHelper().createBlankDrawable(176, 166);
    }

    public static void registerRecipes(IModRegistry registry) {
        registry.addRecipes(Lists.newArrayList(
                new MultiblockInfoRecipeWrapper(new FissionReactorInfo())

        ), "gtsharp:multiblock_info");
    }

    @Override
    public String getUid() {
        return "gtsharp:multiblock_info";
    }

    @Override
    public String getTitle() {
        return "sei la";
    }

    @Override
    public String getModName() {
        return "GTSharp";
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MultiblockInfoRecipeWrapper multiblockInfoRecipeWrapper, IIngredients iIngredients) {
        multiblockInfoRecipeWrapper.setRecipeLayout((RecipeLayout) iRecipeLayout);
    }
}