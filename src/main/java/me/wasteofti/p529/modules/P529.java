package me.wasteofti.p529.modules;

import me.wasteofti.p529.Addon;
import meteordevelopment.meteorclient.events.world.AmbientOcclusionEvent;
import meteordevelopment.meteorclient.events.world.ChunkOcclusionEvent;
import meteordevelopment.meteorclient.settings.BlockListSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.List;

public class P529 extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<Block>> blocks = sgGeneral.add(new BlockListSetting.Builder()
        .name("blacklist")
        .description("Which blocks to not display")
        .defaultValue(Blocks.GLASS_PANE
            , Blocks.WHITE_STAINED_GLASS_PANE
            , Blocks.ORANGE_STAINED_GLASS_PANE
            , Blocks.MAGENTA_STAINED_GLASS_PANE
            , Blocks.LIGHT_BLUE_STAINED_GLASS_PANE
            , Blocks.YELLOW_STAINED_GLASS_PANE
            , Blocks.LIME_STAINED_GLASS_PANE
            , Blocks.PINK_STAINED_GLASS_PANE
            , Blocks.GRAY_STAINED_GLASS_PANE
            , Blocks.LIGHT_GRAY_STAINED_GLASS_PANE
            , Blocks.CYAN_STAINED_GLASS_PANE
            , Blocks.PURPLE_STAINED_GLASS_PANE
            , Blocks.BLUE_STAINED_GLASS_PANE
            , Blocks.BROWN_STAINED_GLASS_PANE
            , Blocks.GREEN_STAINED_GLASS_PANE
            , Blocks.RED_STAINED_GLASS_PANE
            , Blocks.BLACK_STAINED_GLASS_PANE
            , Blocks.GLASS
            , Blocks.WHITE_STAINED_GLASS
            , Blocks.ORANGE_STAINED_GLASS
            , Blocks.MAGENTA_STAINED_GLASS
            , Blocks.LIGHT_BLUE_STAINED_GLASS
            , Blocks.YELLOW_STAINED_GLASS
            , Blocks.LIME_STAINED_GLASS
            , Blocks.PINK_STAINED_GLASS
            , Blocks.GRAY_STAINED_GLASS
            , Blocks.LIGHT_GRAY_STAINED_GLASS
            , Blocks.CYAN_STAINED_GLASS
            , Blocks.PURPLE_STAINED_GLASS
            , Blocks.BLUE_STAINED_GLASS
            , Blocks.BROWN_STAINED_GLASS
            , Blocks.GREEN_STAINED_GLASS
            , Blocks.RED_STAINED_GLASS
            , Blocks.BLACK_STAINED_GLASS
            , Blocks.IRON_BARS)
        .onChanged(v -> {
            if (isActive()) mc.worldRenderer.reload();
        }).build());

        private final Setting<Integer> yLevelUpperClamp = sgGeneral.add(new IntSetting.Builder()
            .name("y-level upper limit")
            .description("Upper limit in which not to render blocks")
            .defaultValue(319)
            .min(-64)
            .max(319)
            .sliderRange(-64, 319)
            .build()
        );

    private final Setting<Integer> yLevelLowerClamp = sgGeneral.add(new IntSetting.Builder()
        .name("y-level lower limit")
        .description("Lower limit in which not to render blocks")
        .defaultValue(318)
        .min(-64)
        .max(319)
        .sliderRange(-64, 319)
        .build()
    );
    public P529() {
        super(Addon.CATEGORY, "P529", "An example module in a custom category.");
    }

    @Override
    public void onActivate() {
        mc.worldRenderer.reload();
    }

    @Override
    public void onDeactivate() {
        mc.worldRenderer.reload();
    }

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent event) {
        event.cancel();
    }

    @EventHandler
    private void onAmbientOcclusion(AmbientOcclusionEvent event) {
        event.lightLevel = 1;
    }

    public boolean modifyDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing, boolean returns) {
        if (!returns && pos.getY() >=  yLevelLowerClamp.get() && pos.getY() <= yLevelLowerClamp.get() && !isBlocked(state.getBlock(), pos)) {
            BlockPos adjPos = pos.offset(facing);
            BlockState adjState = view.getBlockState(adjPos);
            return adjState.getCullingFace(view, adjPos, facing.getOpposite()) != VoxelShapes.fullCube() || adjState.getBlock() != state.getBlock();
        }

        return returns;
    }

    public boolean isBlocked(Block block, BlockPos blockPos) {
        return !(blocks.get().contains(block) && ((blockPos == null || BlockUtils.isExposed(blockPos))));
    }


}
