package afyber.pocketknife;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class PocketknifeConfig {

	private static final String CATEGORY_GENERAL = "general";
	private static final String CATEGORY_DIFFICULTY = "difficulty";

	public static boolean hasAdamantiteTools = true;
	public static boolean hasAdamantiteMaterial = true;
	public static boolean hasCrystallineTools = true;
	public static boolean hasCrystallineMaterial = true;
	public static boolean expensiveCrystalRecipe = true;

	public static void readConfig() {
		Configuration cfg = Pocketknife.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
			initDifficultyConfig(cfg);
		} catch (Exception e) {
			Pocketknife.logger().log(Level.ERROR, "Problem loading config file!", e);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration (SOME CHANGES REQUIRE A RESTART TO TAKE EFFECT)");
		hasAdamantiteTools = cfg.getBoolean("hasAdamantiteTools", CATEGORY_GENERAL, hasAdamantiteTools, "If false Adamantite tools and weapons will not be present (May break worlds)");
		hasAdamantiteMaterial = cfg.getBoolean("hasAdamantiteMaterial", CATEGORY_GENERAL, hasAdamantiteMaterial, "If false the Adamantite Ingot will not be present and all recipes involving it will be removed (May break worlds)");
		hasCrystallineTools = cfg.getBoolean("hasCrystallineTools", CATEGORY_GENERAL, hasCrystallineTools, "If false Crystalline tools and weapons will not be present (May break worlds)");
		hasCrystallineMaterial = cfg.getBoolean("hasCrystallineMaterial", CATEGORY_GENERAL, hasCrystallineMaterial, "If false the Crystal and related materials will not be present and all recipes involving them will be removed (May break worlds)");
	}

	private static void initDifficultyConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_DIFFICULTY, "Difficulty configuration (SOME CHANGES REQUIRE A RESTART TO TAKE EFFECT)");
		expensiveCrystalRecipe = cfg.getBoolean("expensiveCrystalRecipe", CATEGORY_DIFFICULTY, expensiveCrystalRecipe, "If true the crystal recipe uses 8 diamonds, if false it uses 4.");

	}
}
