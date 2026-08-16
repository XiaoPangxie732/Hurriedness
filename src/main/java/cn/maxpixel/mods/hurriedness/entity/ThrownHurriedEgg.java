package cn.maxpixel.mods.hurriedness.entity;

import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValueHelper;
import cn.maxpixel.mods.hurriedness.registry.EntityTypeRegistry;
import cn.maxpixel.mods.hurriedness.registry.ItemRegistry;
import cn.maxpixel.mods.hurriedness.util.HurriednessUtil;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

public class ThrownHurriedEgg extends ThrowableItemProjectile {
    public ThrownHurriedEgg(EntityType<? extends ThrownHurriedEgg> type, Level level) {
        super(type, level);
    }

    public ThrownHurriedEgg(double x, double y, double z, Level level, ItemStack itemStack) {
        super(EntityTypeRegistry.HURRIED_EGG.get(), x, y, z, level, itemStack);
    }

    public ThrownHurriedEgg(Level level, LivingEntity owner, ItemStack itemStack) {
        super(EntityTypeRegistry.HURRIED_EGG.get(), owner, level, itemStack);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EntityEvent.DEATH) {
            ItemStack item = getItem();
            if (!item.isEmpty()) {
                ItemParticleOption breakParticle = new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item));
                for (int i = 0; i < 8; i++) {
                    level().addParticle(
                            breakParticle, getX(), getY(), getZ(),
                            (random.nextFloat() - 0.5) * 0.08,
                            (random.nextFloat() - 0.5) * 0.08,
                            (random.nextFloat() - 0.5) * 0.08
                    );
                }
            }
        }
    }

    @Override
    protected void onHit(@NonNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!level().isClientSide()) {
            level().broadcastEntityEvent(this, EntityEvent.DEATH);
            discard();
        }
    }

    @Override
    protected void onHitEntity(@NonNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        var e = hitResult.getEntity();
        var owner = getOwner();
        e.hurt(damageSources().thrown(this, owner), 0.0F);
        if (e instanceof ServerPlayer sp) {
            sp.addEffect(new MobEffectInstance(MobEffects.SPEED, 100, 1, false, false, false), owner);
            if (sp != owner) sp.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 100, 1, false, false, false), owner);
            if (owner != null) HurriednessUtil.sendMessage1(sp, owner);
            HurriednessValueHelper.increase(sp.level(), sp);
        } else if (e instanceof Chicken c) {
            if (c.level() instanceof ServerLevel level) {
                c.spawnAtLocation(level, ItemRegistry.HURRIED_EGG);
                HurriednessValueHelper.increase(level, c);
            }
        }
    }

    @Override
    protected void onHitBlock(@NonNull BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (level() instanceof ServerLevel level) {
            HurriednessUtil.rangeHurryBlocks(hitResult.getBlockPos(), level, getOwner());
        }
    }

    @Override
    protected @NonNull Item getDefaultItem() {
        return ItemRegistry.HURRIED_EGG.get();
    }
}
