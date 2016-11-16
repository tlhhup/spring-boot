package com.tlh.spring.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {

	@Async
	public void executeAsyncTask(int i){
		System.out.println("executeAsyncTask-->ִ���첽����"+i);
	}
	
	@Async
	public void executeAsyncTaskPlus(int i){
		System.out.println("executeAsyncTaskPlus-->ִ���첽����"+i);
	}
	
}
