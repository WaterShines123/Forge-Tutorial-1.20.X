package net.watershines.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class SoundBlock extends Block {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private int soundCounter = 0;
    public SoundBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        Random random = new Random();
        int i = random.nextInt(5);
        float pitch = random.nextFloat(10f);
        if(i == 0) {
            pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_DIDGERIDOO.get(), SoundSource.BLOCKS, 1f, pitch);
        } else if (i == 1) {
            pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_COW_BELL.get(), SoundSource.BLOCKS, 1f, pitch);
        } else if (i == 2) {
            pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_FLUTE.get(), SoundSource.BLOCKS, 1f, pitch);
        } else if (i == 3) {
            pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_GUITAR.get(), SoundSource.BLOCKS, 1f, pitch);
        } else {
            pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_BASEDRUM.get(), SoundSource.BLOCKS, 1f, pitch);
        }
        return InteractionResult.SUCCESS;
    }



    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (flag != pState.getValue(POWERED)) {
            if (flag) {
                Random random = new Random();
                soundCounter++;
                float pitch = random.nextFloat(10f);

                switch (soundCounter) {
                    case 1:
                        pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_DIDGERIDOO.get(), SoundSource.BLOCKS, 1f, pitch);
                        break;
                    case 2:
                        pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_COW_BELL.get(), SoundSource.BLOCKS, 1f, pitch);
                        break;
                    case 3:
                        pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_FLUTE.get(), SoundSource.BLOCKS, 1f, pitch);
                        break;
                    case 4:
                        pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_GUITAR.get(), SoundSource.BLOCKS, 1f, pitch);
                        break;
                    case 5:
                        pLevel.playSound((Entity) null, pPos, SoundEvents.NOTE_BLOCK_BASEDRUM.get(), SoundSource.BLOCKS, 1f, pitch);
                        soundCounter = 0;  // Reset counter after last sound
                        break;
                }
            }
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(flag)), 1);
        }

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Plays sounds when right clicked or powered"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
