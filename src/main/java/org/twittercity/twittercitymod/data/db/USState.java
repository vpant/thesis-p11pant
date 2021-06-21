package org.twittercity.twittercitymod.data.db;

import com.sun.istack.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usa_state")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class USState {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "state_name")
    private String stateName;
    @Column(name = "state_abbr")
    private String stateAbbr;

    public USState(NBTTagCompound nbt) {
        id = nbt.getInteger("id");
        stateName = nbt.getString("stateName");
        stateAbbr = nbt.getString("stateAbbr");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("id", id);
        nbt.setString("stateName", stateName);
        nbt.setString("stateAbbr", stateAbbr);

        return nbt;
    }

    @Override
    public String toString() {

        return "";
    }
}
