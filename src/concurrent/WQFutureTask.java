package concurrent;

import java.util.concurrent.Callable;

//作为任务类，需要实现Runnable的Run方法
public class WQFutureTask<T> implements Runnable {

    //真正的任务逻辑都在这里
    private Callable<T> callable;
    //任务的返回值,防止重排并保证立即可见
    private volatile T result;

    public WQFutureTask(Callable<T> callable){
        this.callable = callable;
    }

    @Override
    public void run() {
        //run其实就是调callable的call
        try {
            T result = callable.call();
            this.result = result;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized T get(){

        //callable没有跑完，自旋等待
        while (result == null){
            try {
                this.wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
