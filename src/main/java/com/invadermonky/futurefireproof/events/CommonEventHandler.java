package com.invadermonky.futurefireproof.events;

import com.invadermonky.futurefireproof.FutureFireproof;
import com.invadermonky.futurefireproof.entity.EntityFireproofItem;
import com.invadermonky.futurefireproof.entity.EntityFireproofItemLootHandler;
import com.invadermonky.futurefireproof.registry.RegistrarFF;
import com.invadermonky.futurefireproof.util.FireproofHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = FutureFireproof.MOD_ID)
public class CommonEventHandler {
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        if (RegistrarFF.isRealDropsLoaded && (event.getEntity().getClass() == EntityItem.class || event.getEntity().getClass().toString().equals("realdrops.entities.EntityItemLoot"))) {
            EntityItem entityItem = (EntityItem) event.getEntity();
            ItemStack stack = entityItem.getItem();
            if (!stack.isEmpty() && FireproofHelper.isFireproofItem(stack)) {
                EntityItem fireproofItem = EntityFireproofItemLootHandler.getEntityItem(entityItem);
                if (fireproofItem != null) {
                    event.getWorld().spawnEntity(fireproofItem);
                    entityItem.setDead();
                    return;
                }
            }
        }
        if (event.getEntity().getClass() == EntityItem.class) {
            EntityItem entityItem = (EntityItem) event.getEntity();
            ItemStack stack = entityItem.getItem();
            if (!stack.isEmpty() && FireproofHelper.isFireproofItem(stack)) {
                EntityFireproofItem fireproofItem = new EntityFireproofItem(world, entityItem, stack);
                event.getWorld().spawnEntity(fireproofItem);
                entityItem.setDead();
            }
        }
    }
}
