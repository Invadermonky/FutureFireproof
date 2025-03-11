package com.invadermonky.futurefireproof.entity;

import com.invadermonky.futurefireproof.config.ConfigHandlerFF;
import com.invadermonky.futurefireproof.config.ModTags;
import com.invadermonky.futurefireproof.registry.RegistrarFF;
import com.invadermonky.futurefireproof.util.FireproofHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import realdrops.core.RID_Settings;
import realdrops.entities.EntityItemLoot;

import javax.annotation.Nullable;

public class EntityFireproofItemLootHandler {
    @Nullable
    public static EntityItem getEntityItem(World world) {
        if(RegistrarFF.isRealDropsLoaded) {
            try {
                return getEntityFireproofLootItemClass().getConstructor(world.getClass()).newInstance(world);
            } catch (Exception ignored) {}
        }
        return null;
    }

    @Nullable
    public static EntityItem getEntityItem(World world, double x, double y, double z, ItemStack stack) {
        if(RegistrarFF.isRealDropsLoaded) {
            try {
                return getEntityFireproofLootItemClass().getConstructor(world.getClass(), double.class, double.class, double.class, stack.getClass()).newInstance(world, x, y, z, stack);
            } catch (Exception ignored) {}
        }
        return null;
    }

    @Nullable
    public static EntityItem getEntityItem(EntityItem entityItem) {
        if(RegistrarFF.isRealDropsLoaded) {
            try {
                return getEntityFireproofLootItemClass().getConstructor(entityItem.getClass()).newInstance(entityItem);
            } catch (Exception ignored) {}
        }
        return null;
    }

    @Nullable
    public static Class<? extends EntityItem> getEntityFireproofLootItemClass() {
        class EntityFireproofItemLoot extends EntityItemLoot {
            private int lavaDecay;

            public EntityFireproofItemLoot(World world, double x, double y, double z, ItemStack stack) {
                super(world, x, y, z, stack);
                this.isImmuneToFire = true;
                this.lavaDecay = 0;
            }

            public EntityFireproofItemLoot(World world) {
                super(world);
                this.isImmuneToFire = true;
                this.lavaDecay = 0;
            }

            public EntityFireproofItemLoot(EntityItem orig) {
                super(orig);
                this.isImmuneToFire = true;
                this.lavaDecay = 0;
            }

            @Override
            public void onUpdate() {
                if(this.getItem().getItem().onEntityItemUpdate(this)) return;
                if(this.getItem().isEmpty()) {
                    this.setDead();
                } else {
                    if(!this.world.isRemote) {
                        this.setFlag(6, this.isGlowing());
                    }

                    this.onEntityUpdate();

                    if(this.pickupDelay > 0 && this.pickupDelay != (int) Short.MAX_VALUE) {
                        --this.pickupDelay;
                    }

                    this.prevPosX = this.posX;
                    this.prevPosY = this.posY;
                    this.prevPosZ = this.posZ;
                    double d0 = this.motionX;
                    double d1 = this.motionY;
                    double d2 = this.motionZ;

                    boolean inLava = this.isInsideOfMaterial(Material.LAVA);
                    boolean inFire = this.isInsideOfMaterial(Material.FIRE);
                    boolean inWater = this.isInsideOfMaterial(Material.WATER) || this.world.getBlockState(this.getPosition().up()).getMaterial() == Material.WATER;
                    ItemStack stack = this.getItem();

                    if(inLava) {
                        this.floatInLava();
                    } else if(RID_Settings.canFloat && inWater) {
                        this.floatInWater();
                    } else if(!this.hasNoGravity()) {
                        this.motionY -= 0.04;
                    }

                    if(((!inLava && this.onGround) && !inFire) || inWater) {
                        this.lavaDecay = 0;
                    }

                    if(this.world.isRemote) {
                        this.noClip = false;
                    } else {
                        this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
                    }

                    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                    boolean flag = (int) this.prevPosX != (int) this.posX || (int) this.prevPosY != (int) this.posY || (int) this.prevPosZ != (int) this.posZ;

                    if(flag || this.ticksExisted % 25 == 0) {
                        if(!this.world.isRemote) {
                            this.searchForOtherItemsNearby();
                        }
                    }

                    float f = 0.98F;

                    if(this.onGround) {
                        BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
                        IBlockState underState = this.world.getBlockState(underPos);
                        f *= underState.getBlock().getSlipperiness(underState, this.world, underPos, this);
                    }

                    this.motionX *= f;
                    this.motionY *= 0.98;
                    this.motionZ *= f;

                    if(this.onGround) {
                        this.motionY *= -0.5;
                    }

                    if(this.age != (int) Short.MIN_VALUE) {
                        ++this.age;
                        this.lavaDecay += FireproofHelper.getLavaDecayRate(stack);
                    }

                    this.handleWaterMovement();
                    this.handleLavaMovement();

                    if(!this.world.isRemote) {
                        double d3 = this.motionX - d0;
                        double d4 = this.motionY - d1;
                        double d5 = this.motionZ - d2;
                        double d6 = d3 * d3 + d4 * d4 + d5 * d5;

                        if(d6 > 0.01) {
                            this.isAirBorne = true;
                        }
                    }

                    if(!this.world.isRemote && (this.age >= lifespan || this.lavaDecay >= lifespan)) {
                        int hook = ForgeEventFactory.onItemExpire(this, stack);
                        if(hook < 0) {
                            if(inFire || inLava) {
                                this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
                            }
                            this.setDead();
                        } else {
                            this.lifespan += hook;
                        }
                    }

                    if(stack.isEmpty()) {
                        this.setDead();
                    }
                }
            }

            protected boolean handleLavaMovement() {
                if(this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.LAVA, this)) {
                    this.motionX *= 0.75f;
                    this.motionZ *= 0.75f;
                }
                return this.isInsideOfMaterial(Material.LAVA);
            }

            @Override
            public boolean attackEntityFrom(DamageSource source, float amount) {
                if(this.world.isRemote || this.isDead) return false;
                if(this.isEntityInvulnerable(source)) {
                    return false;
                } else if(!this.getItem().isEmpty()) {
                    if(source.isExplosion() && (this.getItem().getItem() == Items.NETHER_STAR || ConfigHandlerFF.explosionImmunity)) {
                        return false;
                    } else if(source.isFireDamage()) {
                        return false;
                    } if(ModTags.isIgnoredDamageType(source)) {
                        return false;
                    }
                }
                this.markVelocityChanged();
                this.health = (int)((float) this.health - amount);
                if(this.health <= 0) {
                    this.setDead();
                }
                return false;
            }

            @Override
            public void searchForOtherItemsNearby() {
                for(EntityFireproofItemLoot fpItem : this.world.getEntitiesWithinAABB(EntityFireproofItemLoot.class, this.getEntityBoundingBox().grow(0.5, 0, 0.5))) {
                    this.combineItems(fpItem);
                }
            }

            protected boolean combineItems(EntityFireproofItemLoot other) {
                if (other == this) {
                    return false;
                } else if (other.isEntityAlive() && this.isEntityAlive()) {
                    ItemStack itemstack = this.getItem();
                    ItemStack itemstack1 = other.getItem();
                    if (this.pickupDelay != 32767 && other.pickupDelay != 32767) {
                        if (this.age != -32768 && other.age != -32768) {
                            if (itemstack1.getItem() != itemstack.getItem()) {
                                return false;
                            } else if (itemstack1.hasTagCompound() ^ itemstack.hasTagCompound()) {
                                return false;
                            } else if (itemstack1.hasTagCompound() && !itemstack1.getTagCompound().equals(itemstack.getTagCompound())) {
                                return false;
                            } else if (itemstack1.getItem() == null) {
                                return false;
                            } else if (itemstack1.getItem().getHasSubtypes() && itemstack1.getMetadata() != itemstack.getMetadata()) {
                                return false;
                            } else if (itemstack1.getCount() < itemstack.getCount()) {
                                return other.combineItems(this);
                            } else if (itemstack1.getCount() + itemstack.getCount() > itemstack1.getMaxStackSize()) {
                                return false;
                            } else if (!itemstack.areCapsCompatible(itemstack1)) {
                                return false;
                            } else {
                                itemstack1.grow(itemstack.getCount());
                                other.pickupDelay = Math.max(other.pickupDelay, this.pickupDelay);
                                other.age = Math.min(other.age, this.age);
                                other.lavaDecay = Math.min(other.lavaDecay, this.lavaDecay);
                                other.setItem(itemstack1);
                                this.setDead();
                                return true;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            protected void floatInWater() {
                this.motionX = MathHelper.clamp(this.motionX, -0.05, 0.05);
                this.motionY += this.motionY < 0.05 && (world.getBlockState(this.getPosition().up()).getMaterial() == Material.WATER || this.posY % 1.0 < 0.8999999761581421) ? Math.min(0.075D, 0.075D - this.motionY) : 0.0;
                this.motionZ = MathHelper.clamp(this.motionZ, -0.05, 0.05);
            }

            protected void floatInLava() {
                this.motionX *= 0.95;
                this.motionY += this.motionY < 0.06 ? Math.min(0.075D, 0.075D - this.motionY) : 0.0;
                this.motionZ *= 0.95;
            }
        }

        return RegistrarFF.isRealDropsLoaded ? EntityFireproofItemLoot.class : null;
    }
}
