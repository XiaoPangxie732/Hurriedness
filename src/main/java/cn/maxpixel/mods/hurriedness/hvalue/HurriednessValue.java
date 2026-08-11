package cn.maxpixel.mods.hurriedness.hvalue;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

import java.util.Arrays;

public class HurriednessValue {
    public static final int MAX_VALUE = 100;
    public static final int SECTION_SIZE = SectionPos.SECTION_SIZE * SectionPos.SECTION_SIZE * SectionPos.SECTION_SIZE;
    private static final byte[] EMPTY = new byte[HurriednessValue.SECTION_SIZE];

    private final short sectionCount;
    private final byte[][] sections;
    private final short[] cnts;
    private final boolean[] needSync;
    private int cntTotal;
    private boolean needSyncTotal;

    public HurriednessValue(short sectionCount) {
        this.sectionCount = sectionCount;
        this.sections = new byte[sectionCount][];
        Arrays.fill(sections, EMPTY);
        this.cnts = new short[sectionCount];
        this.needSync = new boolean[sectionCount];
    }

    public static HurriednessValue of(IAttachmentHolder holder) {
        if (!(holder instanceof ChunkAccess chunk))
            throw new IllegalArgumentException("HurriednessValue should only be attached to chunks");
        return new HurriednessValue((short) chunk.getSectionsCount());
    }

    public short getSectionCount() {
        return sectionCount;
    }

    public byte[] getSection(int i) {
        return sections[i];
    }

    public byte getValue(int sectionIndex, int sectionX, int sectionY, int sectionZ) {
        return sections[sectionIndex][(sectionY * SectionPos.SECTION_SIZE + sectionX) * SectionPos.SECTION_SIZE + sectionZ];// Y X Z
    }

    public void setValue(int sectionIndex, int sectionX, int sectionY, int sectionZ, byte value) {
        if (value < 0) throw new IllegalArgumentException("Hurriedness value should be non-negative");
        if (sections[sectionIndex] == EMPTY) sections[sectionIndex] = new byte[SECTION_SIZE];
        byte[] section = sections[sectionIndex];
        int i = (sectionY * SectionPos.SECTION_SIZE + sectionX) * SectionPos.SECTION_SIZE + sectionZ;
        if (section[i] == 0) {
            if (value > 0) {
                cnts[sectionIndex]++;
                cntTotal++;
                section[i] = value;
                needSync[sectionIndex] = true;
                needSyncTotal = true;
            }
        } else {
            if (value == 0) {
                cntTotal--;
                if (--cnts[sectionIndex] == 0) {
                    sections[sectionIndex] = EMPTY;
                }
            }
            section[i] = value;
            needSync[sectionIndex] = true;
            needSyncTotal = true;
        }
    }

    public byte[][] getSections() {
        return sections;
    }

    public short getCnt(int i) {
        return cnts[i];
    }

    public boolean needSync() {
        return needSyncTotal;
    }

    public boolean needSync(int i) {
        return needSync[i];
    }

    public void synced() {
        this.needSyncTotal = false;
        Arrays.fill(needSync, false);
    }

    public int getCntTotal() {
        return cntTotal;
    }
}