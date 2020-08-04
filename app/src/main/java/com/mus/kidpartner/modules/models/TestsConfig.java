package com.mus.kidpartner.modules.models;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.Point;
import com.mus.kidpartner.modules.models.garage.CraftQuest;
import com.mus.kidpartner.modules.models.garage.CraftTest;
import com.mus.kidpartner.modules.models.school.IQQuestion;
import com.mus.kidpartner.modules.models.school.IQTest;

public class TestsConfig {
    private static IQTest[] iqTests = new IQTest[]{
            new IQTest(
                    new IQQuestion(0, R.drawable.school_iq_test1_q1_ques, R.drawable.school_iq_test1_q1_ans1, R.drawable.school_iq_test1_q1_ans2, R.drawable.school_iq_test1_q1_ans2, R.drawable.school_iq_test1_q1_ans2, R.drawable.school_iq_test1_q1_ans2, R.drawable.school_iq_test1_q1_ans2),
                    new IQQuestion(1, R.drawable.school_iq_test1_q2_ques, R.drawable.school_iq_test1_q2_ans1, R.drawable.school_iq_test1_q2_ans2, R.drawable.school_iq_test1_q2_ans3, R.drawable.school_iq_test1_q2_ans4, R.drawable.school_iq_test1_q2_ans5, R.drawable.school_iq_test1_q2_ans6),
                    new IQQuestion(2, R.drawable.school_iq_test1_q3_ques, R.drawable.school_iq_test1_q3_ans1, R.drawable.school_iq_test1_q3_ans2, R.drawable.school_iq_test1_q3_ans3, R.drawable.school_iq_test1_q3_ans4, R.drawable.school_iq_test1_q3_ans5, R.drawable.school_iq_test1_q3_ans6),
                    new IQQuestion(3, R.drawable.school_iq_test1_q4_ques, R.drawable.school_iq_test1_q4_ans1, R.drawable.school_iq_test1_q4_ans2, R.drawable.school_iq_test1_q4_ans3, R.drawable.school_iq_test1_q4_ans4, R.drawable.school_iq_test1_q4_ans5, R.drawable.school_iq_test1_q4_ans6),
                    new IQQuestion(4, R.drawable.school_iq_test1_q5_ques, R.drawable.school_iq_test1_q5_ans1, R.drawable.school_iq_test1_q5_ans2, R.drawable.school_iq_test1_q5_ans3, R.drawable.school_iq_test1_q5_ans4, R.drawable.school_iq_test1_q5_ans5, R.drawable.school_iq_test1_q5_ans6),
                    new IQQuestion(5, R.drawable.school_iq_test1_q6_ques, R.drawable.school_iq_test1_q6_ans1, R.drawable.school_iq_test1_q6_ans2, R.drawable.school_iq_test1_q6_ans3, R.drawable.school_iq_test1_q6_ans4, R.drawable.school_iq_test1_q6_ans5, R.drawable.school_iq_test1_q6_ans6),
                    new IQQuestion(6, R.drawable.school_iq_test1_q7_ques, R.drawable.school_iq_test1_q7_ans1, R.drawable.school_iq_test1_q7_ans2, R.drawable.school_iq_test1_q7_ans3, R.drawable.school_iq_test1_q7_ans4, R.drawable.school_iq_test1_q7_ans5, R.drawable.school_iq_test1_q7_ans6),
                    new IQQuestion(7, R.drawable.school_iq_test1_q8_ques, R.drawable.school_iq_test1_q8_ans1, R.drawable.school_iq_test1_q8_ans2, R.drawable.school_iq_test1_q8_ans3, R.drawable.school_iq_test1_q8_ans4, R.drawable.school_iq_test1_q8_ans5, R.drawable.school_iq_test1_q8_ans6),
                    new IQQuestion(8, R.drawable.school_iq_test1_q9_ques, R.drawable.school_iq_test1_q9_ans1, R.drawable.school_iq_test1_q9_ans2, R.drawable.school_iq_test1_q9_ans3, R.drawable.school_iq_test1_q9_ans4, R.drawable.school_iq_test1_q9_ans5, R.drawable.school_iq_test1_q9_ans6),
                    new IQQuestion(9, R.drawable.school_iq_test1_q10_ques, R.drawable.school_iq_test1_q10_ans1, R.drawable.school_iq_test1_q10_ans2, R.drawable.school_iq_test1_q10_ans3, R.drawable.school_iq_test1_q10_ans4, R.drawable.school_iq_test1_q10_ans5, R.drawable.school_iq_test1_q10_ans6)
            ),
            new IQTest(
                    new IQQuestion(0, R.drawable.school_iq_test2_q1_ques, R.drawable.school_iq_test2_q1_ans1, R.drawable.school_iq_test2_q1_ans2, R.drawable.school_iq_test2_q1_ans3, R.drawable.school_iq_test2_q1_ans4, R.drawable.school_iq_test2_q1_ans5, R.drawable.school_iq_test2_q1_ans6),
                    new IQQuestion(1, R.drawable.school_iq_test2_q2_ques, R.drawable.school_iq_test2_q2_ans1, R.drawable.school_iq_test2_q2_ans2, R.drawable.school_iq_test2_q2_ans3, R.drawable.school_iq_test2_q2_ans4, R.drawable.school_iq_test2_q2_ans5, R.drawable.school_iq_test2_q2_ans6),
                    new IQQuestion(2, R.drawable.school_iq_test2_q3_ques, R.drawable.school_iq_test2_q3_ans1, R.drawable.school_iq_test2_q3_ans2, R.drawable.school_iq_test2_q3_ans3, R.drawable.school_iq_test2_q3_ans4, R.drawable.school_iq_test2_q3_ans5, R.drawable.school_iq_test2_q3_ans6),
                    new IQQuestion(3, R.drawable.school_iq_test2_q4_ques, R.drawable.school_iq_test2_q4_ans1, R.drawable.school_iq_test2_q4_ans2, R.drawable.school_iq_test2_q4_ans3, R.drawable.school_iq_test2_q4_ans4, R.drawable.school_iq_test2_q4_ans5, R.drawable.school_iq_test2_q4_ans6),
                    new IQQuestion(4, R.drawable.school_iq_test2_q5_ques, R.drawable.school_iq_test2_q5_ans1, R.drawable.school_iq_test2_q5_ans2, R.drawable.school_iq_test2_q5_ans3, R.drawable.school_iq_test2_q5_ans4, R.drawable.school_iq_test2_q5_ans5, R.drawable.school_iq_test2_q5_ans6),
                    new IQQuestion(5, R.drawable.school_iq_test2_q6_ques, R.drawable.school_iq_test2_q6_ans1, R.drawable.school_iq_test2_q6_ans2, R.drawable.school_iq_test2_q6_ans3, R.drawable.school_iq_test2_q6_ans4, R.drawable.school_iq_test2_q6_ans5, R.drawable.school_iq_test2_q6_ans6),
                    new IQQuestion(6, R.drawable.school_iq_test2_q7_ques, R.drawable.school_iq_test2_q7_ans1, R.drawable.school_iq_test2_q7_ans2, R.drawable.school_iq_test2_q7_ans3, R.drawable.school_iq_test2_q7_ans4, R.drawable.school_iq_test2_q7_ans5, R.drawable.school_iq_test2_q7_ans6),
                    new IQQuestion(7, R.drawable.school_iq_test2_q8_ques, R.drawable.school_iq_test2_q8_ans1, R.drawable.school_iq_test2_q8_ans2, R.drawable.school_iq_test2_q8_ans3, R.drawable.school_iq_test2_q8_ans4, R.drawable.school_iq_test2_q8_ans5, R.drawable.school_iq_test2_q8_ans6),
                    new IQQuestion(8, R.drawable.school_iq_test2_q9_ques, R.drawable.school_iq_test2_q9_ans1, R.drawable.school_iq_test2_q9_ans2, R.drawable.school_iq_test2_q9_ans3, R.drawable.school_iq_test2_q9_ans4, R.drawable.school_iq_test2_q9_ans5, R.drawable.school_iq_test2_q9_ans6),
                    new IQQuestion(9, R.drawable.school_iq_test2_q10_ques, R.drawable.school_iq_test2_q10_ans1, R.drawable.school_iq_test2_q10_ans2, R.drawable.school_iq_test2_q10_ans3, R.drawable.school_iq_test2_q10_ans4, R.drawable.school_iq_test2_q10_ans5, R.drawable.school_iq_test2_q10_ans6),
                    new IQQuestion(10, R.drawable.school_iq_test2_q11_ques, R.drawable.school_iq_test2_q11_ans1, R.drawable.school_iq_test2_q11_ans2, R.drawable.school_iq_test2_q11_ans3, R.drawable.school_iq_test2_q11_ans4, R.drawable.school_iq_test2_q11_ans5, R.drawable.school_iq_test2_q11_ans6),
                    new IQQuestion(11, R.drawable.school_iq_test2_q12_ques, R.drawable.school_iq_test2_q12_ans1, R.drawable.school_iq_test2_q12_ans2, R.drawable.school_iq_test2_q12_ans3, R.drawable.school_iq_test2_q12_ans4, R.drawable.school_iq_test2_q12_ans5, R.drawable.school_iq_test2_q12_ans6)
            ),
            new IQTest(
                    new IQQuestion(0, R.drawable.school_iq_test3_q1_ques, R.drawable.school_iq_test3_q1_ans1, R.drawable.school_iq_test3_q1_ans2, R.drawable.school_iq_test3_q1_ans3, R.drawable.school_iq_test3_q1_ans4, R.drawable.school_iq_test3_q1_ans5, R.drawable.school_iq_test3_q1_ans6),
                    new IQQuestion(1, R.drawable.school_iq_test3_q2_ques, R.drawable.school_iq_test3_q2_ans1, R.drawable.school_iq_test3_q2_ans2, R.drawable.school_iq_test3_q2_ans3, R.drawable.school_iq_test3_q2_ans4, R.drawable.school_iq_test3_q2_ans5, R.drawable.school_iq_test3_q2_ans6),
                    new IQQuestion(2, R.drawable.school_iq_test3_q3_ques, R.drawable.school_iq_test3_q3_ans1, R.drawable.school_iq_test3_q3_ans2, R.drawable.school_iq_test3_q3_ans3, R.drawable.school_iq_test3_q3_ans4, R.drawable.school_iq_test3_q3_ans5, R.drawable.school_iq_test3_q3_ans6),
                    new IQQuestion(3, R.drawable.school_iq_test3_q4_ques, R.drawable.school_iq_test3_q4_ans1, R.drawable.school_iq_test3_q4_ans2, R.drawable.school_iq_test3_q4_ans3, R.drawable.school_iq_test3_q4_ans4, R.drawable.school_iq_test3_q4_ans5, R.drawable.school_iq_test3_q4_ans6),
                    new IQQuestion(4, R.drawable.school_iq_test3_q5_ques, R.drawable.school_iq_test3_q5_ans1, R.drawable.school_iq_test3_q5_ans2, R.drawable.school_iq_test3_q5_ans3, R.drawable.school_iq_test3_q5_ans4, R.drawable.school_iq_test3_q5_ans5, R.drawable.school_iq_test3_q5_ans6),
                    new IQQuestion(5, R.drawable.school_iq_test3_q6_ques, R.drawable.school_iq_test3_q6_ans1, R.drawable.school_iq_test3_q6_ans2, R.drawable.school_iq_test3_q6_ans3, R.drawable.school_iq_test3_q6_ans4, R.drawable.school_iq_test3_q6_ans5, R.drawable.school_iq_test3_q6_ans6),
                    new IQQuestion(6, R.drawable.school_iq_test3_q7_ques, R.drawable.school_iq_test3_q7_ans1, R.drawable.school_iq_test3_q7_ans2, R.drawable.school_iq_test3_q7_ans3, R.drawable.school_iq_test3_q7_ans4, R.drawable.school_iq_test3_q7_ans5, R.drawable.school_iq_test3_q7_ans6),
                    new IQQuestion(7, R.drawable.school_iq_test3_q8_ques, R.drawable.school_iq_test3_q8_ans1, R.drawable.school_iq_test3_q8_ans2, R.drawable.school_iq_test3_q8_ans3, R.drawable.school_iq_test3_q8_ans4, R.drawable.school_iq_test3_q8_ans5, R.drawable.school_iq_test3_q8_ans6)
            )
    };

    private static CraftTest[] craftTests = new CraftTest[]{
            new CraftTest(
                    new CraftQuest(new int[]{R.drawable.gara_blender_0, R.drawable.gara_blender_1}, new Point[]{new Point(11.759399f, -343.92023f)}, new int[]{0,1}),
                    new CraftQuest(new int[]{R.drawable.gara_fan_0, R.drawable.gara_fan_1, R.drawable.gara_fan_2}, new Point[]{new Point(-54.249146f, -171.13113f), new Point(-113.07855f, -211.65909f)}, new int[]{0,1,2}),
                    new CraftQuest(new int[]{R.drawable.gara_hair_dryer_0, R.drawable.gara_hair_dryer_1, R.drawable.gara_hair_dryer_2}, new Point[]{new Point(-347.42297f, 291.69565f), new Point(-101.90234f, -61.32968f)}, new int[]{0,1,2}),
                    new CraftQuest(new int[]{R.drawable.gara_lamp_0, R.drawable.gara_lamp_1, R.drawable.gara_lamp_2}, new Point[]{new Point(-102.98865f, 707.2158f), new Point(-388.72778f, -64.81616f)}, new int[]{0,1,2})
            ),
            new CraftTest(
                    new CraftQuest(new int[]{R.drawable.gara_bike_0, R.drawable.gara_bike_1, R.drawable.gara_bike_1}, new Point[]{new Point(342.3047f, 132.80438f), new Point(-112.31305f, 134.03754f)}, new int[]{2,0,1}),
                    new CraftQuest(new int[]{R.drawable.gara_motorcycle_0, R.drawable.gara_motorcycle_1, R.drawable.gara_motorcycle_1}, new Point[]{new Point(588.6322f, 445.50525f), new Point(88.84656f, 432.37988f)}, new int[]{2,0,1}),
                    new CraftQuest(new int[]{R.drawable.gara_car_0, R.drawable.gara_car_1, R.drawable.gara_car_1, R.drawable.gara_car_2, R.drawable.gara_car_3}, new Point[]{new Point(75.21484f, 175.95663f), new Point(679.4273f, 171.13638f), new Point(166.32074f, 18.052216f), new Point(375.93195f, 18.912292f)}, new int[]{4,0,1,2,3}),
                    new CraftQuest(new int[]{R.drawable.gara_truck_0, R.drawable.gara_truck_1, R.drawable.gara_truck_1, R.drawable.gara_truck_1, R.drawable.gara_truck_2}, new Point[]{new Point(624.63354f, 262.05267f), new Point(491.828f, 261.97998f), new Point(102.35022f, 261.7923f), new Point(183.88684f, 1.5118408f)}, new int[]{4,0,1,2,3})
            ),
            new CraftTest(
                    new CraftQuest(new int[]{R.drawable.gara_drill_0, R.drawable.gara_drill_1}, new Point[]{new Point(462.68713f, 28.950073f)}, new int[]{0,1}),
                    new CraftQuest(new int[]{R.drawable.gara_saw_machine_0, R.drawable.gara_saw_machine_1, R.drawable.gara_saw_machine_2}, new Point[]{new Point(111.848206f, 213.17726f), new Point(210.47247f, 257.30585f)}, new int[]{0,2,1}),
                    new CraftQuest(new int[]{R.drawable.gara_cutting_machine_0, R.drawable.gara_cutting_machine_1, R.drawable.gara_cutting_machine_2}, new Point[]{new Point(-21.298523f, 141.44543f), new Point(-88.54266f, -420.89432f)}, new int[]{0,1,2}),
                    new CraftQuest(new int[]{R.drawable.gara_lathe_machine_0, R.drawable.gara_lathe_machine_1, R.drawable.gara_lathe_machine_2}, new Point[]{new Point(18.695557f, -201.85919f), new Point(496.14337f, -171.45981f)}, new int[]{0,1,2})
            )
    };

    public static IQTest getIQTest(int index){
        if(index > 0 && index < iqTests.length)
            return iqTests[index];
        else
            return iqTests[0];
    }

    public static CraftTest getCraftTest(int index){
        if(index > 0 && index < craftTests.length){
            return craftTests[index];
        }
        else
            return craftTests[0];
    }
}
