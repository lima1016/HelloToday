package com.lima.hellotodayconsole.cluster;

import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperCluster {

  private String host;
  private int sessionTimeout = 3000;

  public ZookeeperCluster(String host) {
    this.host = host;
  }

  public ZookeeperCluster(String host, int sessionTimeout) {
    this.host = host;
    this.sessionTimeout = sessionTimeout;
  }

  public void connectCluster () {
    try {
      ZooKeeper zookeeper = new ZooKeeper(host, sessionTimeout, new Watcher() {
        public void process(WatchedEvent event) {
          // 이벤트 처리
          System.out.println("Event received: " + event);
        }
      });

      // Zookeeper 클러스터의 맴버 노드 생성
      zookeeper.create("/member", "data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
      // /member 는 Zookeeper의 계층 구조에서 위치를 지정한다.
      // 노드의 ACL(Access Control List)을 나타낸다. 이 ACL 설정은 노드에 대한 접근 권한을 지정한다.
      // ZooDefs.Ids~는 모든 권한을 허용하는 기본 ACL을 나타낸다. 실제 운영환경에서는 보안을 고려하여 더 엄격하게 설정/
      // createMode.PERSISTENT 노드의 생성 보드를 나타낸다. PERSISTENT 모드는 지속적인 노드를 생성함을 의미
      // 즉 노드가 삭제되지 않는 한 지속적으로 유지된다.

      // Zookeeper 클러스터 맴버 노드 확인
      System.out.println("Cluster members: " + zookeeper.getChildren("/member", false));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}