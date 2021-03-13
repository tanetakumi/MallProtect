package com.github.showwno.mallprotect;

import com.github.showwno.mallprotect.Util.LocationStructure;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Location loc1 = config.getLocation("mall.loc1");
        Location loc2 = config.getLocation("mall.loc2");
        if(loc1!=null && loc2!=null){
            locationStructureList.add(new LocationStructure(loc1,loc2));
        }
    }

    public List<LocationStructure> getLocationStructureList() {
        return locationStructureList;
    }

    public void saveLocationsData(String arg,Location loc){
        /* //複数条件の時
        List<Map<String,Object>> locations ~~~~~~
        for(int i=0;i<locations.size();i++){
            Location loc1 = locations.get(0).getLocations();
        }*/
        if(arg.equals("loc1")){
            config.set("mall.loc1",loc);
        } else if(arg.equals("loc2")){
            config.set("mall.loc2",loc);
        }
        plugin.saveConfig();
    }

}
