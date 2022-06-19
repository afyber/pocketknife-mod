package afyber.maxlermod.entity;

import afyber.maxlermod.MaxlerMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomingArrow extends EntityTippedArrow {

	// The madness of the reflection has begun, HYAAAAAAAAA
	public static Field xTile;
	public static Field yTile;
	public static Field zTile;

	public static Field inTile;
	public static Field inData;
	public static Field ticksInGround;
	public static Field ticksInAir;

	public static Field customPotionEffects;
	public static Field potion;
	public static Field COLOR;

	public static Method spawnPotionParticles;

	protected static final DataParameter<Integer> TARGET = EntityDataManager.createKey(HomingArrow.class, DataSerializers.VARINT);

	public HomingArrow(World worldIn)
	{
		super(worldIn);
	}

	public HomingArrow(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public HomingArrow(World worldIn, EntityLivingBase shooter)
	{
		super(worldIn, shooter);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		this.dataManager.register(TARGET, -1);
	}

	@Override
	public void onUpdate() {
		super.onEntityUpdate();

		if (this.getTarget() == -1) {
			List<Entity> list = this.getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - 3, this.posY - 3, this.posZ - 3, this.posX + 3, this.posY + 3, this.posZ + 3));
			Stream<Entity> stream = list.stream().filter(entity -> !(entity instanceof EntityPlayer));
			ArrayList<Entity> listMob = (ArrayList<Entity>)stream.filter(IMob.class::isInstance).collect(Collectors.toList());
			if (!listMob.isEmpty()) {
				listMob.sort((entity1, entity2) -> {
					float health1 = ((EntityLivingBase)entity1).getMaxHealth();
					float health2 = ((EntityLivingBase)entity2).getMaxHealth();
					if (health1 < health2) {
						return 1;
					} else if (health1 == health2) {
						return 0;
					}
					return -1;
				});

				this.dataManager.set(TARGET, list.get(0).getEntityId());

				String message = world.getEntityByID(this.dataManager.get(TARGET)).toString();
				MaxlerMod.logger().info(message);
			}
		}

		try {
			onUpdateArrow();

			onUpdateTippedArrow();
		}
		catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
	}

	private void onUpdateArrow() throws IllegalAccessException {
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
			this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}

		BlockPos blockpos;
		try {
			blockpos = new BlockPos(xTile.getFloat(this), yTile.getFloat(this), zTile.getFloat(this));
		}
		catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		if (iblockstate.getMaterial() != Material.AIR)
		{
			AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

			if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ)))
			{
				inGround = true;
			}
		}

		if (this.arrowShake > 0)
		{
			--this.arrowShake;
		}

		if (inGround)
		{
			int j = block.getMetaFromState(iblockstate);

			if ((block != inTile.get(this) || j != inData.getInt(this)) && !this.world.collidesWithAnyBlock(this.getEntityBoundingBox().grow(0.05D)))
			{
				inGround = false;
				this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
				ticksInGround.set(this, 0);
				ticksInAir.set(this, 0);
			}
			else
			{
				ticksInGround.set(this, ticksInGround.getInt(this) + 1);

				if (ticksInGround.getInt(this) >= 1200)
				{
					this.setDead();
				}
			}

			++this.timeInGround;
		}
		else
		{
			this.timeInGround = 0;
			ticksInAir.set(this, ticksInAir.getInt(this) + 1);
			Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
			vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (raytraceresult != null)
			{
				vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
			}

			Entity entity = this.findEntityOnPath(vec3d1, vec3d);

			if (entity != null)
			{
				raytraceresult = new RayTraceResult(entity);
			}

			if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)raytraceresult.entityHit;

				if (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
				{
					raytraceresult = null;
				}
			}

			if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
			{
				this.onHit(raytraceresult);
			}

			if (this.getIsCritical())
			{
				for (int k = 0; k < 4; ++k)
				{
					this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double)k / 4.0D, this.posY + this.motionY * (double)k / 4.0D, this.posZ + this.motionZ * (double)k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

			for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f4) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			{
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f1 = 0.99F;
			float f2 = 0.05F;

			if (this.isInWater())
			{
				for (int i = 0; i < 4; ++i)
				{
					float f3 = 0.25F;
					this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
				}

				f1 = 0.6F;
			}

			if (this.isWet())
			{
				this.extinguish();
			}

			this.motionX *= (double)f1;
			this.motionY *= (double)f1;
			this.motionZ *= (double)f1;

			if (!this.hasNoGravity())
			{
				this.motionY -= 0.05000000074505806D;
			}

			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}

	private void onUpdateTippedArrow() throws IllegalAccessException, InvocationTargetException {
		if (this.world.isRemote)
		{
			if (inGround)
			{
				if (this.timeInGround % 5 == 0)
				{
					spawnPotionParticles.invoke(this, 1);
				}
			}
			else
			{
				spawnPotionParticles.invoke(this, 2);
			}
		}
		else if (inGround && this.timeInGround != 0 && !((Set<PotionEffect>)customPotionEffects.get(this)).isEmpty() && this.timeInGround >= 600)
		{
			this.world.setEntityState(this, (byte)0);
			potion.set(this, PotionTypes.EMPTY);
			((Set<PotionEffect>)customPotionEffects.get(this)).clear();
			this.dataManager.set((DataParameter<Integer>)COLOR.get(this), Integer.valueOf(-1));
		}
	}

	private int getTarget() {
		return this.dataManager.get(TARGET);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("target", this.getTarget());
	}
}