package com.PaytmLabs.CodeChallenge.Service.impl;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.PaytmLabs.CodeChallenge.Service.PaytmLabs_SDE_Challenge_Coding_Question;
@Service
public class PaytmLabs_SDE_Challenge_Coding_QuestionImpl implements PaytmLabs_SDE_Challenge_Coding_Question {

	private int amount = 0;// this variable stores the amount of N last added elements
	private int windowSize = 0;// this is to store the given N value
	Queue<Integer> queue;// To choice to use Queue because it is FIFO

	@Override
	public void init(int N) {
		queue = new LinkedList<>();
		windowSize = N;

	}

	@Override
	public double addAndGetMovingAverage(int input) {
		queue.add(input);
		amount += input;
		if (queue.size() > windowSize) {
			amount -= queue.poll();
		}
		return amount * 1.0 / queue.size();
	}

}
