package gtsharp.gtsharp;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;

public class GTSharpRecipeMap {

    public static final RecipeMap<SimpleRecipeBuilder> FISSION_REACTOR_RECIPE;

    static {
        FISSION_REACTOR_RECIPE = (new RecipeMap("fission_reactor", 0, 1, 0, 0, 0, 1, 1, 2, 8, new SimpleRecipeBuilder())).setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, ProgressWidget.MoveType.HORIZONTAL);
    }
}
