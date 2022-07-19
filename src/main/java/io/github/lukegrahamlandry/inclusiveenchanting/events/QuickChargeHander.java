package io.github.lukegrahamlandry.inclusiveenchanting.events;


import io.github.lukegrahamlandry.inclusiveenchanting.InclusiveEnchanting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Item;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class QuickChargeHander {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBus {
        @SubscribeEvent
        public static void changeArrowSpeed(ArrowLooseEvent event) {
            int quick = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, event.getBow());
            if (quick > 0) {
                event.setCharge(event.getCharge() + (quick * 5));
            }
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
    public static class ClientBus {
        @SubscribeEvent
        public static void changeDrawRendering(RegistryEvent.Register<Item> event) {
            ItemProperties.register(Items.BOW, new ResourceLocation("pull"), (stack, world, entity) -> {
                if (entity == null || entity.getUseItem() != stack) return 0.0F;
                int quick = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
                float time = (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks());
                time = time + (quick * 5);
                return time / 20.0F;
            });
        }
    }
}

