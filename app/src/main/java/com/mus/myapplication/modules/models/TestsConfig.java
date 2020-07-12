package com.mus.myapplication.modules.models;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.models.garage.CraftQuest;
import com.mus.myapplication.modules.models.garage.CraftTest;
import com.mus.myapplication.modules.models.school.IQQuestion;
import com.mus.myapplication.modules.models.school.IQTest;

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
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)})
            ),
            new CraftTest(
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)})
            ),
            new CraftTest(
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)}),
                    new CraftQuest(new int[]{R.drawable.gara_test1_q1_0, R.drawable.gara_test1_q1_1, R.drawable.gara_test1_q1_2}, new Point[]{new Point(-168.33087f, 261.84726f), new Point(430.84045f, 262.732f)})
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
