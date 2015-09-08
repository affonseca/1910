package com.mobilelearning.game1910;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ObjectMap;
import com.mobilelearning.game1910.uiUtils.PolygonRegionDrawable;
import com.mobilelearning.game1910.uiUtils.RgbColor;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 12-01-2015
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public class Assets {

    public static String currentVersion = "1.1";

    public static Color lightBrown = new RgbColor(151f, 109f, 92f, 255f);
    public static Color darkBrown = new RgbColor(117f, 51f, 11f, 255f);
    public static int levelValues [] = {1890, 1908, 1910, 1911, 1913};
    public static int maxLevelScores [] = {448, 535, 460, 815};

    //Global Assets
    public static Skin uiSkin;
    public static TextureAtlas notebook;
    public static TextureAtlas miscellaneous;
    public static Animation loading;
    public static Music textBoxFX;
    public static Animation clickAnimation;

    //Standard Level Assets
    public static Sound successFX;
    public static Sound failFX;
    public static Sound paperFX;
    public static Sound penFX;
    public static Sound moneyFX;
    public static Sound fanfareFX;

    //Reused sound (level 1, 3 and 4)
    public static Sound phoneFX;


    //Level 1 Assets
    public static TextureAtlas level1scene1;
    public static TextureAtlas level1scene2_1;
    public static TextureAtlas level1scene2_2;
    public static TextureAtlas level1scene3;
    private final static int hangmanAudiosSize = 8;
    public static Music [] hangmanAudios;

    //Level 2 Assets
    public static TextureAtlas level2scene1;
    public static TextureAtlas level2scene2_1;
    public static TextureAtlas level2scene2_2;
    public static TextureAtlas level2scene2_3;
    public static TextureAtlas level2scene3_1;
    public static TextureAtlas level2scene3_2;
    public static TextureAtlas level2scene4_1;
    public static TextureAtlas level2scene4_2;
    public static TextureAtlas level2scene4_3;
    public static TextureAtlas level2scene5_1;
    public static TextureAtlas level2scene5_2;
    private static final int regicidePuzzleSize = 20;
    public static PolygonRegionDrawable [] regicidePuzzle;
    public static Animation pathAnimation;
    public static Music regicideFX;
    private static final int audio9size = 10;
    public static Music [] audio9;

    //Level 3 Assets
    public static TextureAtlas level3scene1;
    public static TextureAtlas level3scene2_1;
    public static TextureAtlas level3scene2_2;
    public static TextureAtlas level3scene3_1;
    public static TextureAtlas level3scene3_2;
    public static TextureAtlas level3scene4;
    public static TextureAtlas level3scene5;

    //Level 4 Assets
    public static TextureAtlas level4scene1;
    public static TextureAtlas level4scene2;
    public static TextureAtlas level4scene3;
    public static TextureAtlas level4scene4;
    public static TextureAtlas level4scene5;
    public static Music audio10;

    private static GameAssetManager manager;

    public static void  loadStartupAssets(){
        manager = new GameAssetManager();

        manager.gLoad("ui/startup.atlas", TextureAtlas.class);
        manager.gLoad("animations/loadingAnimation.atlas", TextureAtlas.class);

        manager.finishLoading();

        final TextureAtlas loadingAtlas = manager.get("animations/loadingAnimation.atlas", TextureAtlas.class);
        loadingAtlas.getRegions().sort(new Comparator<TextureAtlas.AtlasRegion>() {
            @Override
            public int compare(TextureAtlas.AtlasRegion o1, TextureAtlas.AtlasRegion o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        loading = new Animation(1 / 15f, loadingAtlas.getRegions());
        loading.setPlayMode(Animation.PlayMode.LOOP);

    }

    public static void loadGlobalAssets() {
        manager.gLoad("ui/notebook.atlas", TextureAtlas.class);
        manager.gLoad("ui/uiskin.json", Skin.class);
        manager.gLoad("sounds/textBoxSound.ogg", Music.class);
        manager.gLoad("ui/miscellaneous.atlas", TextureAtlas.class);
        manager.gLoad("animations/clickAnimation.atlas", TextureAtlas.class);

        //Main menu and level select
        manager.gLoad("ui/mainMenu.atlas", TextureAtlas.class);
        manager.gLoad("ui/levelSelect.atlas", TextureAtlas.class);

        //Standard level Assets
        manager.gLoad("sounds/paperFX.ogg", Sound.class);
        manager.gLoad("sounds/penFX.ogg", Sound.class);
        manager.gLoad("sounds/moneyFX.ogg", Sound.class);
        manager.gLoad("sounds/fanfareFX.ogg", Sound.class);
        manager.gLoad("sounds/successFX.ogg", Sound.class);
        manager.gLoad("sounds/failFX.ogg", Sound.class);
    }

    public static void prepareGlobalAssets(){
        uiSkin = manager.get("ui/uiskin.json", Skin.class);
        notebook = manager.get("ui/notebook.atlas", TextureAtlas.class);
        miscellaneous = manager.get("ui/miscellaneous.atlas", TextureAtlas.class);

        final TextureAtlas clickAtlas = manager.get("animations/clickAnimation.atlas", TextureAtlas.class);
        clickAtlas.getRegions().sort(new Comparator<TextureAtlas.AtlasRegion>() {
            @Override
            public int compare(TextureAtlas.AtlasRegion o1, TextureAtlas.AtlasRegion o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        clickAnimation = new Animation(1 / 15f, clickAtlas.getRegions());
        clickAnimation.setPlayMode(Animation.PlayMode.LOOP);

        textBoxFX = manager.get("sounds/textBoxSound.ogg", Music.class);
        textBoxFX.setLooping(true);

        //Standard level Assets
        paperFX = manager.get("sounds/paperFX.ogg", Sound.class);
        penFX = manager.get("sounds/penFX.ogg", Sound.class); //penFX.loop();
        moneyFX = manager.get("sounds/moneyFX.ogg", Sound.class); //moneyFX.loop();
        fanfareFX = manager.get("sounds/fanfareFX.ogg", Sound.class);
        successFX = manager.get("sounds/successFX.ogg", Sound.class);
        failFX = manager.get("sounds/failFX.ogg", Sound.class);

    }

    public static void loadStartupScreen(){
        manager.gLoad("ui/startup.atlas", TextureAtlas.class);
    }

    public static TextureAtlas prepareStartupScreen(){
        return manager.get("ui/startup.atlas", TextureAtlas.class);
    }

    public static void unloadStartupScreen(){
        manager.gUnload("ui/startup.atlas");
    }

    public static TextureAtlas prepareMainMenu(){
        return manager.get("ui/mainMenu.atlas", TextureAtlas.class);
    }

    public static TextureAtlas prepareLevelSelect() {
        return manager.get("ui/levelSelect.atlas", TextureAtlas.class);
    }

    public static void loadLevel1(){
        manager.gLoad("level1/level1scene1.atlas", TextureAtlas.class);
        manager.gLoad("level1/level1scene2_1.atlas", TextureAtlas.class);
        manager.gLoad("level1/level1scene2_2.atlas", TextureAtlas.class);
        manager.gLoad("level1/level1scene3.atlas", TextureAtlas.class);
        manager.gLoad("sounds/phoneFX.ogg", Sound.class);
        for(int i=0; i< hangmanAudiosSize; i++){
            manager.gLoad("sounds/audio" + (i + 1) + ".ogg", Music.class);
        }

    }

    public static void prepareLevel1(){
        level1scene1 = manager.get("level1/level1scene1.atlas", TextureAtlas.class);
        level1scene2_1 = manager.get("level1/level1scene2_1.atlas", TextureAtlas.class);
        level1scene2_2 = manager.get("level1/level1scene2_2.atlas", TextureAtlas.class);
        level1scene3 = manager.get("level1/level1scene3.atlas", TextureAtlas.class);
        phoneFX = manager.get("sounds/phoneFX.ogg", Sound.class);
        hangmanAudios = new Music[hangmanAudiosSize];
        for(int i=0; i<hangmanAudiosSize; i++){
            hangmanAudios[i] = manager.get("sounds/audio" + (i + 1) + ".ogg", Music.class);
        }
    }

    public static void unloadLevel1(){
        manager.gUnload("level1/level1scene1.atlas");
        manager.gUnload("level1/level1scene2_1.atlas");
        manager.gUnload("level1/level1scene2_2.atlas");
        manager.gUnload("level1/level1scene3.atlas");
        manager.gUnload("sounds/phoneFX.ogg");
        for(int i=0; i< hangmanAudiosSize; i++){
            manager.gUnload("sounds/audio" + (i + 1) + ".ogg");
        }

    }

    public static void loadLevel2(){
        manager.gLoad("level2/level2scene1.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene2_1.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene2_2.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene2_3.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene3_1.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene3_2.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene4_1.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene4_2.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene4_3.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene5_1.atlas", TextureAtlas.class);
        manager.gLoad("level2/level2scene5_2.atlas", TextureAtlas.class);
        manager.gLoad("animations/pathAnimation.atlas", TextureAtlas.class);
        manager.gLoad("sounds/regicideFX.ogg", Music.class);
        for(int i=0; i< audio9size; i++){
            manager.gLoad("sounds/audio9/audio9_" + (i + 1) + ".ogg", Music.class);
        }
        for(int i=0; i<regicidePuzzleSize; i++){
            manager.gLoad("level2/puzzle/regicidePiece" +(i+1) +".psh", PolygonRegion.class);
        }
    }

    public static void prepareLevel2(){
        level2scene1 = manager.get("level2/level2scene1.atlas", TextureAtlas.class);
        level2scene2_1 = manager.get("level2/level2scene2_1.atlas", TextureAtlas.class);
        level2scene2_2 = manager.get("level2/level2scene2_2.atlas", TextureAtlas.class);
        level2scene2_3 = manager.get("level2/level2scene2_3.atlas", TextureAtlas.class);
        level2scene3_1 = manager.get("level2/level2scene3_1.atlas", TextureAtlas.class);
        level2scene3_2 = manager.get("level2/level2scene3_2.atlas", TextureAtlas.class);
        level2scene4_1 = manager.get("level2/level2scene4_1.atlas", TextureAtlas.class);
        level2scene4_2 = manager.get("level2/level2scene4_2.atlas", TextureAtlas.class);
        level2scene4_3 = manager.get("level2/level2scene4_3.atlas", TextureAtlas.class);
        level2scene5_1 = manager.get("level2/level2scene5_1.atlas", TextureAtlas.class);
        level2scene5_2 = manager.get("level2/level2scene5_2.atlas", TextureAtlas.class);
        regicideFX = manager.get("sounds/regicideFX.ogg", Music.class);

        audio9 = new Music[audio9size];
        for(int i=0; i< audio9size; i++){
            audio9[i] = manager.get("sounds/audio9/audio9_" + (i + 1) + ".ogg", Music.class);
        }

        final TextureAtlas pathAtlas = manager.get("animations/pathAnimation.atlas", TextureAtlas.class);
        pathAtlas.getRegions().sort(new Comparator<TextureAtlas.AtlasRegion>() {
            @Override
            public int compare(TextureAtlas.AtlasRegion o1, TextureAtlas.AtlasRegion o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        pathAnimation = new Animation(1 / 15f, pathAtlas.getRegions());

        regicidePuzzle = new PolygonRegionDrawable[regicidePuzzleSize];
        for(int i=0; i<regicidePuzzleSize; i++){
            regicidePuzzle[i] = new PolygonRegionDrawable(manager.get("level2/puzzle/regicidePiece" +(i+1) +".psh", PolygonRegion.class));
        }
    }

    public static void unloadLevel2(){
        manager.gUnload("level2/level2scene1.atlas");
        manager.gUnload("level2/level2scene2_1.atlas");
        manager.gUnload("level2/level2scene2_2.atlas");
        manager.gUnload("level2/level2scene2_3.atlas");
        manager.gUnload("level2/level2scene3_1.atlas");
        manager.gUnload("level2/level2scene3_2.atlas");
        manager.gUnload("level2/level2scene4_1.atlas");
        manager.gUnload("level2/level2scene4_2.atlas");
        manager.gUnload("level2/level2scene4_3.atlas");
        manager.gUnload("level2/level2scene5_1.atlas");
        manager.gUnload("level2/level2scene5_2.atlas");
        manager.gUnload("sounds/regicideFX.ogg");
        for(int i=0; i< audio9size; i++){
            manager.gUnload("sounds/audio9/audio9_" + (i + 1) + ".ogg");
        }
        for(int i=0; i<regicidePuzzleSize; i++){
            manager.gUnload("level2/puzzle/regicidePiece" + (i + 1) + ".psh");
        }
    }

    public static void loadLevel3(){
        manager.gLoad("level3/level3scene1.atlas", TextureAtlas.class);
        manager.gLoad("level3/level3scene2_1.atlas", TextureAtlas.class);
        manager.gLoad("level3/level3scene2_2.atlas", TextureAtlas.class);
        manager.gLoad("level3/level3scene3_1.atlas", TextureAtlas.class);
        manager.gLoad("level3/level3scene3_2.atlas", TextureAtlas.class);
        manager.gLoad("level3/level3scene4.atlas", TextureAtlas.class);
        manager.gLoad("level3/level3scene5.atlas", TextureAtlas.class);
        manager.gLoad("sounds/phoneFX.ogg", Sound.class);
    }

    public static void prepareLevel3(){
        level3scene1 = manager.get("level3/level3scene1.atlas", TextureAtlas.class);
        level3scene2_1 = manager.get("level3/level3scene2_1.atlas", TextureAtlas.class);
        level3scene2_2 = manager.get("level3/level3scene2_2.atlas", TextureAtlas.class);
        level3scene3_1 = manager.get("level3/level3scene3_1.atlas", TextureAtlas.class);
        level3scene3_2 = manager.get("level3/level3scene3_2.atlas", TextureAtlas.class);
        level3scene4 = manager.get("level3/level3scene4.atlas", TextureAtlas.class);
        level3scene5 = manager.get("level3/level3scene5.atlas", TextureAtlas.class);
        phoneFX = manager.get("sounds/phoneFX.ogg", Sound.class);
    }

    public static void unloadLevel3(){
        manager.gUnload("level3/level3scene1.atlas");
        manager.gUnload("level3/level3scene2_1.atlas");
        manager.gUnload("level3/level3scene2_2.atlas");
        manager.gUnload("level3/level3scene3_1.atlas");
        manager.gUnload("level3/level3scene3_2.atlas");
        manager.gUnload("level3/level3scene4.atlas");
        manager.gUnload("level3/level3scene5.atlas");
        manager.gUnload("sounds/phoneFX.ogg");
    }

    public static void loadLevel4(){
        manager.gLoad("level4/level4scene1.atlas", TextureAtlas.class);
        manager.gLoad("level4/level4scene2.atlas", TextureAtlas.class);
        manager.gLoad("level4/level4scene3.atlas", TextureAtlas.class);
        manager.gLoad("level4/level4scene4.atlas", TextureAtlas.class);
        manager.gLoad("level4/level4scene5.atlas", TextureAtlas.class);
        manager.gLoad("sounds/phoneFX.ogg", Sound.class);
        manager.gLoad("sounds/audio10.ogg", Music.class);
    }

    public static void prepareLevel4(){
        level4scene1 = manager.get("level4/level4scene1.atlas", TextureAtlas.class);
        level4scene2 = manager.get("level4/level4scene2.atlas", TextureAtlas.class);
        level4scene3 = manager.get("level4/level4scene3.atlas", TextureAtlas.class);
        level4scene4 = manager.get("level4/level4scene4.atlas", TextureAtlas.class);
        level4scene5 = manager.get("level4/level4scene5.atlas", TextureAtlas.class);
        phoneFX = manager.get("sounds/phoneFX.ogg", Sound.class);
        audio10 = manager.get("sounds/audio10.ogg", Music.class);
    }

    public static void unloadLevel4(){
        manager.gUnload("level4/level4scene1.atlas");
        manager.gUnload("level4/level4scene2.atlas");
        manager.gUnload("level4/level4scene3.atlas");
        manager.gUnload("level4/level4scene4.atlas");
        manager.gUnload("level4/level4scene5.atlas");
        manager.gUnload("sounds/phoneFX.ogg");
        manager.gUnload("sounds/audio10.ogg");
    }

    public static boolean update(){
        return manager.update();
    }

    public static void dispose(){
        if(manager != null)
            manager.dispose();
    }

    public static class GameAssetManager extends AssetManager {
        ObjectMap<String, AtomicInteger> usages;

        public GameAssetManager() {
            super();
            usages = new ObjectMap<>();
        }

        public synchronized <T> void gLoad(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
            if(!usages.containsKey(fileName)){
                usages.put(fileName, new AtomicInteger(0));
                super.load(fileName, type, parameter);
            }
            usages.get(fileName).addAndGet(1);
        }

        public synchronized <T> void gLoad(String fileName, Class<T> type) {
            gLoad(fileName, type, null);
        }

        public synchronized void gUnload(String fileName) {
            if(usages.containsKey(fileName)){
                if(usages.get(fileName).addAndGet(-1) == 0){
                    usages.remove(fileName);
                    super.unload(fileName);
                }
            }
        }
    }

}
