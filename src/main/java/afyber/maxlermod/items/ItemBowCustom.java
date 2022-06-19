package afyber.maxlermod.items;

import afyber.maxlermod.enchantments.CustomEnchantments;
import afyber.maxlermod.entity.HomingArrow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBowCustom extends ItemBow {

	private final float bonusDamage;

	public ItemBowCustom(int maxDamage, float bonusDamage) {
		super();
		this.bonusDamage = bonusDamage;
		this.setMaxDamage(maxDamage);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;
			boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemstack = this.findAmmo(entityplayer);

			int i = this.getMaxItemUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
			if (i < 0) return;

			if (!itemstack.isEmpty() || flag)
			{
				if (itemstack.isEmpty())
				{
					itemstack = new ItemStack(Items.ARROW);
				}

				float f = getArrowVelocity(i);

				if ((double)f >= 0.1D)
				{
					boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

					if (!worldIn.isRemote)
					{
						ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
						EntityArrow entityarrow;
						if (EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.HOMING, stack) > 0) {
							entityarrow = createHomingArrow(worldIn, itemstack, entityplayer);
						}
						else {
							entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
						}
						entityarrow = this.customizeArrow(entityarrow);
						entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

						if (f == 1.0F)
						{
							entityarrow.setIsCritical(true);
						}

						int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

						if (j > 0)
						{
							entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
						}

						int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

						if (k > 0)
						{
							entityarrow.setKnockbackStrength(k);
						}

						if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
						{
							entityarrow.setFire(100);
						}

						stack.damageItem(1, entityplayer);

						if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
						{
							entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
						}

						worldIn.spawnEntity(entityarrow);
					}

					worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

					if (!flag1 && !entityplayer.capabilities.isCreativeMode)
					{
						itemstack.shrink(1);

						if (itemstack.isEmpty())
						{
							entityplayer.inventory.deleteStack(itemstack);
						}
					}

					entityplayer.addStat(StatList.getObjectUseStats(this));
				}
			}
		}
	}

	public EntityArrow createHomingArrow(World worldIn, ItemStack stack, EntityLivingBase shooter)
	{
		EntityTippedArrow entitytippedarrow = new HomingArrow(worldIn, shooter);
		entitytippedarrow.setPotionEffect(stack);
		return entitytippedarrow;
	}

	@Override
	public EntityArrow customizeArrow(EntityArrow arrow) {
		arrow.setDamage(arrow.getDamage() + bonusDamage);
		return arrow;
	}
}
