package com.mus.kidpartner.modules.classes;

import com.mus.kidpartner.modules.controllers.GameViewCache;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.popup.FlashcardPopup;
import com.mus.kidpartner.modules.views.setting.SettingUI;
import com.mus.kidpartner.modules.views.tutorial.NotUnlockedScene;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class UIManager {
    private static UIManager instance;
    private HashMap<Class, String> map;

    // Add new gui type here
    private UIManager(){
        this.map = new HashMap<>();
        map.put(SettingUI.class, GameViewCache.SETTING);
    }

    public static UIManager getInstance(){
        if(instance == null){
            instance = new UIManager();
        }

        return instance;
    }

    public void hideFlashcardPopup(GameView parent){
        FlashcardPopup popup = (FlashcardPopup) parent.getChild("flashcard");
        if(popup != null){
            popup.hide();
        }
    }

    public NotUnlockedScene getNotUnlockedLayer(GameView parent){
        NotUnlockedScene layer = (NotUnlockedScene) parent.getChild("notUnlocked");
        if(layer == null){
            layer = new NotUnlockedScene(parent);
            parent.mappingChild(layer, "notUnlocked");
        }
        layer.show();
        return layer;
    }

    public FlashcardPopup getFlashcardPopup(FlashcardPopup.WordDesc word, GameView parent){
        FlashcardPopup popup = (FlashcardPopup) parent.getChild("flashcard");
        if(popup == null){
            popup = new FlashcardPopup(parent);
            parent.mappingChild(popup, "flashcard");
            popup.setPositionCenterParent(false, true);
        }

        popup.loadContent(word);
        popup.show();
        return popup;
    }
    public FlashcardPopup getFlashcardPopup(String word, GameView parent){
        if(WordCache.getWordDesc(word) != null)
            return getFlashcardPopup(WordCache.getWordDesc(word), parent);
        return null;
    }

    public <T extends GameView> T getUI(Class<T> uiClass, GameView parent){
        if(map.containsKey(uiClass)){
            String viewTag = map.get(uiClass);
            GameView v = GameViewCache.getView(viewTag);
            if(v == null){
                try {
                    v = uiClass.getConstructor(GameView.class).newInstance(parent);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                GameViewCache.putView(viewTag, v);
            }
            return (T) v;
        }
        return null;
    }
}
