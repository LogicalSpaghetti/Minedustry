package me.spaghetti.minedustry.block.abstractions;

import me.spaghetti.minedustry.sounds.ModSounds;
import me.spaghetti.minedustry.util.*;
import me.spaghetti.minedustry.util.Category;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public abstract class NewBlock extends BlockWithEntity implements BlockEntityProvider {
    /** If true, buildings have an ItemModule. */
    public boolean hasItems;
    /** If true, buildings have a LiquidModule. */
    public boolean hasLiquids;
    /** If true, buildings have a PowerModule. */
    public boolean hasPower;
    /** Flag for determining whether this block outputs liquid somewhere; used for connections. */
    public boolean outputsLiquid = false;
    /** Used by certain power blocks (nodes) to flag as non-consuming of power. True by default, even if this block has no power. */
    public boolean consumesPower = true;
    /** If true, this block is a generator that can produce power. */
    public boolean outputsPower = false;
    /** If false, power nodes cannot connect to this block. */
    public boolean connectedPower = true;
    /** If true, this block can conduct power like a cable. */
    public boolean conductivePower = false;
    /** If true, this block can output payloads; affects blending. */
    public boolean outputsPayload = false;
    /** If true, this block can input payloads; affects unit payload enter behavior. */
    public boolean acceptsPayloads = false;
    /** If true, payloads will attempt to move into this block. */
    public boolean acceptsPayload = false;
    /** Visual flag use for blending of certain transportation blocks. */
    public boolean acceptsItems = false;
    /** If true, all item capacities of this block are separate instead of pooled as one number. */
    public boolean separateItemCapacity = false;
    /** maximum items this block can carry (usually, this is per-type of item) */
    public int itemCapacity = 10;
    /** maximum total liquids this block can carry if hasLiquids = true */
    public float liquidCapacity = 10f;
    /** higher numbers increase liquid output speed; */
    public float liquidPressure = 1f;
    /** If true, this block outputs to its facing direction, when applicable.
     * Used for blending calculations. */
    public boolean outputFacing = true;
    /** if true, this block does not accept input from the sides (used for armored conveyors) */
    public boolean noSideBlend = false;
    /** whether to display flow rate */
    public boolean displayFlow = true;
    /** whether this block is visible in the editor */
    public boolean inEditor = true;
    /** the last configuration value applied to this block. */
    public @Nullable Object lastConfig;
    /** whether to save the last config and apply it to newly placed blocks */
    public boolean saveConfig = false;
    /** whether to allow copying the config through middle click */
    public boolean copyConfig = true;
    /** if true, double-tapping this configurable block clears configuration. */
    public boolean clearOnDoubleTap = false;
    /** whether this block has a tile entity that updates */
    public boolean update;
    /** whether this block has health and can be destroyed */
    public boolean destructible;
    /** whether unloaders work on this block */
    public boolean unloadable = true;
    /** if true, this block acts a duct and will connect to armored ducts from the side. */
    public boolean isDuct = false;
    /** whether units can resupply by taking items from this block */
    public boolean allowResupply = false;
    /** whether this is solid */
    public boolean solid;
    /** whether this block CAN be solid. */
    public boolean solidifes;
    /** if true, this counts as a non-solid block to this team. */
    public boolean teamPassable;
    /** if true, this block cannot be hit by bullets unless explicitly targeted. */
    public boolean underBullets;
    /** whether this is rotatable */
    public boolean rotate;
    /** if rotate is true and this is false, the region won't rotate when drawing */
    public boolean rotateDraw = true;
    /** if rotate = false and this is true, rotation will be locked at 0 when placing (default); advanced use only */
    public boolean lockRotation = true;
    /** if true, schematic flips with this block are inverted. */
    public boolean invertFlip = false;
    /** number of different variant regions to use */
    public int variants = 0;
    /** whether to draw a rotation arrow - this does not apply to lines of blocks */
    public boolean drawArrow = true;
    /** whether to draw the team corner by default */
    public boolean drawTeamOverlay = true;
    /** for static blocks only: if true, tile data() is saved in world data. */
    public boolean saveData;
    /** whether you can break this with rightclick */
    public boolean breakable;
    /** whether to add this block to brokenblocks */
    public boolean rebuildable = true;
    /** if true, this logic-related block can only be used with privileged processors (or is one itself) */
    public boolean privileged = false;
    /** whether this block can only be placed on water */
    public boolean requiresWater = false;
    /** whether this block can be placed on any liquids, anywhere */
    public boolean placeableLiquid = false;
    /** whether this block can be placed directly by the player via PlacementFragment */
    public boolean placeablePlayer = true;
    /** whether this floor can be placed on. */
    public boolean placeableOn = true;
    /** whether this block has insulating properties. */
    public boolean insulated = false;
    /** whether the sprite is a full square. */
    public boolean squareSprite = true;
    /** whether this block absorbs laser attacks. */
    public boolean absorbLasers = false;
    /** if false, the status is never drawn */
    public boolean enableDrawStatus = true;
    /** whether to draw disabled status */
    public boolean drawDisabled = true;
    /** whether to automatically reset enabled status after a logic block has not interacted for a while. */
    public boolean autoResetEnabled = true;
    /** if true, the block stops updating when disabled */
    public boolean noUpdateDisabled = false;
    /** if true, this block updates when it's a payload in a unit. */
    public boolean updateInUnits = true;
    /** if true, this block updates in payloads in units regardless of the experimental game rule */
    public boolean alwaysUpdateInUnits = false;
    /** if false, only incinerable liquids are dropped when deconstructing; otherwise, all liquids are dropped. */
    public boolean deconstructDropAllLiquid = false;
    /** Whether to use this block's color in the minimap. Only used for overlays. */
    public boolean useColor = true;
    /** item that drops from this block, used for drills */
    public @Nullable Item itemDrop = null;
    /** if true, this block cannot be mined by players. useful for annoying things like sand. */
    public boolean playerUnmineable = false;
    /** Array of affinities to certain things. */
    public Affinities attributes = new Affinities();
    /** Health per square tile that this block occupies; essentially, this is multiplied by size * size. Overridden if health is > 0. If <0, the default is 40. */
    public float scaledHealth = -1;
    /** building health; -1 to use scaledHealth */
    public int health = -1;
    /** damage absorption, similar to unit armor */
    public float armor = 0f;
    /** base block explosiveness */
    public float baseExplosiveness = 0f;
    /** bullet that this block spawns when destroyed */
    public @Nullable BulletType destroyBullet = null;
    /** if true, destroyBullet is spawned on the block's team instead of Derelict team */
    public boolean destroyBulletSameTeam = false;
    /** liquid used for lighting */
    public @Nullable Fluid lightLiquid;
    /** whether cracks are drawn when this block is damaged */
    public boolean drawCracks = true;
    /** whether rubble is created when this block is destroyed */
    public boolean createRubble = true;
    /** whether this block can be placed on edges of liquids. */
    public boolean floating = false;
    /** multiblock size */
    public int size = 1;
    /** multiblock offset */
    public float offset = 0f;
    /** offset for iteration (internal use only) */
    public int sizeOffset = 0;
    /** Clipping size of this block. Should be as large as the block will draw. */
    public float clipSize = -1f;
    /** When placeRangeCheck is enabled, this is the range checked for enemy blocks. */
    public float placeOverlapRange = 50f;
    /** Multiplier of damage dealt to this block by tanks. Does not apply to crawlers. */
    public float crushDamageMultiplier = 1f;
    /** Max of timers used. */
    public int timers = 0;
    /** Special flag; if false, floor will be drawn under this block even if it is cached. */
    public boolean fillsTile = true;
    /** If true, this block can be covered by darkness / fog even if synthetic. */
    public boolean forceDark = false;
    /** whether this block can be replaced in all cases */
    public boolean alwaysReplace = false;
    /** if false, this block can never be replaced. */
    public boolean replaceable = true;
    /** The block group. Unless {@link #canReplace} is overridden, blocks in the same group can replace each other. */
    public BlockGroup group = BlockGroup.none;
/*    *//** List of block flags. Used for AI indexing. *//*
    public EnumSet<BlockFlag> flags = EnumSet.of();*/
    /** Targeting priority of this block, as seen by enemies. */
    public float priority = TargetPriority.base;
    /** How much this block affects the unit cap by.
     * The block flags must contain unitModifier in order for this to work. */
    public int unitCapModifier = 0;
    /** Whether the block can be tapped and selected to configure. */
    public boolean configurable;
    /** If true, this block does not have pointConfig with a transform called on map resize. */
    public boolean ignoreResizeConfig;
    /** If true, this building can be selected like a unit when commanding. */
    public boolean commandable;
    /** If true, the building inventory can be shown with the config. */
    public boolean allowConfigInventory = true;
    /** Defines how large selection menus, such as that of sorters, should be. */
    public int selectionRows = 5, selectionColumns = 4;
    /** If true, this block can be configured by logic. */
    public boolean logicConfigurable = false;
    /** Whether this block consumes touchDown events when tapped. */
    public boolean consumesTap;
    /** Whether to draw the glow of the liquid for this block, if it has one. */
    public boolean drawLiquidLight = true;
    /** Environmental flags that are *all* required for this block to function. 0 = any environment */
    public int envRequired = 0;
    /** The environment flags that this block can function in. If the env matches any of these, it will be enabled. */
    public int envEnabled = Env.terrestrial;
    /** The environment flags that this block *cannot* function in. If the env matches any of these, it will be *disabled*. */
    public int envDisabled = 0;
    /** Whether to periodically sync this block across the network. */
    public boolean sync;
    /** Whether this block uses conveyor-type placement mode. */
    public boolean conveyorPlacement;
    /** If false, diagonal placement (ctrl) for this block is not allowed. */
    public boolean allowDiagonal = true;
    /** Whether to swap the diagonal placement modes. */
    public boolean swapDiagonalPlacement;
    /** Whether to allow rectangular placement, as opposed to a line. */
    public boolean allowRectanglePlacement = false;
    /** Build queue priority in schematics. */
    public int schematicPriority = 0;
    /**
     * The color of this block when displayed on the minimap or map preview.
     * Do not set manually! This is overridden when loading for most blocks.
     */
    public Color mapColor = new Color(0, 0, 0, 1);
    /** Whether this block has a minimap color. */
    public boolean hasColor = false;
    /** Whether units target this block. */
    public boolean targetable = true;
    /** If true, this block attacks and is considered a turret in the indexer. Building must implement Ranged. */
    public boolean attacks = false;
    /** If true, this block is mending-related and can be suppressed with special units/missiles. */
    public boolean suppressable = false;
    /** Whether the overdrive core has any effect on this block. */
    public boolean canOverdrive = true;
    /** Outlined icon color.*/
    public Color outlineColor = Color.decode("404049");
    /** Whether any icon region has an outline added. */
    public boolean outlineIcon = false;
    /** Outline icon radius. */
    public int outlineRadius = 4;
    /** Which of the icon regions gets the outline added. Uses last icon if <= 0. */
    public int outlinedIcon = -1;
    /** Whether this block has a shadow under it. */
    public boolean hasShadow = true;
    /** If true, a custom shadow (name-shadow) is drawn under this block. */
    public boolean customShadow = false;
    /** Should the sound made when this block is built change in pitch. */
    public boolean placePitchChange = true;
    /** Should the sound made when this block is deconstructed change in pitch. */
    public boolean breakPitchChange = true;
    /** Sound made when this block is built. */
    public SoundEvent placeSound = ModSounds.place;
    /** Sound made when this block is deconstructed. */
    public SoundEvent breakSound = ModSounds.breaks;
    /** Sounds made when this block is destroyed.*/
    public SoundEvent destroySound = ModSounds.boom;
    /** How reflective this block is. */
    public float albedo = 0f;
    /** Environmental passive light color. */
    public Color lightColor = Color.white;
    /**
     * Whether this environmental block passively emits light.
     * Does not change behavior for non-environmental blocks, but still updates clipSize. */
    public boolean emitLight = false;
    /** Radius of the light emitted by this block. */
    public float lightRadius = 60f;

    /** How much fog this block uncovers, in tiles. Cannot be dynamic. <= 0 to disable. */
    public int fogRadius = -1;

    /** The sound that this block makes while active. One sound loop. Do not overuse. */
    public SoundEvent loopSound = null;
    /** Active sound base volume. */
    public float loopSoundVolume = 0.5f;

    /** The sound that this block makes while idle. Uses one sound loop for all blocks. */
    public SoundEvent ambientSound = ModSounds.none;
    /** Idle sound base volume. */
    public float ambientSoundVolume = 0.05f;

    /** Cost of constructing this block. */
    public ItemStack[] requirements = {};
    /** Category in place menu. */
    public Category category = Category.distribution;
    /** Time to build this block in ticks; do not modify directly! */
    public float buildCost = 20f;
/*    *//** Whether this block is visible and can currently be built. *//*
    public BuildVisibility buildVisibility = BuildVisibility.hidden;*/
    /** Multiplier for speed of building this block. */
    public float buildCostMultiplier = 1f;
    /** Build completion at which deconstruction finishes. */
    public float deconstructThreshold = 0f;
    /** If true, this block deconstructs immediately. Instant deconstruction implies no resource refund. */
    public boolean instantDeconstruct = false;
    /** If true, this block constructs immediately. This implies no resource requirement, and ignores configs - do not use, this is for performance only! */
    public boolean instantBuild = false;
    /** Effect for placing the block. Passes size as rotation. */
/*    public Effect placeEffect = Fx.placeBlock;
    *//** Effect for breaking the block. Passes size as rotation. *//*
    public Effect breakEffect = Fx.breakBlock;
    *//** Effect for destroying the block. *//*
    public Effect destroyEffect = Fx.dynamicExplosion;*/
    /** Multiplier for cost of research in tech tree. */
    public float researchCostMultiplier = 1;
    /** Cost multipliers per-item. */
    /*public ObjectFloatMap<Item> researchCostMultipliers = new ObjectFloatMap<>();*/
    /** Override for research cost. Uses multipliers above and building requirements if not set. */
    public @Nullable ItemStack[] researchCost;
    /** Whether this block has instant transfer.*/
    public boolean instantTransfer = false;
    /** Whether you can rotate this block after it is placed. */
    public boolean quickRotate = true;
    /** If true, this derelict block can be repair by clicking it. */
    public boolean allowDerelictRepair = true;
    /** Main subclass. Non-anonymous. */
    public @Nullable Class<?> subclass;
    /** Scroll position for certain blocks. */
    public float selectScroll;
    /** Building that is created for this block. Initialized in init() via reflection. Set manually if modded. */
    /*public Prov<Building> buildType = null;*/
    /** Configuration handlers by type. */
    /*public ObjectMap<Class<?>, Cons2> configurations = new ObjectMap<>();*/
    /** Consumption filters. */
    public boolean[] itemFilter = {}, liquidFilter = {};
/*    *//** Array of consumers used by this block. Only populated after init(). *//*
    public Consume[] consumers = {}, optionalConsumers = {}, nonOptionalConsumers = {}, updateConsumers = {};
    *//** Set to true if this block has any consumers in its array. *//*
    public boolean hasConsumers;
    *//** The single power consumer, if applicable. *//*
    public @Nullable ConsumePower consPower;*/


    protected NewBlock(Settings settings) {
        super(settings);
    }
}
