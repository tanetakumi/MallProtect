package com.github.showwno.mallprotect;

import com.github.showwno.mallprotect.Util.LocationStructure;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class MallProtectConfig {

    private final MallProtect plugin;
    private FileConfiguration config;

    private final List<LocationStructure> locationStructureList = new ArrayList<>();

    public MallProtectConfig(MallProtect plugin) {
        this.plugin = plugin;
        load();
    }


    //@SuppressWarnings("unchecked")
    public void load() {
        // 設定ファイルを保存
        plugin.saveDefaultConfig();
        if (config != null) { // configが非null == リロードで呼び出された
            plugin.reloadConfig();
        }
        config = plugin.getConfig();

        /* //複数条件の時
        List<Map<?,?>> locations=config.getMapList("locations");
        for(int i=0;i<locations.size();i++){
            Location loc1 = locations.get(0).getLocations();
        }*/

        //List<Map<?,?>> regions = config.getMapList("region");
        //config.getConfigurationSection("region").getKeys(false).size();
        //System.out.println(regions.);

        int num = 0;
        if(config.contains("region")){
            num = config.getConfigurationSection("region").getKeys(false).size();
        }

        for(int i= 0; i<num;i++){
            String region_name = config.getString("region.region"+i+".name");
            Location loc1 = config.getLocation("region.region"+i+".loc1");
            Location loc2 = config.getLocation("region.region"+i+".loc2");
            if(loc1!=null && loc2!=null){
                locationStructureList.add(new LocationStructure(region_name,loc1,loc2));
            }
        }
    }

    public List<LocationStructure> getLocationStructureList() {
        return locationStructureList;
    }

    public void saveLocationsData(){
        /* //複数条件の時
        List<Map<String,Object>> locations ~~~~~~
        for(int i=0;i<locations.size();i++){
            Location loc1 = locations.get(0).getLocations();
        }*/
        config.set("region",null);
        for(int i= 0; i<locationStructureList.size();i++){
            config.set("region.region"+i+".name",locationStructureList.get(i).getName());
            config.set("region.region"+i+".loc1",locationStructureList.get(i).getLoc1());
            config.set("region.region"+i+".loc2",locationStructureList.get(i).getLoc2());
        }
        plugin.saveConfig();
    }

}
