package com.mus.myapplication.modules.classes;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.views.popup.FlashcardPopup;

import java.util.HashMap;

public class WordCache {
    private static HashMap<String, FlashcardPopup.WordDesc> map = new HashMap<String, FlashcardPopup.WordDesc>(){{
        put("window", new FlashcardPopup.WordDesc(R.drawable.bedroom_window, R.raw.sound_us_window, "Window", "Cửa sổ", "/ˈwɪn.doʊ/"));
        put("drawers", new FlashcardPopup.WordDesc(R.drawable.bedroom_drawers, R.raw.sound_us_drawer, "Drawer", "Ngăn kéo", "/drɑː/"));
        put("mirror", new FlashcardPopup.WordDesc(R.drawable.bedroom_mirror, R.raw.sound_us_mirror, "Mirror", "Gương/Kính", "/ˈmɪr.ɚ/"));
        put("picture", new FlashcardPopup.WordDesc(R.drawable.bedroom_picture, R.raw.sound_us_picture, "Picture", "Bức tranh", "/ˈpɪk.tʃɚ/"));
        put("cupboard", new FlashcardPopup.WordDesc(R.drawable.bedroom_cupboard, R.raw.sound_us_cupboard, "Cupboard", "Cái tủ", "/ˈkʌb.ɚd/"));
        put("lamp", new FlashcardPopup.WordDesc(R.drawable.livingroom_lamp, R.raw.sound_us_lamp, "Lamp", "Cái đèn", "/læmp/"));
        put("bed", new FlashcardPopup.WordDesc(R.drawable.bedroom_bed, R.raw.sound_us_bed, "Bed", "Cái giường", "/bed/"));
        put("pillow", new FlashcardPopup.WordDesc(R.drawable.bedroom_pillow, R.raw.sound_us_pillow, "Pillow", "Cái gối", "/ˈpɪl.oʊ/"));
        put("blanket", new FlashcardPopup.WordDesc(R.drawable.bedroom_blanket, R.raw.sound_us_blanket, "Blanket", "Cái chăn/mền", "/ˈblæŋ.kɪt/"));
        put("pot", new FlashcardPopup.WordDesc(R.drawable.bedroom_bonsai_pot, R.raw.sound_us_pot, "Pot", "Cái chậu", "/pɑːt/"));
        put("carpet", new FlashcardPopup.WordDesc(R.drawable.bedroom_carpet, R.raw.sound_us_carpet, "Carpet", "Tấm thảm", "/ˈkɑːr.pət/"));
        put("grandfather", new FlashcardPopup.WordDesc(R.drawable.relative_grandfather, R.raw.sound_us_grandfather, "Grandfather", "Ông", "/ˈɡræn.fɑː.ðɚ/"));
        put("grandmother", new FlashcardPopup.WordDesc(R.drawable.relative_grandmother, R.raw.sound_us_grandmother, "Grandmother", "Bà", "/ˈɡræn.mʌð.ɚ/"));
        put("father", new FlashcardPopup.WordDesc(R.drawable.relative_father, R.raw.sound_us_father, "Father", "Cha/Bố", "/ˈfɑː.ðɚ/"));
        put("mother", new FlashcardPopup.WordDesc(R.drawable.relative_mother, R.raw.sound_us_mother, "Mother", "Mẹ", "/ˈmʌð.ɚ/"));
        put("uncle", new FlashcardPopup.WordDesc(R.drawable.relative_uncle, R.raw.sound_us_uncle, "Uncle", "Chú/Cậu/Bác trai", "/ˈʌŋ.kəl/"));
        put("aunt", new FlashcardPopup.WordDesc(R.drawable.relative_aunt, R.raw.sound_us_aunt, "Aunt", "Dì/Cô/Bác gái", "/ænt/"));
        put("sister", new FlashcardPopup.WordDesc(R.drawable.relative_sister, R.raw.sound_us_sister, "Sister", "Chị/em gái", "/ˈsɪs.tɚ/"));
        put("brother", new FlashcardPopup.WordDesc(R.drawable.relative_brother, R.raw.sound_us_brother, "Brother", "Anh/em trai", "/ˈbrʌð.ɚ/"));
        put("cousin", new FlashcardPopup.WordDesc(R.drawable.relative_cousin2, R.raw.sound_us_cousin, "Cousin", "Anh chị em họ", "/ˈkʌz.ən/"));
    }};

    public static FlashcardPopup.WordDesc getWordDesc(String s){
        return map.get(s);
    }
}
