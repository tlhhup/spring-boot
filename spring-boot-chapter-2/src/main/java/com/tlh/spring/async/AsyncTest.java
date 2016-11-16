package com.tlh.spring.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AsyncTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext act = new AnnotationConfigApplicationContext();
		act.register(TaskExecutorConfig.class);
		act.refresh();
		
		AsyncTaskService taskService = act.getBean(AsyncTaskService.class);
		for(int i=0;i<10;i++){
			taskService.executeAsyncTask(i);
			taskService.executeAsyncTaskPlus(i);
		}
		act.close();
	}

}
