package com.lima.hellotodaycore.schedule;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

// 실제 작업이 수행될 클래스 생성
public class MyJob implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println("context.getJobDetail().getJobDataMap().get(\"key\") >>>" + context.getJobDetail().getJobDataMap().get("key"));
    System.out.println(context.getMergedJobDataMap().get("key"));

  System.out.println("context.getFireTime() >>> " + context.getFireTime());
    System.out.println(context.getNextFireTime());
    System.out.println("============= 작업 시작");
    // 작업할 코드
  }
}