//import java.io.IOException;
//import java.util.List;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooDefs.Ids;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class AutoZookeeperConfig {
////    private String connectString="hadoop101:2181,hadoop102:2181,hadoop103:2181";
//	private String connectString="10.0.11.209:4181";
//    private int sessionTimeout=5000;
//    private ZooKeeper zooKeeper;
//
//    //  zkCli.sh  
//    @Before
//    public void testConnection() throws Exception {
//        
//         zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
//            
//            // process 是watcher的回调方法
//            @Override
//            public void process(WatchedEvent event) {
//                
//                System.out.println(event);
//            }
//        });
//        
//        System.out.println(zooKeeper.getState());
//        
//    }
//
//
//    // 持续监听： 在当前watcher的回调方法中，再次设置观察者！ 递归调用！
//    public String GetData() throws Exception {
//        
//        // 创建一个watcher
//        byte[] data = zooKeeper.getData("/dongjs", new Watcher() {
//            
//            @Override
//            public void process(WatchedEvent event) {
//                
//                System.out.println(event.getPath()+"数据发生了变化！====>"+event.getType());
//                
//                try {
//                    String newResult = GetData();
//                    
//                    System.out.println("新的结果是："+newResult);
//                    
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                
//            }
//        }, null);
//        
//        return new String(data);
//        
//    }
//    
//    @Test
//    public void testAlwaysSetWatcher() throws Exception {
//        
//        String data = GetData();
//        
//        System.out.println("查询到的数据是："+data);
//        
//        // 必须保证程序在运行状态，才可以获取到watcher的回调方法！
//                while(true) {
//                    
//                    Thread.sleep(10000);
//                    
//                    System.out.println("我还活着！");
//                    
//                }
//        
//    }
//    
//    
//    
//    @After
//    public void testClose() throws Exception {
//        
//        if (zooKeeper !=null) {
//            
//            zooKeeper.close();
//        }
//        
//    }
//
//}