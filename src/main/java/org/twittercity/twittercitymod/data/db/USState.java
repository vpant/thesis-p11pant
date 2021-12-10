package org.twittercity.twittercitymod.data.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NBTTagCompound;

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
        id = nbt.getInteger("stateId");
        stateName = nbt.getString("stateName");
        stateAbbr = nbt.getString("stateAbbr");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("stateId", id);
        nbt.setString("stateName", stateName);
        nbt.setString("stateAbbr", stateAbbr);

        return nbt;
    }

    @Override
    public String toString() {

        return "";
    }
}
