package service;

import java.util.ArrayList;
import java.util.List;

public class RemoteService {

    //模拟调用一个第三方接口
    public List<String> getInfo(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> result = new ArrayList<>();
        result.add("1");
        result.add("2");
        result.add("3");
        return result;
    }


    //模拟调用第二个第三方接口
    public List<String> getInfoToo(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> result = new ArrayList<>();
        result.add("A");
        result.add("B");
        result.add("C");
        return result;
    }
}
