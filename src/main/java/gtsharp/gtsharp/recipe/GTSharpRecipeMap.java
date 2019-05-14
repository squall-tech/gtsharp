package gtsharp.gtsharp.recipe;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;

public class GTSharpRecipeMap {

    public static final RecipeMap<SimpleRecipeBuilder> PRESSURIZER_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> HEAT_EXCHANGER_RECIPES;

    static {
        PRESSURIZER_RECIPES = (new RecipeMap("pressurizer", 0, 0, 0, 0, 1, 1, 1, 1, 1, new SimpleRecipeBuilder())).setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, ProgressWidget.MoveType.HORIZONTAL);
        HEAT_EXCHANGER_RECIPES = (new RecipeMap("heat_exchanger", 0, 0, 0, 0, 2, 2, 2, 2, 0, new SimpleRecipeBuilder())).setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, ProgressWidget.MoveType.HORIZONTAL);
    }
}
