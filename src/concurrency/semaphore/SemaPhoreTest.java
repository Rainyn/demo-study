package concurrency.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/** 
* @ClassName: SemaPhoreTest 
* @Description: 线程通信中的"信号量",有时被称为信号灯，是在多线程环境下使用的一种设施, 它负责协调各个线程, 以保证它们能够正确、合理的使用公共资源。

举个例子,公共厕所只有3个位子,有10个人要上厕所.一开始3个位都是空的,那么将有3个人先后占得坑,其它人只要在外面等待.如果有一个人上完厕所,然后等待中的7人中将有一个可以去上厕所.也有可能同时有两人OK,那么也将会同时有两人补位.简单说就是你必须有空位了,才能去上厕所.

上厕所的例子虽不雅观,但令人印象深刻,本来想举ATM机取钱的例子的,但意思一样,还是举上厕所吧.

Semaphore的作用就是控制位置的分配,一般情况下位置的分配是随机的,可以在实例化对象时设置规则进行排序.
* @author: amosli
* @email:hi_amos@outlook.com
* @date Apr 25, 2014 12:06:22 AM  
*/
public class SemaPhoreTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        
        final Semaphore semaphore= new Semaphore(3); //new Semaphore(3);默认创建是无序的.   new Semaphore(3,true);有序
        for(int i=0;i<10;i++){
            threadPool.execute(new Runnable() {
                public void run() {
                    try {
                        //程序会先检查,"坑"是否已经满了,如果没满,那么可以按需分配
                        semaphore.acquire();//获取一个可用的permits
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程 " + Thread.currentThread().getName()+" 已进入.  " + "目前已经有"+(3-semaphore.availablePermits())+" 个线程进入");
                    try {
                        Thread.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程 "+Thread.currentThread().getName()+ " 即将离开...");
                    semaphore.release();//释放一个线程
                    System.out.println("线程 "+Thread.currentThread().getName()+ " 已离开.  当前有"+(3-semaphore.availablePermits())+"并发");
                }
            });
        }
    }
}