package br.com.finalcraft.pixelmonmultiplier.config.playerdata;

import br.com.finalcraft.evernifecorespongy.config.playerdata.PDSection;
import br.com.finalcraft.evernifecorespongy.config.playerdata.PlayerController;
import br.com.finalcraft.evernifecorespongy.config.playerdata.PlayerData;

public class PMPlayerData implements PDSection {

    public static PMPlayerData getOrCreate(String playerName) {
        PlayerData playerData = PlayerController.getPlayerData(playerName);
        if (playerData == null){
            return null;
        }
        return getOrCreate(playerData);
    }
    public static PMPlayerData getOrCreate(PlayerData playerData){
        PDSection pdSection = playerData.getConfigSection(PMPlayerData.class);
        if (pdSection != null){
            return (PMPlayerData)pdSection;
        }
        return new PMPlayerData(playerData);
    }

    //------------------------------------------------------------------------------------------------------------------
    //  PixelmonMultiplier PlayerData
    //------------------------------------------------------------------------------------------------------------------

    private PlayerData playerData;
    private Double personalMultiplier = 0D;
    public boolean recentChanged = false;

    public PMPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        playerData.addConfigSection(this);
        this.personalMultiplier = playerData.getConfig().getDouble("PixelmonMultiplier.personalMultiplier", personalMultiplier);
    }

    @Override
    public void save(PlayerData playerData) {
        if (this.recentChanged){
            playerData.getConfig().setValue("PixelmonMultiplier.personalMultiplier",this.personalMultiplier);
            this.recentChanged = false;
        }
    }

    public void setRecentChanged() {
        if (!this.recentChanged) {
            this.recentChanged = true;
            this.playerData.setRecentChanged();
        }
    }

    public void setPersonalMultiplier(double multiplier){
        this.personalMultiplier = multiplier;
        forceSavePlayerDataOnYML();
    }

    public boolean forceSavePlayerDataOnYML() {
        this.setRecentChanged();
        return this.playerData.savePlayerDataOnYML();
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public double getPersonalMultiplier() {
        return personalMultiplier;
    }

}
