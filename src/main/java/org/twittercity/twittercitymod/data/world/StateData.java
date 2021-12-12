package org.twittercity.twittercitymod.data.world;

import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.data.db.USStateDAO;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class StateData extends WorldSavedData {

    private static final String DATA_NAME = Reference.MOD_ID + "_StateData";
    private static StateData instance;

    private List<Integer> totalStateIds = new ArrayList<>();
    private final Map<Integer, Integer> stateIdByLatestTweetId = new HashMap<>();
    private Integer currentStateId = 1;

    public StateData() {
        super(DATA_NAME);
    }

    public StateData(String name) {
        super(name);
    }

    public static StateData get(World world) {
        MapStorage storage = world.getMapStorage();
        instance = (StateData) storage.getOrLoadData(StateData.class, DATA_NAME);

        if (instance == null) {
            instance = new StateData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        int[] stateIdsArray = nbt.getIntArray("stateIds");
        totalStateIds = Arrays.stream(stateIdsArray).boxed().collect(Collectors.toList());

        for(Integer stateId : totalStateIds) {
            stateIdByLatestTweetId.put(stateId, nbt.getInteger("stateId-" + stateId));
        }

        currentStateId = nbt.getInteger("currentStateId");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        int[] stateIdsArray = stateIdByLatestTweetId.keySet().stream().mapToInt(i -> i).toArray();
        compound.setIntArray("stateIds", stateIdsArray);
        compound.setInteger("currentStateId", currentStateId);

        for(Integer stateId : stateIdsArray) {
            compound.setInteger("stateId-" + stateId, getLatestTweetIdForStateId(stateId));
        }

        return compound;
    }

    public int getLatestTweetIdForStateId(final int stateId) {
        return stateIdByLatestTweetId.computeIfAbsent(stateId, key -> 0);
    }

    public void setLatestTweetIdForStateId(final int stateId, final int tweetId) {
        stateIdByLatestTweetId.put(stateId, tweetId);
        markDirty();
    }

    public int getNextStateId(int currentStateId) {
        final int lastStateId = USStateDAO.getInstance().getLastStateId();
        final int nextStateId = currentStateId + 1;

        final int validNextStateId = lastStateId >= nextStateId ? nextStateId : 1;
        markDirty();

        return validNextStateId;
    }
}
