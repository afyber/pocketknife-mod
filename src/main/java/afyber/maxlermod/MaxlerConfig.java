package afyber.maxlermod;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class MaxlerConfig {
	private static final String CATEGORY_DIFFICULTY = "difficulty";

	public static boolean expensiveCrystalRecipe = true;

	public static void readConfig() {
		Configuration cfg = MaxlerMod.config;
		try {
			cfg.load();
			initDifficultyConfig(cfg);
		} catch (Exception e) {
			MaxlerMod.logger().log(Level.ERROR, "Problem loading config file!", e);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	private static void initDifficultyConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_DIFFICULTY, "Difficulty configuration (SOME CHANGES REQUIRE A RESTART TO TAKE EFFECT)");
		expensiveCrystalRecipe = cfg.getBoolean("expensiveCrystalRecipe", CATEGORY_DIFFICULTY, expensiveCrystalRecipe, "If true the crystal recipe uses 8 diamonds, if false it uses 4.");

	}
}
