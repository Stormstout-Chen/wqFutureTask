import concurrent.WQFutureTask;
import org.junit.Test;
import service.RemoteService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Run {

    private RemoteService remoteService = new RemoteService();

    /*
    * 这里我们模拟了一个场景：通过异步调用两个接口，提高响应时间从3+2秒到3秒
    * 接下来就是自己去实现一个FutureTask类
    * 已知：这个类的构造方法需要一个Callable对象，提供繁星，提供一个阻塞式获取返回值的get方法，且能作为Thread类的构造传参
    * 求：实现其作为提供阻塞式获取结果的任务对象的全部功能
    * */
    @Test public void TestFutureTask() throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();

        FutureTask<List<String>> futureTask1 = new FutureTask<>(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return remoteService.getInfo();
            }
        });

        FutureTask<List<String>> futureTask2 = new FutureTask<>(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return remoteService.getInfoToo();
            }
        });

        new Thread(futureTask1).start();
        new Thread(futureTask2).start();

        ArrayList<String> result = new ArrayList<>();
        result.addAll(futureTask1.get());
        result.addAll(futureTask2.get());

        long endTime = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("耗时"+(endTime-startTime)+"毫秒");
    }


    /*
     * 与上面完全一样的代码，只不过使用WQFutureTask替换了JDK的FutureTask
     * */
    @Test public void TestWQFutureTask() throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();

        WQFutureTask<List<String>> wqfutureTask1 = new WQFutureTask<>(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return remoteService.getInfo();
            }
        });

        WQFutureTask<List<String>> wqfutureTask2 = new WQFutureTask<>(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return remoteService.getInfoToo();
            }
        });

        new Thread(wqfutureTask1).start();
        new Thread(wqfutureTask2).start();

        ArrayList<String> result = new ArrayList<>();
        result.addAll(wqfutureTask1.get());
        result.addAll(wqfutureTask2.get());

        long endTime = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("耗时"+(endTime-startTime)+"毫秒");
    }

}
