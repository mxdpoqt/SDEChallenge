package com.PaytmLabs.CodeChallenge;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.PaytmLabs.CodeChallenge.Service.PaytmLabs_SDE_Challenge_Coding_Question;
import org.junit.Assert;
@RunWith(SpringRunner.class)
@SpringBootTest
class PaytmLabsSdeChallengeApplicationTests {

	@Autowired
	PaytmLabs_SDE_Challenge_Coding_Question paytmLabsService;
	

	
	@Test
	public void TestCodeQuestionInterface() {
		paytmLabsService.init(5);//let's set the N value of last N as 5
		Assert.assertEquals("result match", 2.0, paytmLabsService.addAndGetMovingAverage(2), 0);//amount =2, 2/1=2
		Assert.assertEquals("result match", 3.5, paytmLabsService.addAndGetMovingAverage(5), 0);//amount =2+5=7, 7/2 = 3.5
		Assert.assertEquals("result match", 4.67, paytmLabsService.addAndGetMovingAverage(7), 0.1);//since 2+5+7 =14 and 14/3 = 4.6666666.....7 so i set the delta value as 0.1
		Assert.assertEquals("result match", 4.25, paytmLabsService.addAndGetMovingAverage(3), 0);//amount = 2+5+7+3=17, 17/4 = 4.25
		Assert.assertEquals("result match", 4.6, paytmLabsService.addAndGetMovingAverage(6), 0);//amount =2+5+7+3+6 = 23, 23/5 = 4.6
		Assert.assertEquals("result match", 5.8, paytmLabsService.addAndGetMovingAverage(8), 0);//amount = 5+7+3+6+8 = 29, 29/5=5.8, since there are only 5 items in max allow to be calculated
	}
	
}
