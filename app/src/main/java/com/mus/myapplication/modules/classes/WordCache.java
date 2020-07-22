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

        put("apple", new FlashcardPopup.WordDesc(R.drawable.alphabet_apple, R.raw.sound_us_apple, "Apple", "Quả táo", "/ˈæp.əl/"));
        put("banana", new FlashcardPopup.WordDesc(R.drawable.alphabet_banana, R.raw.sound_us_banana, "Banana", "Quả chuối", "/bəˈnæn.ə/"));
        put("cat", new FlashcardPopup.WordDesc(R.drawable.alphabet_cat, R.raw.sound_us_cat, "Cat", "Con mèo", "/kæt/"));
        put("dog", new FlashcardPopup.WordDesc(R.drawable.alphabet_dog, R.raw.sound_us_dog, "Dog", "Con chó", "/dɑːɡ/"));
        put("elephant", new FlashcardPopup.WordDesc(R.drawable.alphabet_elephant, R.raw.sound_us_elephant, "Elephant", "Con voi", "/ˈel.ə.fənt/"));
        put("fish", new FlashcardPopup.WordDesc(R.drawable.alphabet_fish, R.raw.sound_us_fish, "Fish", "Con cá", "/fɪʃ/"));
        put("ghost", new FlashcardPopup.WordDesc(R.drawable.alphabet_ghost, R.raw.sound_us_ghost, "Ghost", "Con ma", "/ɡoʊst/"));
        put("hat", new FlashcardPopup.WordDesc(R.drawable.alphabet_hat, R.raw.sound_us_hat, "Hat", "Cái mũ/nón", "/hæt/"));
        put("ice cream", new FlashcardPopup.WordDesc(R.drawable.alphabet_icecream, R.raw.sound_us_ice_cream, "Ice Cream", "Kem", "/ˈaɪs ˌkriːm/"));
        put("jump", new FlashcardPopup.WordDesc(R.drawable.alphabet_jump, R.raw.sound_us_jump, "Jump", "Nhảy", "/dʒʌmp/"));
        put("king", new FlashcardPopup.WordDesc(R.drawable.alphabet_king, R.raw.sound_us_king, "King", "Vua", "/kɪŋ/"));
        put("lamb", new FlashcardPopup.WordDesc(R.drawable.alphabet_lamb, R.raw.sound_us_lamb, "Lamb", "Con cừu", "/læm/"));
        put("moon", new FlashcardPopup.WordDesc(R.drawable.alphabet_moon, R.raw.sound_us_moon, "Moon", "Mặt trăng", "/muːn/"));
        put("nervous", new FlashcardPopup.WordDesc(R.drawable.alphabet_nervous, R.raw.sound_us_nervous, "Nervous", "Lo lắng", "/ˈnɝː.vəs/"));
        put("octopus", new FlashcardPopup.WordDesc(R.drawable.alphabet_octopus, R.raw.sound_us_octopus, "Octopus", "Con bạch tuộc", "/ˈɑːk.tə.pəs/"));
        put("pig", new FlashcardPopup.WordDesc(R.drawable.alphabet_pig, R.raw.sound_us_pig, "Pig", "Con lợn/heo", "/pɪɡ/"));
        put("queen", new FlashcardPopup.WordDesc(R.drawable.alphabet_queen, R.raw.sound_us_queen, "Queen", "Hoàng hậu", "/kwiːn/"));
        put("ruler", new FlashcardPopup.WordDesc(R.drawable.alphabet_ruler, R.raw.sound_us_ruler, "Ruler", "Cây thước", "/ˈruː.lɚ/"));
        put("ship", new FlashcardPopup.WordDesc(R.drawable.alphabet_ship, R.raw.sound_us_ship, "Ship", "Tàu thuỷ", "/ʃɪp/"));
        put("train", new FlashcardPopup.WordDesc(R.drawable.alphabet_train, R.raw.sound_us_train, "Train", "Tàu hoả", "/treɪn/"));
        put("umbrella", new FlashcardPopup.WordDesc(R.drawable.alphabet_umbrella, R.raw.sound_us_umbrella, "Umbrella", "Cây dù", "/ʌmˈbrel.ə/"));
        put("village", new FlashcardPopup.WordDesc(R.drawable.alphabet_village, R.raw.sound_us_village, "Village", "Ngôi làng", "/ˈvɪl.ɪdʒ/"));
        put("wolf", new FlashcardPopup.WordDesc(R.drawable.alphabet_wolf, R.raw.sound_us_wolf, "Wolf", "Con sói", "/wʊlf/"));
        put("x ray", new FlashcardPopup.WordDesc(R.drawable.alphabet_xray, R.raw.sound_us_x_ray, "X Ray", "Phim X quang", "/ˈeks.reɪ/"));
        put("yellow", new FlashcardPopup.WordDesc(R.drawable.alphabet_yellow, R.raw.sound_us_yellow, "Yellow", "Màu vàng", "/ˈjel.oʊ/"));
        put("zebra", new FlashcardPopup.WordDesc(R.drawable.alphabet_zebra, R.raw.sound_us_zebra, "Zebra", "Ngựa vằn", "/ˈziː.brə/"));
    }};

    public static FlashcardPopup.WordDesc getWordDesc(String s){
        return map.get(s);
    }
}
