package com.mus.kidpartner.modules.classes;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.views.popup.FlashcardPopup;

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
        put("pot", new FlashcardPopup.WordDesc(R.drawable.livingroom_bonsai_pot, R.raw.sound_us_pot, "Pot", "Cái chậu", "/pɑːt/"));
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
        put("fridge", new FlashcardPopup.WordDesc(R.drawable.kitchen_fridge, R.raw.sound_us_fridge, "Fridge", "Tủ lạnh", "/ˈfrɪdʒ/"));
        put("table", new FlashcardPopup.WordDesc(R.drawable.kitchen_table, R.raw.sound_us_table, "Table", "Cái bàn", "/ˈteɪ.bəl/"));
        put("stove", new FlashcardPopup.WordDesc(R.drawable.kitchen_stove, R.raw.sound_us_stove, "Stove", "Cái bếp", "/ˈstoʊv/"));
        put("chair", new FlashcardPopup.WordDesc(R.drawable.kitchen_chair1, R.raw.sound_us_chair, "Chair", "Cái ghế", "/ˈtʃer/"));
        put("curtain", new FlashcardPopup.WordDesc(R.drawable.livingroom_curtain, R.raw.sound_us_curtain, "Curtain", "Tấm màn", "/ˈkɝː.t̬ən/"));
        put("sofa", new FlashcardPopup.WordDesc(R.drawable.livingroom_sofa, R.raw.sound_us_sofa, "Sofa", "Ghế sô pha", "/ˈsoʊ.fə/"));
        put("ceilingLight", new FlashcardPopup.WordDesc(R.drawable.livingroom_ceiling_light1, R.raw.sound_us_light, "Light", "Cái đèn", "/laɪt/"));
        put("toilet", new FlashcardPopup.WordDesc(R.drawable.bathroom_toilet, R.raw.sound_us_toilet, "Toilet", "Bồn cầu", "/ˈtɔɪ.lət/"));
        put("washbasin", new FlashcardPopup.WordDesc(R.drawable.bathroom_washbasin, R.raw.sound_us_washbasin, "Washbasin", "Bồn rửa mặt", "/ˈwɑːʃˌbeɪ.sən/"));
        put("bathtub", new FlashcardPopup.WordDesc(R.drawable.bathroom_bathtub, R.raw.sound_us_bathtub, "Bathtub", "Bồn tắm", "/ˈbæθ.tʌb/"));
        put("shower", new FlashcardPopup.WordDesc(R.drawable.bathroom_shower, R.raw.sound_us_shower, "Shower", "Vòi sen", "/ˈʃaʊ.ɚ/"));
        put("door", new FlashcardPopup.WordDesc(R.drawable.bathroom_door, R.raw.sound_us_door, "Door", "Cửa", "/dɔːr/"));

        put("hair dryer", new FlashcardPopup.WordDesc(R.drawable.gara_hair_dryer, R.raw.sound_us_hair_dryer, "Hair Dryer", "Máy sấy tóc", "/ˈher ˌdraɪ.ɚ/"));
        put("truck", new FlashcardPopup.WordDesc(R.drawable.gara_truck, R.raw.sound_us_truck, "Truck", "Xe tải", "/trʌk/"));
        put("car", new FlashcardPopup.WordDesc(R.drawable.gara_car, R.raw.sound_us_car, "Car", "Ô tô", "/kɑːr/"));
        put("blender", new FlashcardPopup.WordDesc(R.drawable.gara_blender, R.raw.sound_us_blender, "Blender", "Máy xay", "/ˈblen.dɚ/"));
        put("fan", new FlashcardPopup.WordDesc(R.drawable.gara_fan, R.raw.sound_us_fan, "Fan", "Quạt", "/fæn/"));
        put("bicycle", new FlashcardPopup.WordDesc(R.drawable.gara_bicycle, R.raw.sound_us_bicycle, "Bicycle", "Xe đạp", "/ˈbaɪ.sə.kəl/"));
        put("motorcycle", new FlashcardPopup.WordDesc(R.drawable.gara_motorcycle, R.raw.sound_us_motorcycle, "Motorcycle", "Xe máy", "/ˈmoʊ.t̬ɚˌsaɪ.kəl/"));
        put("drill", new FlashcardPopup.WordDesc(R.drawable.gara_drill, R.raw.sound_us_drill, "Drill", "Cái khoan", "/drɪl/"));
        put("saw machine", new FlashcardPopup.WordDesc(R.drawable.gara_saw_machine, R.raw.sound_us_saw_machine, "Saw Machine", "Cưa máy", "/sɑː məˈʃiːn/"));
        put("cutting machine", new FlashcardPopup.WordDesc(R.drawable.gara_cutting_machine, R.raw.sound_us_cutting_machine, "Cutting Machine", "Máy cắt", "/ˈkʌt̬.ɪŋ məˈʃiːn/"));
        put("lathe machine", new FlashcardPopup.WordDesc(R.drawable.gara_lathe_machine, R.raw.sound_us_lathe_machine, "Lathe Machine", "Máy tiện", "/leɪð məˈʃiːn/"));


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

    public static String[] listWord = new String[]{
            "apple",
            "banana",
            "cat",
            "dog",
            "elephant",
            "fish",
            "ghost",
            "hat",
            "ice cream",
            "jump",
            "king",
            "lamb",
            "moon",
            "nervous",
            "octopus",
            "pig",
            "queen",
            "ruler",
            "ship",
            "train",
            "umbrella",
            "village",
            "wolf",
            "x ray",
            "yellow",
            "zebra"
    };
    public static FlashcardPopup.WordDesc getWordDesc(String s){
        return map.get(s);
    }
}
